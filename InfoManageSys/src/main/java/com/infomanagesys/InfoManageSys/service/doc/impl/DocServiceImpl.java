package com.infomanagesys.InfoManageSys.service.doc.impl;

import com.infomanagesys.InfoManageSys.dao.repository.TempTableRepository;
import com.infomanagesys.InfoManageSys.dao.repository.doc.*;
import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFileInfo;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.DirClassifyRules;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.Label;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.LabelGroup;
import com.infomanagesys.InfoManageSys.dataobject.enums.DocTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.RuleGroupEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.TempTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.*;
import com.infomanagesys.InfoManageSys.exception.FileExistException;
import com.infomanagesys.InfoManageSys.service.doc.itf.IDocService;
import com.infomanagesys.InfoManageSys.util.Pid;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
@Service
public class DocServiceImpl implements IDocService
{

    private final DocFileRepository docFileRepository;
    private final DocDirRepository docDirRepository;
    private final TempTableRepository tempTableRepository;
    private final DocFileInfoRepository docFileInfoRepository;
    private final NLPServiceImpl nlpService;
    private final DirClassifyRulesRepository dirClassifyRulesRepository;
    private final LabelRepository labelRepository;
    private final LabelGroupRepository labelGroupRepository;

    @Autowired
    public DocServiceImpl(final DocFileRepository docFileRepository,
                          final DocDirRepository docDirRepository,
                          final TempTableRepository tempTableRepository,
                          final NLPServiceImpl nlpService,
                          final DocFileInfoRepository docFileInfoRepository,
                          final DirClassifyRulesRepository dirClassifyRulesRepository,
                          final LabelRepository labelRepository,
                          final LabelGroupRepository labelGroupRepository){
        this.docFileRepository=docFileRepository;
        this.docDirRepository=docDirRepository;
        this.tempTableRepository=tempTableRepository;
        this.nlpService=nlpService;
        this.docFileInfoRepository = docFileInfoRepository;
        this.dirClassifyRulesRepository = dirClassifyRulesRepository;
        this.labelRepository=labelRepository;
        this.labelGroupRepository = labelGroupRepository;
    }
    @Override
    public RuleAndFileResponseDTO uploadFile(String userId, MultipartFile file){

    // 获取上传文件的路径
        String uploadFilePath = file.getOriginalFilename();
        System.out.println("uploadFlePath:" + uploadFilePath);
        // 截取上传文件的文件名
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        System.out.println("multiReq.getFile()" + uploadFileName);
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
        System.out.println("uploadFileSuffix:" + uploadFileSuffix);

        TempTable tempTable=tempTableRepository.findFirstByTempUserAndTempType(userId, TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey());
        DocDir parentDir=docDirRepository.findFirstByPid(tempTable.getTempValue());
        DocFile docFile1=docFileRepository.findFirstByUserAndParentAndNameAndPostfix(userId,parentDir.getPid(),uploadFileName,DocTypeEnum.getEnumByName(uploadFileSuffix).getName());
        if(docFile1!=null){
            throw new FileExistException("已存在同名文件");
        }

        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = (FileInputStream) file.getInputStream();
            fos = new FileOutputStream(new File("E://upLoadFiles//" + uploadFileName
                    + ".")
                    + uploadFileSuffix);
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1){
                fos.write(temp,0,temp.length);
                fos.flush();
                i = fis.read(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        DocFile docFile = DocFile.docFileBuilder().withPid(Pid.getPid())
                .withName(uploadFileName)
                .withType(DocTypeEnum.getEnumByName(uploadFileSuffix).getType())
                .withParent(parentDir.getPid())
                .withPath(parentDir.getPath())
                .withPathLocal("E:\\upLoadFiles")
                .withClassify("false")
                .withUser(userId)
                .withStatus("0")
                .withPostfix(DocTypeEnum.getEnumByName(uploadFileSuffix).getName())
                .build();
        ArrayList<LabelResponseDTO> labelResponseDTOArrayList=nlpService.GetLabelsByWorldFile(docFile,userId);

        DocFileInfo docFileInfo = DocFileInfo.docFileInfoBuilder()
                .withFileId(docFile.getPid())
                .withLabel(LabelListToString(labelResponseDTOArrayList))
                .withtDescription("null")
                .build();
        docFileInfoRepository.save(docFileInfo);
        docFileRepository.save(docFile);
        FileResponseDTO fileResponseDTO = parseDocFileToDTO(docFile);
        fileResponseDTO.setLabels(labelResponseDTOArrayList);
        RuleAndFileResponseDTO ruleAndFileResponseDTO = new RuleAndFileResponseDTO();
        ruleAndFileResponseDTO.setLabelGroup(getLabelGroupByFile(parentDir.getPid(),fileResponseDTO));
        ruleAndFileResponseDTO.setFile(fileResponseDTO);
        return ruleAndFileResponseDTO;

    }
    @Override
    public FileResponseDTO createDir(String userId, String dirName){
        TempTable tempTable=tempTableRepository.findFirstByTempUserAndTempType(userId, TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey());
        if(docDirRepository.findFirstByUserAndNameAndParent(userId,dirName,tempTable.getTempValue())!=null){
            throw new FileExistException("已存在同名文件夹");
        }
        if(tempTable!=null){
            DirClassifyRules dirClassifyRules = dirClassifyRulesRepository.save(
                    DirClassifyRules.dirClassifyRulesBuilderBuilder()
                            .withPid(Pid.getPid())
                            .withName("null")
                            .build()
            );
            DocDir parentDir=docDirRepository.findFirstByPid(tempTable.getTempValue());
            DocDir docDir = DocDir.docDirBuilder()
                    .withPid(Pid.getPid())
                    .withName(dirName)
                    .withtParent(parentDir.getPid())
                    .withPath(parentDir.getPath()+"/"+dirName)
                    .withClassifyRules(dirClassifyRules.getPid())
                    .withUser(userId)
                    .withStatus("0")
                    .withLevel(parentDir.getLevel()+1).build();
            docDir = docDirRepository.save(docDir);
            return parseDocDirToDTO(docDir);
        }else {
            return null;
        }
    }
    @Override
    @Transactional
    public  FileResponseDTO createRootDir(String userId){
        DirClassifyRules dirClassifyRules = dirClassifyRulesRepository.save(
          DirClassifyRules.dirClassifyRulesBuilderBuilder()
                  .withPid(Pid.getPid())
                  .withName("null")
                .build()
        );
        UUID uuid = UUID.randomUUID();
        DocDir docDir = docDirRepository.save(DocDir.docDirBuilder().withName("root")
                .withPath("/")
                .withtParent("null")
                .withUser(userId)
                .withStatus("0")
                .withLevel(0)
                .withClassifyRules(dirClassifyRules.getPid())
                .withPid(uuid.toString().replaceAll("-",""))
                .build());

        return parseDocDirToDTO(docDir);
    }
    @Override
    @Transactional
    public ClassfiyedFileResponseDTO openNewDir(String userId, String dirId){
        if(tempTableRepository.findFirstByTempUserAndTempType(userId, TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey())!=null)
        {
            tempTableRepository.updateValue(dirId,userId,TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey());
        }else{
            tempTableRepository.save(TempTable.tempTableBuilder()
                    .withTempKey(userId)
                    .withTempValue(dirId)
                    .withTempType(TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey())
                    .withTempUser(userId).build());
        }

//            return getChildFile(dirId);
        return getClassfiyedFile(dirId);
    }
    @Override
    @Transactional
    public ClassfiyedFileResponseDTO openRootDir(String userId){
        String dirId = docDirRepository.findFirstByLevelAndUser(0, userId).getPid();
        if(tempTableRepository.findFirstByTempUserAndTempType(userId, TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey())!=null)
        {
            tempTableRepository.updateValue(dirId,userId,TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey());
        }else{
            tempTableRepository.save(TempTable.tempTableBuilder()
                    .withTempKey(userId)
                    .withTempValue(dirId)
                    .withTempType(TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey())
                    .withTempUser(userId).build());
        }

//        return getChildFile(dirId);
        return getClassfiyedFile(dirId);
    }

    @Override
    public FileResponseDTO getCurrentDir(String userId){
        TempTable tempTable=tempTableRepository.findFirstByTempUserAndTempType(userId, TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey());
        if(tempTable!=null){
            return parseDocDirToDTO(docDirRepository.findFirstByPid(tempTable.getTempValue()));
        }else {
            return null;
        }

    }
    @Override
    public ArrayList<FileResponseDTO> getChildFile(String parentId){
        ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();
        fileResponseDTOArrayList =parseDocFileListToDTO(docFileRepository.findByParent(parentId));
        fileResponseDTOArrayList.addAll(parseDocDirListToDTO(docDirRepository.findByParent(parentId)));
        return fileResponseDTOArrayList;
    }
    @Override
    public FileResponseDTO getParentFile(String childId){
        return parseDocDirToDTO(docDirRepository.findFirstByPid(docDirRepository.findFirstByPid(childId).getParent()));
    }
    @Override
    public AllLabelResponseDTO getAllLabels(String userId){
        ArrayList<Label> labelArrayList = labelRepository.findByUser(userId);
        AllLabelResponseDTO allLabelResponseDTO = new AllLabelResponseDTO();
        allLabelResponseDTO.setLabelResponseDTOArrayList(parseLabelListToDTO(labelArrayList));
        return allLabelResponseDTO;
    }
    @Override
    @Transactional
    public FileResponseDTO addLabelToDir(String userId,JSONObject file, JSONArray labels){
        JSONArray labelArray=new JSONArray();
        String DirId="";
        try{
            DirId= file.getString("pid");
            if(file.getString("labels")!=null){
                labelArray = new JSONArray(file.getString("labels"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0;i<labels.length();i++){
            try{
                JSONObject label = new JSONObject(labels.getString(i));
                if(label.getBoolean("isAdd")){
                    LabelResponseDTO labelResponseDTO = new LabelResponseDTO();
                    labelResponseDTO.setScore(label.getDouble("score"));
                    labelResponseDTO.setLevel(label.getInt("level"));
                    labelResponseDTO.setColor(label.getString("color"));
                    labelResponseDTO.setPid(label.getString("pid"));
                    labelResponseDTO.setContent(label.getString("content"));
                    labelArray.put(labelResponseDTO.toString());
                }else{
                   Label _label = Label.labelBuilder()
                            .withType("other")
                            .withPid(Pid.getPid())
                            .withContent(label.getString("content"))
                            .withUser(userId)
                            .withDescription(label.getString("description"))
                            .withStatus("0").build();
                    _label = labelRepository.save(_label);
                    LabelResponseDTO labelResponseDTO = new LabelResponseDTO();
                    labelResponseDTO.setScore(label.getDouble("score"));
                    labelResponseDTO.setLevel(label.getInt("level"));
                    labelResponseDTO.setColor(label.getString("color"));
                    labelResponseDTO.setPid(_label.getPid());
                    labelResponseDTO.setContent(_label.getContent());
                    labelArray.put(labelResponseDTO.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        docDirRepository.updateLabelByUserAndPid(labelArray.toString(),userId,DirId);
        return parseDocDirToDTO(docDirRepository.findFirstByPid(DirId));
    }
    @Override
    public boolean checkLabel(String userId, String content){
        Label label = labelRepository.findFirstByContent(content);
        if(label!=null){
            return false;
        }else {
            return true;
        }

    }
    @Override
    @Transactional
    public FileResponseDTO addRuleToDir(String userId,JSONObject dir,JSONArray labels,String ruleName,JSONArray fileTypes){
        int priorityLevel;
        JSONArray labelsGroup =new JSONArray();
        JSONArray groupLabels =new JSONArray();
        JSONArray groupFileTypes =new JSONArray();
        String dirId="";
        try{
            dirId = dir.getString("pid");
            DocDir docDir = docDirRepository.findFirstByPidAndUser(dirId,userId);
            String classfiyRulesId = docDir.getClassifyRules();
            DirClassifyRules dirClassifyRules = dirClassifyRulesRepository.findFirstByPid(classfiyRulesId);
            if(dirClassifyRules.getLabelsGroup()!=null){
                labelsGroup = new JSONArray(dirClassifyRules.getLabelsGroup());
                if(labelsGroup.length()>0){
                    JSONObject labelGroup = new JSONObject(labelsGroup.getString(labelsGroup.length()-1));
                    priorityLevel = labelGroupRepository.findFirstByPid(labelGroup.getString("pid")).getPriorityLevel()+1;
                } else {
                    priorityLevel=0;
                }
            }else {
                priorityLevel=0;
            }
            for(int i=0;i<labels.length();i++) {
                JSONObject label = new JSONObject(labels.getString(i));
                JSONObject _label = new JSONObject();
                _label.put("pid",label.getString("pid"));
                _label.put("group",label.getString("group"));
                groupLabels.put(_label);
            }
            for(int i=0;i<fileTypes.length();i++) {
                JSONObject fileType = new JSONObject(fileTypes.getString(i));
                JSONObject _fileType = new JSONObject();
                _fileType.put("name",fileType.getString("name"));
                groupFileTypes.put(_fileType);
            }
            LabelGroup labelGroup = labelGroupRepository.save(LabelGroup.labelGroupBuilder()
                    .withPid(Pid.getPid())
                    .withLabels(groupLabels.toString())
                    .withFileType(groupFileTypes.toString())
                    .withPriorityLevel(priorityLevel)
                    .withName(ruleName)
                    .build()
            );
            JSONObject _labelGroup = new JSONObject();
            _labelGroup.put("pid",labelGroup.getPid());
            labelsGroup.put(_labelGroup);
            dirClassifyRulesRepository.updateLabelsGroupByPid(labelsGroup.toString(),classfiyRulesId);


        }catch (Exception e){
            e.printStackTrace();
        }
        return parseDocDirToDTO(docDirRepository.findFirstByPid(dirId));

    }

    @Override
    public ClassfiyRuleResponseDTO getClassfiyRule( String dirId){
        ClassfiyRuleResponseDTO classfiyRuleResponseDTO = new ClassfiyRuleResponseDTO();
        DocDir docDir = docDirRepository.findFirstByPid(dirId);
        String classfiyId = docDir.getClassifyRules();
        ArrayList<LabelGroupResponseDTO> labelGroups = new ArrayList<LabelGroupResponseDTO>();
        JSONArray _labelGroups = new JSONArray();

        DirClassifyRules dirClassifyRules = dirClassifyRulesRepository.findFirstByPid(classfiyId);
        classfiyRuleResponseDTO.setDirId(dirId);
        classfiyRuleResponseDTO.setName(dirClassifyRules.getName());
        classfiyRuleResponseDTO.setPid(dirClassifyRules.getPid());
        if(dirClassifyRules.getLabelsGroup()!=null){
            try {
                _labelGroups = new JSONArray(dirClassifyRules.getLabelsGroup());
                for(int i=0;i<_labelGroups.length();i++){
                    LabelGroupResponseDTO labelGroupResponseDTO = new LabelGroupResponseDTO();

                    LabelGroup labelGroup = labelGroupRepository.findFirstByPid(new JSONObject(_labelGroups.getString(i)).getString("pid"));
                    ArrayList<LabelResponseDTO> labelResponseDTOArrayList = new ArrayList<LabelResponseDTO>();
                    ArrayList<String> fileTypeArrayList = new ArrayList<String>();
                    JSONArray labaels = new JSONArray(labelGroup.getLabels());
                    JSONArray fileTypes = new JSONArray(labelGroup.getFileType());
                    for(int j=0;j<labaels.length();j++){
                        Label label = labelRepository.findFirstByPid(new JSONObject(labaels.getString(j)).getString("pid"));
                        LabelResponseDTO labelResponseDTO = parseLabelToDTO(label);
                        labelResponseDTO.setColor(RuleGroupEnum.getEnumByGroup(new JSONObject(labaels.getString(j)).getString("group")).getColor());
                        labelResponseDTOArrayList.add(labelResponseDTO);
                    }
                    for(int j=0;j<fileTypes.length();j++){
                        JSONObject fileType = new JSONObject(fileTypes.getString(j));
                        fileTypeArrayList.add(fileType.getString("name"));
                    }
                    labelGroupResponseDTO.setFileTypes(fileTypeArrayList);
                    labelGroupResponseDTO.setLabels(labelResponseDTOArrayList);
                    labelGroupResponseDTO.setName(labelGroup.getName());
                    labelGroupResponseDTO.setPid(labelGroup.getPid());
                    labelGroupResponseDTO.setPriorityLevel(labelGroup.getPriorityLevel());
                    labelGroups.add(labelGroupResponseDTO);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        ArrayList<LabelGroupResponseDTO> rankLabelGroups = new ArrayList<LabelGroupResponseDTO>();

//        int length = labelGroups.size();
//        for(int i=0;i<length;i++){
//            int temp_k=100;
//            int temp_j=0;
//            for (int j=0;j<labelGroups.size();j++){
//                if(temp_k>labelGroups.get(j).getPriorityLevel()){
//                    temp_k = labelGroups.get(j).getPriorityLevel();
//                    temp_j = j;
//                }
//            }
//            rankLabelGroups.add(labelGroups.get(temp_j));
//            labelGroups.remove(temp_j);
//        }

        classfiyRuleResponseDTO.setLabelGroups(labelGroups);
        return classfiyRuleResponseDTO;

    }

    @Override
    @Transactional
    public ResponseDTO deleteClassfiyRule(String ruleId, String classfiyId){
        labelGroupRepository.deleteByPid(ruleId);
        DirClassifyRules dirClassifyRules = dirClassifyRulesRepository.findFirstByPid(classfiyId);
        try {
            JSONArray labelsGroup = new JSONArray(dirClassifyRules.getLabelsGroup());
            for(int i=0;i<labelsGroup.length();i++){
                JSONObject labelGroup = new JSONObject(labelsGroup.getString(i));
                String _ruleId = labelGroup.getString("pid");
                if(_ruleId.equals(ruleId)){
                    labelsGroup.remove(i);
                    break;
                }
            }
            dirClassifyRulesRepository.updateLabelsGroupByPid(labelsGroup.toString(),classfiyId);
            return new ResponseDTO();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    @Transactional
    public ResponseDTO deleteLabel(String labelId,String labelGroupId){
       LabelGroup labelGroup = labelGroupRepository.findFirstByPid(labelGroupId);
       try {
           JSONArray labels = new JSONArray(labelGroup.getLabels());
           for(int i=0;i<labels.length();i++){
               JSONObject label = new JSONObject(labels.getString(i));
               if(label.getString("pid").equals(labelId)){
                   labels.remove(i);
                   break;
               }
           }
           labelGroupRepository.updateLabelsByPid(labels.toString(),labelGroupId);
           return  new ResponseDTO();
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }
    @Override
    @Transactional
    public ResponseDTO deleteFileType(String fileType,String labelGroupId){
        LabelGroup labelGroup = labelGroupRepository.findFirstByPid(labelGroupId);
        try {
            JSONArray fileTypes = new JSONArray(labelGroup.getFileType());
            for(int i=0;i<fileTypes.length();i++){
                JSONObject _fileType = new JSONObject(fileTypes.getString(i));
                if(_fileType.getString("name").equals(fileType)){
                    fileTypes.remove(i);
                    break;
                }
            }
            labelGroupRepository.updateFileTypesByPid(fileTypes.toString(),labelGroupId);
            return  new ResponseDTO();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    @Transactional
    public ResponseDTO addSingleLabel(JSONObject label,String labelGroupId){
        LabelGroup labelGroup = labelGroupRepository.findFirstByPid(labelGroupId);
        try {
            JSONArray labels = new JSONArray(labelGroup.getLabels());
            JSONObject _label = new JSONObject();
            _label.put("pid",label.getString("pid"));
            _label.put("group",label.getString("group"));
            labels.put(_label);
            labelGroupRepository.updateLabelsByPid(labels.toString(),labelGroupId);
            return  new ResponseDTO();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    @Transactional
    public ResponseDTO addSingleFileType(String fileType,String labelGroupId){
        LabelGroup labelGroup = labelGroupRepository.findFirstByPid(labelGroupId);
        try {
            JSONArray fileTypes = new JSONArray(labelGroup.getFileType());
            JSONObject _fileType = new JSONObject();
            _fileType.put("name",fileType);
            fileTypes.put(_fileType);
            labelGroupRepository.updateFileTypesByPid(fileTypes.toString(),labelGroupId);
            return  new ResponseDTO();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    @Transactional
    public ResponseDTO exchangeLevel(String curPid,String nextPid,String classfiyRuleId){
        DirClassifyRules dirClassifyRules = dirClassifyRulesRepository.findFirstByPid(classfiyRuleId);
        try {
            JSONArray labelsGroup = new JSONArray(dirClassifyRules.getLabelsGroup());

            for(int i=0;i<labelsGroup.length();i++){
                JSONObject labelGroup = new JSONObject(labelsGroup.getString(i));
                if(labelGroup.getString("pid").equals(nextPid)){
                    JSONObject nextObject = new JSONObject();
                    JSONObject curObject = new JSONObject();
                    nextObject.put("pid",nextPid);
                    curObject.put("pid",curPid);
                    labelsGroup.put(i,curObject);
                    labelsGroup.put(i+1,nextObject);
                    LabelGroup curGroup = labelGroupRepository.findFirstByPid(curPid);
                    LabelGroup nectGroup = labelGroupRepository.findFirstByPid(nextPid);
                    int temp_c = curGroup.getPriorityLevel();
                    int temp_n = nectGroup.getPriorityLevel();
                    labelGroupRepository.updateLevelByPid(temp_c,nextPid);
                    labelGroupRepository.updateLevelByPid(temp_n,curPid);
                    break;
                }
            }
            dirClassifyRulesRepository.updateLabelsGroupByPid(labelsGroup.toString(),classfiyRuleId);
            return new ResponseDTO();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public ClassfiyedFileResponseDTO getClassfiyedFile(String dirId){
        ClassfiyedFileResponseDTO classfiyedFileResponseDTO = new ClassfiyedFileResponseDTO();
        ArrayList<RulesAndFileLIstResponseDTO> rulesAndFileLIstResponseDTOArrayList = new ArrayList<RulesAndFileLIstResponseDTO>();

        ArrayList<FileResponseDTO> fileResponseDTOArrayList = getChildFile(dirId);
        ClassfiyRuleResponseDTO classfiyRuleResponseDTO = getClassfiyRule(dirId);
        for(LabelGroupResponseDTO labelGroupResponseDTO:classfiyRuleResponseDTO.getLabelGroups()){

            RulesAndFileLIstResponseDTO rulesAndFileLIstResponseDTO = new RulesAndFileLIstResponseDTO();
            LabelGroupResponseDTO labelGroup = labelGroupResponseDTO;
            ArrayList<FileResponseDTO> fileArrayList = new ArrayList<FileResponseDTO>();

            for(FileResponseDTO fileResponseDTO : fileResponseDTOArrayList){
                if(labelGroupResponseDTO.getFileTypes().size()>0&&labelGroupResponseDTO.getFileTypes().indexOf(fileResponseDTO.getType())<0){
                    break;
                }
                boolean isAnd=true;
                boolean isOr=false;
                boolean isNo=true;
                boolean haveGreen=false;
                for(LabelResponseDTO labelResponseDTO : labelGroupResponseDTO.getLabels()){
                    if(labelResponseDTO.getColor()=="red"){
                        isAnd=false;
                        if(fileResponseDTO.getLabels()!=null){
                            for(LabelResponseDTO fileLabel:fileResponseDTO.getLabels()){
                                if(fileLabel.getContent().equals(labelResponseDTO.getContent())){
                                    isAnd=true;
                                    break;
                                }
                            }
                        }
                        if(!isAnd){
                            break;
                        }
                    }else if(labelResponseDTO.getColor()=="green"){
                        haveGreen=true;
                        if(fileResponseDTO.getLabels()!=null) {
                            for (LabelResponseDTO fileLabel : fileResponseDTO.getLabels()) {
                                if (fileLabel.getContent().equals(labelResponseDTO.getContent())) {
                                    isOr = true;
                                    break;
                                }
                            }
                        }
                    }else if(labelResponseDTO.getColor()=="#52575c"){
                        if(fileResponseDTO.getLabels()!=null) {
                            for (LabelResponseDTO fileLabel : fileResponseDTO.getLabels()) {
                                if (fileLabel.getContent().equals(labelResponseDTO.getContent())) {
                                    isNo = false;
                                    break;
                                }
                            }
                        }
                        if(!isNo){
                            break;
                        }
                    }
                }
                if(isAnd&&isNo){
                    if((!haveGreen)||(haveGreen&&isOr)){
                        fileArrayList.add(fileResponseDTO);
                    }
                }
            }
            for(FileResponseDTO fileResponseDTO : fileArrayList){
                fileResponseDTOArrayList.remove(fileResponseDTO);
            }
            rulesAndFileLIstResponseDTO.setLabelGroup(labelGroup);
            rulesAndFileLIstResponseDTO.setFileResponseDTOArrayList(fileArrayList);
            rulesAndFileLIstResponseDTOArrayList.add(rulesAndFileLIstResponseDTO);
        }
        if(fileResponseDTOArrayList.size()>0){
            RulesAndFileLIstResponseDTO rulesAndFileLIstResponseDTO = new RulesAndFileLIstResponseDTO();
            LabelGroupResponseDTO labelGroup = new LabelGroupResponseDTO();
            labelGroup.setName("其他");
            rulesAndFileLIstResponseDTO.setLabelGroup(labelGroup);
            rulesAndFileLIstResponseDTO.setFileResponseDTOArrayList(fileResponseDTOArrayList);
            rulesAndFileLIstResponseDTOArrayList.add(rulesAndFileLIstResponseDTO);
        }
        classfiyedFileResponseDTO.setDirId(dirId);
        classfiyedFileResponseDTO.setRulesAndFileLIstResponseDTOArrayList(rulesAndFileLIstResponseDTOArrayList);
        return classfiyedFileResponseDTO;

    }
    public LabelGroupResponseDTO getLabelGroupByFile(String dirId, FileResponseDTO file){
        ClassfiyRuleResponseDTO classfiyRuleResponseDTO = getClassfiyRule(dirId);
        for(LabelGroupResponseDTO labelGroupResponseDTO:classfiyRuleResponseDTO.getLabelGroups()){

                if(labelGroupResponseDTO.getFileTypes().size()>0&&labelGroupResponseDTO.getFileTypes().indexOf(file.getType())<0){
                    break;
                }
                boolean isAnd=true;
                boolean isOr=false;
                boolean isNo=true;
                boolean haveGreen=false;
                for(LabelResponseDTO labelResponseDTO : labelGroupResponseDTO.getLabels()){
                    if(labelResponseDTO.getColor()=="red"){
                        isAnd=false;
                        if(file.getLabels()!=null){
                            for(LabelResponseDTO fileLabel:file.getLabels()){
                                if(fileLabel.getContent().equals(labelResponseDTO.getContent())){
                                    isAnd=true;
                                    break;
                                }
                            }
                        }
                        if(!isAnd){
                            break;
                        }
                    }else if(labelResponseDTO.getColor()=="green"){
                        haveGreen=true;
                        if(file.getLabels()!=null) {
                            for (LabelResponseDTO fileLabel : file.getLabels()) {
                                if (fileLabel.getContent().equals(labelResponseDTO.getContent())) {
                                    isOr = true;
                                    break;
                                }
                            }
                        }
                    }else if(labelResponseDTO.getColor()=="#52575c"){
                        if(file.getLabels()!=null) {
                            for (LabelResponseDTO fileLabel : file.getLabels()) {
                                if (fileLabel.getContent().equals(labelResponseDTO.getContent())) {
                                    isNo = false;
                                    break;
                                }
                            }
                        }
                        if(!isNo){
                            break;
                        }
                    }
                }
                if(isAnd&&isNo){
                    if((!haveGreen)||(haveGreen&&isOr)){
                        return  labelGroupResponseDTO;
                    }
                }
        }
        LabelGroupResponseDTO labelGroup = new LabelGroupResponseDTO();
        labelGroup.setName("其他");
        return labelGroup;
    }
    private FileResponseDTO parseDocDirToDTO(DocDir docDir){
        FileResponseDTO fileResponseDTO = new FileResponseDTO();
        fileResponseDTO.setName(docDir.getName());
        fileResponseDTO.setPath(docDir.getPath());
        fileResponseDTO.setPid(docDir.getPid());
        fileResponseDTO.setType(DocTypeEnum.DIR_TYPE_ENUM.getType());
        fileResponseDTO.setIconPath(DocTypeEnum.DIR_TYPE_ENUM.getIconPath());
        if(docDir.getLabel()!=null){
            try {
                ArrayList<LabelResponseDTO> labelResponseDTOArrayList = new ArrayList<LabelResponseDTO>();
                JSONArray labels = new JSONArray(docDir.getLabel());
                for(int i=0;i<labels.length();i++){
                    JSONObject label=new JSONObject(labels.getString(i));
                    LabelResponseDTO labelResponseDTO = new LabelResponseDTO();
                    labelResponseDTO.setContent(label.getString("content"));
                    labelResponseDTO.setColor(label.getString("color"));
                    labelResponseDTO.setLevel(label.getInt("level"));
                    labelResponseDTO.setScore(label.getDouble("score"));
                    labelResponseDTO.setPid(label.getString("pid"));
                    labelResponseDTOArrayList.add(labelResponseDTO);
                }
                fileResponseDTO.setLabels(labelResponseDTOArrayList);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            fileResponseDTO.setLabels(new ArrayList<LabelResponseDTO>());
        }
        return fileResponseDTO;
    }
    private FileResponseDTO parseDocFileToDTO(DocFile docFile){
        FileResponseDTO fileResponseDTO = new FileResponseDTO();
        fileResponseDTO.setName(docFile.getName());
        fileResponseDTO.setPath(docFile.getPath());
        fileResponseDTO.setPid(docFile.getPid());
        fileResponseDTO.setType(docFile.getType());
        fileResponseDTO.setIconPath(DocTypeEnum.getEnumByName(docFile.getPostfix().toLowerCase()).getIconPath());
        if(docFile.getId()!=null){
            DocFileInfo docFileInfo=docFileInfoRepository.findFirstByFileId(docFile.getPid());
            if(docFileInfo!=null){
                try {
                    ArrayList<LabelResponseDTO> labelResponseDTOArrayList = new ArrayList<LabelResponseDTO>();
                    JSONArray labels = new JSONArray(docFileInfo.getLabel());
                    for(int i=0;i<labels.length();i++){
                        JSONObject label= new JSONObject(labels.getString(i));
                        LabelResponseDTO labelResponseDTO = new LabelResponseDTO();
                        labelResponseDTO.setContent(label.getString("content"));
                        labelResponseDTO.setColor(label.getString("color"));
                        labelResponseDTO.setLevel(label.getInt("level"));
                        labelResponseDTO.setScore(label.getDouble("score"));
                        labelResponseDTO.setPid(label.getString("pid"));
                        labelResponseDTOArrayList.add(labelResponseDTO);
                    }
                    fileResponseDTO.setLabels(labelResponseDTOArrayList);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        return fileResponseDTO;
    }
    private  ArrayList<FileResponseDTO> parseDocFileListToDTO(ArrayList<DocFile> docFileArrayList){
        ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();
        for (DocFile docFile:docFileArrayList) {
            fileResponseDTOArrayList.add(parseDocFileToDTO(docFile));
        }
        return fileResponseDTOArrayList;
    }
    private ArrayList<FileResponseDTO> parseDocDirListToDTO(ArrayList<DocDir> docDirArrayList){
        ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();
        for (DocDir docDir: docDirArrayList) {
            fileResponseDTOArrayList.add(parseDocDirToDTO(docDir));
        }
        return fileResponseDTOArrayList;
    }
    private String LabelListToString(ArrayList<LabelResponseDTO> labelResponseDTOArrayList){
        JSONArray labels = new JSONArray();
        for (LabelResponseDTO labelResponseDTO: labelResponseDTOArrayList) {
            labels.put(labelResponseDTO.toString());
        }
        return labels.toString();
    }
    private LabelResponseDTO parseLabelToDTO(Label label){
        LabelResponseDTO labelResponseDTO = new LabelResponseDTO();
        labelResponseDTO.setPid(label.getPid());
        labelResponseDTO.setContent(label.getContent());
        if(label.getDescription()!=null){
            labelResponseDTO.setDiscription(label.getDescription());
        }
        labelResponseDTO.setType(label.getType());
        return labelResponseDTO;
    }
    private ArrayList<LabelResponseDTO> parseLabelListToDTO(ArrayList<Label> labelArrayList){
        ArrayList<LabelResponseDTO> labelResponseDTOArrayList = new ArrayList<LabelResponseDTO>();
        for (Label label: labelArrayList) {
          labelResponseDTOArrayList.add(parseLabelToDTO(label));
        }
        return labelResponseDTOArrayList;
    }
}
