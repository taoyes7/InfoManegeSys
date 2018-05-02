package com.infomanagesys.InfoManageSys.service.doc.impl;

import com.infomanagesys.InfoManageSys.dao.repository.TempTableRepository;
import com.infomanagesys.InfoManageSys.dao.repository.doc.*;
import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFileInfo;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.FileShare;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.DirClassifyRules;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.Label;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.LabelGroup;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.LabelType;
import com.infomanagesys.InfoManageSys.dataobject.enums.*;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.*;
import com.infomanagesys.InfoManageSys.exception.ExistException;
import com.infomanagesys.InfoManageSys.exception.FileExistException;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.doc.itf.IDocService;
import com.infomanagesys.InfoManageSys.util.Pid;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.sql.Timestamp;
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
    private final LabelTypeRepository labelTypeRepository;
    private final FileShareRepository fileShareRepository;

    @Autowired
    public DocServiceImpl(final DocFileRepository docFileRepository,
                          final DocDirRepository docDirRepository,
                          final TempTableRepository tempTableRepository,
                          final NLPServiceImpl nlpService,
                          final DocFileInfoRepository docFileInfoRepository,
                          final DirClassifyRulesRepository dirClassifyRulesRepository,
                          final LabelRepository labelRepository,
                          final LabelGroupRepository labelGroupRepository,
                          final LabelTypeRepository labelTypeRepository,
                          final FileShareRepository fileShareRepository){
        this.docFileRepository=docFileRepository;
        this.docDirRepository=docDirRepository;
        this.tempTableRepository=tempTableRepository;
        this.nlpService=nlpService;
        this.docFileInfoRepository = docFileInfoRepository;
        this.dirClassifyRulesRepository = dirClassifyRulesRepository;
        this.labelRepository=labelRepository;
        this.labelGroupRepository = labelGroupRepository;
        this.labelTypeRepository = labelTypeRepository;
        this.fileShareRepository = fileShareRepository;
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
        DocDir parentDir=docDirRepository.findFirstByPidAndStatus(tempTable.getTempValue(),FileStatusEnum.STATUS_AVAILABLE.getState());
        DocFile docFile1=docFileRepository.findFirstByUserAndParentAndNameAndPostfixAndStatus(userId,parentDir.getPid(),uploadFileName,DocTypeEnum.getEnumByName(uploadFileSuffix).getName(),FileStatusEnum.STATUS_AVAILABLE.getState());
        if(docFile1!=null){
            throw new FileExistException("已存在同名文件");
        }

        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = (FileInputStream) file.getInputStream();
            fos = new FileOutputStream(new File(SeverPathEnum.FILE_PATH.getPath() +"//"+userId+"//file//"+ uploadFileName
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
                .withStatus(FileStatusEnum.STATUS_AVAILABLE.getState())
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
        if(docDirRepository.findFirstByUserAndNameAndParentAndStatus(userId,dirName,tempTable.getTempValue(),FileStatusEnum.STATUS_AVAILABLE.getState())!=null){
            throw new FileExistException("已存在同名文件夹");
        }
        if(tempTable!=null){
            DirClassifyRules dirClassifyRules = dirClassifyRulesRepository.save(
                    DirClassifyRules.dirClassifyRulesBuilderBuilder()
                            .withPid(Pid.getPid())
                            .withName("null")
                            .build()
            );
            DocDir parentDir=docDirRepository.findFirstByPidAndStatus(tempTable.getTempValue(),FileStatusEnum.STATUS_AVAILABLE.getState());
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
        String dirId = docDirRepository.findFirstByLevelAndUserAndStatus(0, userId,FileStatusEnum.STATUS_AVAILABLE.getState()).getPid();
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
            return parseDocDirToDTO(docDirRepository.findFirstByPidAndStatus(tempTable.getTempValue(),FileStatusEnum.STATUS_AVAILABLE.getState()));
        }else {
            return null;
        }

    }
    @Override
    public ArrayList<FileResponseDTO> getChildFile(String parentId){
        ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();
        fileResponseDTOArrayList =parseDocFileListToDTO(docFileRepository.findByParentAndStatus(parentId,FileStatusEnum.STATUS_AVAILABLE.getState()));
        fileResponseDTOArrayList.addAll(parseDocDirListToDTO(docDirRepository.findByParentAndStatus(parentId,FileStatusEnum.STATUS_AVAILABLE.getState())));
        return fileResponseDTOArrayList;
    }
    @Override
    public FileResponseDTO getParentFile(String childId){
        return parseDocDirToDTO(docDirRepository.findFirstByPidAndStatus(docDirRepository.findFirstByPidAndStatus(childId,FileStatusEnum.STATUS_AVAILABLE.getState()).getParent(),FileStatusEnum.STATUS_AVAILABLE.getState()));
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
        return parseDocDirToDTO(docDirRepository.findFirstByPidAndStatus(DirId,FileStatusEnum.STATUS_AVAILABLE.getState()));
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
            DocDir docDir = docDirRepository.findFirstByPidAndUserAndStatus(dirId,userId,FileStatusEnum.STATUS_AVAILABLE.getState());
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
        return parseDocDirToDTO(docDirRepository.findFirstByPidAndStatus(dirId,FileStatusEnum.STATUS_AVAILABLE.getState()));

    }

    @Override
    public ClassfiyRuleResponseDTO getClassfiyRule( String dirId){
        ClassfiyRuleResponseDTO classfiyRuleResponseDTO = new ClassfiyRuleResponseDTO();
        DocDir docDir = docDirRepository.findFirstByPidAndStatus(dirId,FileStatusEnum.STATUS_AVAILABLE.getState());
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
                    if(labelResponseDTO.getColor().equals("red")){
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
                    }else if(labelResponseDTO.getColor().equals("green")){
                        haveGreen=true;
                        if(fileResponseDTO.getLabels()!=null) {
                            for (LabelResponseDTO fileLabel : fileResponseDTO.getLabels()) {
                                if (fileLabel.getContent().equals(labelResponseDTO.getContent())) {
                                    isOr = true;
                                    break;
                                }
                            }
                        }
                    }else if(labelResponseDTO.getColor().equals("#52575c")){
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
    @Override
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
                    if(labelResponseDTO.getColor().equals("red")){
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
                    }else if(labelResponseDTO.getColor().equals("green")){
                        haveGreen=true;
                        if(file.getLabels()!=null) {
                            for (LabelResponseDTO fileLabel : file.getLabels()) {
                                if (fileLabel.getContent().equals(labelResponseDTO.getContent())) {
                                    isOr = true;
                                    break;
                                }
                            }
                        }
                    }else if(labelResponseDTO.getColor().equals("#52575c")){
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
    @Override
    public LabelGroupResponseDTO getCurDirLabelAndType(String dirId){
        LabelGroupResponseDTO labelGroupResponseDTO = new LabelGroupResponseDTO();
        ArrayList<String> fileTypes = new ArrayList<String>();
        ArrayList<LabelResponseDTO> labels= new ArrayList<LabelResponseDTO>();
        ArrayList<String> _labels = new ArrayList<String>();

        ArrayList<FileResponseDTO> fileResponseDTOS = getChildFile(dirId);
        for(FileResponseDTO fileResponseDTO : fileResponseDTOS){
            if(!fileTypes.contains(fileResponseDTO.getType())){
                fileTypes.add(fileResponseDTO.getType());
            }
            if(fileResponseDTO.getLabels()!=null){
                for(LabelResponseDTO labelResponseDTO: fileResponseDTO.getLabels()){
                    if(!_labels.contains(labelResponseDTO.getPid())){
                        labels.add(labelResponseDTO);
                        _labels.add(labelResponseDTO.getPid());
                    }
                }
            }
        }
        labelGroupResponseDTO.setFileTypes(fileTypes);
        labelGroupResponseDTO.setLabels(labels);
        return labelGroupResponseDTO;
    }
    @Override
    public RulesAndFileLIstResponseDTO selectFiles(String dirId,JSONArray labels,JSONArray fileTypes){
        ArrayList<FileResponseDTO> fileResponseDTOS = getChildFile(dirId);
        LabelGroupResponseDTO labelGroup = new LabelGroupResponseDTO();
        ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();
        try {
            for (FileResponseDTO fileResponseDTO : fileResponseDTOS) {
                boolean istType = true;
                if (fileTypes.length() > 0) {
                    istType = false;
                    for (int i = 0; i < fileTypes.length(); i++) {
//                        JSONObject fileType = new JSONObject(fileTypes.getString(i));
                        if(fileTypes.getString(i).equals(fileResponseDTO.getType())){
                            istType = true;
                        }
                    }
                }
                boolean isAnd=true;
                boolean isOr=false;
                boolean isNo=true;
                boolean haveGreen=false;
                if(labels.length()>0){
                    for(int i = 0 ;i<labels.length();i++){
                        JSONObject label = new JSONObject( labels.getString(i));
                        if(label.getString("color").equals("red")){
                            isAnd=false;
                            if(fileResponseDTO.getLabels()!=null){
                                for(LabelResponseDTO fileLabel:fileResponseDTO.getLabels()){
                                    if(fileLabel.getContent().equals(label.getString("content"))){
                                        isAnd=true;
                                        break;
                                    }
                                }
                            }
                            if(!isAnd){
                                break;
                            }
                        }else if(label.getString("color").equals("green")){
                            haveGreen=true;
                            if(fileResponseDTO.getLabels()!=null) {
                                for (LabelResponseDTO fileLabel : fileResponseDTO.getLabels()) {
                                    if (fileLabel.getContent().equals(label.getString("content"))) {
                                        isOr = true;
                                        break;
                                    }
                                }
                            }
                        }else if(label.getString("color").equals("#52575c")){
                            if(fileResponseDTO.getLabels()!=null) {
                                for (LabelResponseDTO fileLabel : fileResponseDTO.getLabels()) {
                                    if (fileLabel.getContent().equals(label.getString("content"))) {
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
                    if(isAnd&&isNo&&istType){
                        if((!haveGreen)||(haveGreen&&isOr)){
                            fileResponseDTOArrayList.add(fileResponseDTO);
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        labelGroup.setFileTypes(new ArrayList<>());
        labelGroup.setLabels(new ArrayList<>());
        labelGroup.setName("筛选结果：");
        RulesAndFileLIstResponseDTO rulesAndFileLIstResponseDTO = new RulesAndFileLIstResponseDTO();
        rulesAndFileLIstResponseDTO.setFileResponseDTOArrayList(fileResponseDTOArrayList);
        rulesAndFileLIstResponseDTO.setLabelGroup(labelGroup);
        return rulesAndFileLIstResponseDTO;

    }
    @Override
    public LabelTypeResponseDTO CreateLabelType(String userId,String name, String description){
        ArrayList<LabelType> labelTypes = labelTypeRepository.findByUser(userId);
        for(LabelType labelType:labelTypes){
            if(labelType.getName().equals(name)){
                throw new ExistException("标签类型已存在");
            }
        }
        LabelType labelType = labelTypeRepository.save(LabelType.labelTypeBuilder().withPid(Pid.getPid())
                .withUser(userId)
                .withName(name)
                .withDescription(description)
                .build());
        return parseLabelTypeToDTO(labelType);
    }
    @Override
    public LabelTypeDTOS GetAllLabelTypes(String userId){
        ArrayList<LabelType> labelTypes = labelTypeRepository.findByUser(userId);
        ArrayList<LabelType> _labelTypes = new ArrayList<LabelType>();
        LabelType labelType_else = new LabelType();
        LabelType labelType_no = new LabelType();
        for(LabelType labelType:labelTypes){
            if(labelType.getName().equals("未分组标签")){
                labelType_else = labelType;
            }else if(labelType.getName().equals("废弃标签")){
                labelType_no = labelType;
            }else {
                _labelTypes.add(labelType);
            }
        }
        _labelTypes.add(labelType_else);
//        _labelTypes.add(labelType_no);
        LabelTypeDTOS labelTypeDTOS = new LabelTypeDTOS();
        labelTypeDTOS.setLabelTypes(parseLabelTypeListToDTO(_labelTypes));
        return labelTypeDTOS;
    }
    @Override
    public LabelGroupDTOS getAllLabelsByGroup(String userId){
        ArrayList<LabelType> labelTypes = labelTypeRepository.findByUser(userId);
        ArrayList<LabelResponseDTO> labelResponseDTOS = getAllLabels(userId).getLabelResponseDTOArrayList();
        LabelGroupDTOS labelGroupDTOS = new LabelGroupDTOS();
        LabelGroupDTO labelGroupDTO_else = new LabelGroupDTO();
        ArrayList<LabelGroupDTO> labelGroups = new ArrayList<LabelGroupDTO>();
        for(LabelType labelType:labelTypes){
            LabelGroupDTO labelGroupDTO = new LabelGroupDTO();
            ArrayList<LabelResponseDTO> labels = new ArrayList<LabelResponseDTO>();
            for(LabelResponseDTO label :labelResponseDTOS){
                try {
//                    JSONObject type = new JSONObject(label.getType());
                    if(labelType.getName().equals(label.getType().getName())){
                        {
                            labels.add(label);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            for(LabelResponseDTO label:labels){
                labelResponseDTOS.remove(label);
            }
            labelGroupDTO.setLabels(labels);
            labelGroupDTO.setLabelType(parseLabelTypeToDTO(labelType));
            if(labelType.getName().equals("废弃标签")){
                labelGroupDTOS.setAbandon_labelGroup(labelGroupDTO);
            }else if(labelType.getName().equals("未分组标签")){
                labelGroupDTO_else = labelGroupDTO;
            }else {
                labelGroups.add(labelGroupDTO);
            }
        }
        labelGroups.add(labelGroupDTO_else);
        labelGroupDTOS.setLabelGroups(labelGroups);
        return labelGroupDTOS;
    }
    @Override
    @Transactional
    public ResponseDTO ChangeLabelTypes(JSONObject type,JSONObject label){
        try {
            JSONObject _type = new JSONObject();
            _type.put("pid",type.getString("pid"));
            _type.put("name",type.getString("name"));

            labelRepository.updateTypeByPid(_type.toString(),label.getString("pid"));
            return new ResponseDTO();
        }catch (Exception  e){
            e.printStackTrace();
            throw new UserCheckException("参数解析出错");
        }

    }
    @Override
    @Transactional
    public ResponseDTO DeleteLabelFromLabel(String userId,JSONObject label){
        try {
            LabelType labelType = labelTypeRepository.findFirstByUserAndName(userId,"废弃标签");
            JSONObject _type = new JSONObject();
            _type.put("pid",labelType.getPid());
            _type.put("name",labelType.getName());
            labelRepository.updateTypeByPid(_type.toString(),label.getString("pid"));
            return new ResponseDTO();
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析失败");
        }
    }
    @Override
    @Transactional
    public LabelResponseDTO newLabel(String userId,String name,String describe,JSONObject labelType){
        JSONObject _labelType = new JSONObject();


             Label label = labelRepository.findFirstByUserAndPid(userId,name);
             if(label!=null){
                 throw new ExistException("该标签已存在");
             }
        try {
             _labelType.put("name", labelType.getString("name"));
             _labelType.put("pid", labelType.getString("pid"));
             label = labelRepository.save(
                             Label.labelBuilder().withContent(name)
                            .withDescription(describe)
                            .withUser(userId)
                            .withPid(Pid.getPid())
                            .withType(_labelType.toString())
                            .build());
            return parseLabelToDTO(label);

        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析出错");
        }
    }
    @Override
    @Transactional
    public LabelGroupDTO deleteLabelType(String userId,String labelTypeId){
        LabelGroupDTO labelGroupDTO = new LabelGroupDTO();
        LabelTypeResponseDTO labelType = parseLabelTypeToDTO(labelTypeRepository.findFirstByUserAndName(userId,"废弃标签"));
        ArrayList<LabelResponseDTO> labelDTOs = getAboundandLabel(userId);

        ArrayList<Label> labels = labelRepository.findByUser(userId);
        for(Label label:labels){
            try{
                JSONObject _labelType = new JSONObject(label.getType());
                if(_labelType.getString("pid").equals(labelTypeId)){
                    JSONObject type = new JSONObject();
                    type.put("name",labelType.getName());
                    type.put("pid",labelType.getPid());
                    labelRepository.updateTypeByPid(type.toString(),label.getPid());
                    label.setType(type.toString());
                    labelDTOs.add(parseLabelToDTO(label));
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析失败");
            }
        }
        labelTypeRepository.deleteByPid(labelTypeId);
        labelGroupDTO.setLabelType(labelType);
        labelGroupDTO.setLabels(labelDTOs);
        return labelGroupDTO;
    }
    @Override
    @Transactional
    public ResponseDTO addLabelToFile(JSONObject file, JSONObject label){
        try{
            if(file.getString("type").equals(DocTypeEnum.DIR_TYPE_ENUM.getType())){
                DocDir docDir = docDirRepository.findFirstByPidAndStatus(file.getString("pid"),FileStatusEnum.STATUS_AVAILABLE.getState());
                JSONArray labels = new JSONArray(docDir.getLabel());
                labels.put(label);
                docDirRepository.updateLabelByPid(labels.toString(),file.getString("pid"));
            }else {
                DocFileInfo docFileInfo = docFileInfoRepository.findFirstByFileId(file.getString("pid"));
                JSONArray labels = new JSONArray(docFileInfo.getLabel());
                labels.put(label);
                docFileInfoRepository.updateLabelByFileid(labels.toString(),file.getString("pid"));
            }
            return new ResponseDTO();

        }catch (Exception e){
            e.printStackTrace();
            throw  new UserCheckException("参数解析失败");
        }
    }
    @Override
    @Transactional
    public ResponseDTO deleteLabelToFile(JSONObject file, String labelId){
        try{
            if(file.getString("type").equals(DocTypeEnum.DIR_TYPE_ENUM.getType())){
                DocDir docDir = docDirRepository.findFirstByPidAndStatus(file.getString("pid"),FileStatusEnum.STATUS_AVAILABLE.getState());
                JSONArray labels = new JSONArray(docDir.getLabel());
                for(int i=0;i<labels.length();i++){
                    JSONObject label = new JSONObject(labels.getString(i));
                    if(label.getString("pid").equals(labelId)){
                        labels.remove(i);
                        break;
                    }
                }
                docDirRepository.updateLabelByPid(labels.toString(),file.getString("pid"));
            }else {
                DocFileInfo docFileInfo = docFileInfoRepository.findFirstByFileId(file.getString("pid"));
                JSONArray labels = new JSONArray(docFileInfo.getLabel());
                for(int i=0;i<labels.length();i++){
                    JSONObject label = new JSONObject(labels.getString(i));
                    if(label.getString("pid").equals(labelId)){
                        labels.remove(i);
                        break;
                    }
                }
                docFileInfoRepository.updateLabelByFileid(labels.toString(),file.getString("pid"));
            }
            return new ResponseDTO();

        }catch (Exception e){
            e.printStackTrace();
            throw  new UserCheckException("参数解析失败");
        }
    }
    @Override
    public ResponseDTO DownLoadFile(String fileId, HttpServletResponse res){
        DocFile docFile=docFileRepository.findFirstByPidAndStatus(fileId, FileStatusEnum.STATUS_AVAILABLE.getState());
        String userId = docFile.getUser();
        String fileName = docFile.getName()+"."+docFile.getPostfix().toLowerCase();
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setCharacterEncoding("utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream os = null;
        try {
            os = res.getOutputStream();
        }catch (IOException e){
            e.printStackTrace();
            throw new UserCheckException("下载出错");
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(new File(SeverPathEnum.FILE_PATH.getPath() +"//"+userId+"//file//"
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserCheckException("下载出错");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new UserCheckException("下载出错");
                }
            }
        }
        return new ResponseDTO();
    }
    @Override
    @Transactional
    public ResponseDTO DeleteFile(String fileId){
        DocFile docFile = docFileRepository.findFirstByPidAndStatus(fileId,FileStatusEnum.STATUS_AVAILABLE.getState());
        if(docFile!=null){
            docFileRepository.updateStatusByPid(FileStatusEnum.STATUS_DELETED.getState(),fileId);
        }else {
            docDirRepository.updateStatusByPid(FileStatusEnum.STATUS_DELETED.getState(),fileId);
        }

        return new ResponseDTO();
    }
    @Override
    public ShareDTO ShareFile(String userId,String fileId,String isPrivate,String shareTypeCode){
        DocFile docFile = docFileRepository.findFirstByPidAndStatus(fileId,FileStatusEnum.STATUS_AVAILABLE.getState());
        String fileType ;
        if(docFile==null){
            fileType="dir";
        }else {
            fileType=docFile.getType().toLowerCase();
        }
        JSONArray shareContents = new JSONArray();
        JSONObject shareContent = new JSONObject();
        try {
            shareContent.put("pid", fileId);
            shareContent.put("type",fileType);
            shareContents.put(shareContent);

        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析失败");
        }
        String password=null;
        if(isPrivate.equals("yes")){
            password = Pid.getPid().substring(0,5);
        }
        FileShare fileShare = fileShareRepository.save(
                FileShare.fileShareBuilder()
                        .withContent(shareContents.toString())
                        .withpid(Pid.getPid())
                        .withType(shareTypeCode)
                        .withStatus(FileStatusEnum.STATUS_AVAILABLE.getState())
                        .withPassworld(password)
                        .withUser(userId)
                        .build()

        );
        ShareDTO shareDTO = new ShareDTO();
        shareDTO.setPassworld(password);
        shareDTO.setShare_url(SeverPathEnum.WEB_PATH.getPath()+"/#/share/"+fileShare.getPid());
        return shareDTO;
    }
    @Override
    public CheckShareDTO GetShareFile(String shareId){
        CheckShareDTO checkShareDTO = new CheckShareDTO();
        FileShare fileShare=fileShareRepository.findFirstByPidAndStatus(shareId,FileStatusEnum.STATUS_AVAILABLE.getState());
        if(fileShare!=null){
            if(checkShareOutOfTime(fileShare)){
                if(fileShare.getPassworld()!=null){
                    checkShareDTO.setCode("0");//该分享有密码
                }else {
                    checkShareDTO.setCode("1");//没有密码
                    try {
                        JSONArray contents = new JSONArray(fileShare.getContent());
                        JSONObject content = new JSONObject(contents.getString(0));
                        if(content.getString("type")!="dir"){
                            DocFile docFile = docFileRepository.findFirstByPidAndStatus(content.getString("pid"),FileStatusEnum.STATUS_AVAILABLE.getState());
                            checkShareDTO.setFile(parseDocFileToDTO(docFile));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new UserCheckException("参数解析出错");
                    }

                }
            }else {
                checkShareDTO.setCode("2");//该分享已失效
            }

        }else {
            checkShareDTO.setCode("3");//该分享不存在
        }
        return checkShareDTO;
    }
    @Override
    public CheckShareDTO GetShareFileByPassword(String shareId,String password){
        CheckShareDTO checkShareDTO = new CheckShareDTO();
        FileShare fileShare=fileShareRepository.findFirstByPidAndStatus(shareId,FileStatusEnum.STATUS_AVAILABLE.getState());
        if(password.equals(fileShare.getPassworld())){
            try {
                JSONArray contents = new JSONArray(fileShare.getContent());
                JSONObject content = new JSONObject(contents.getString(0));
                if(content.getString("type")!="dir"){
                    DocFile docFile = docFileRepository.findFirstByPidAndStatus(content.getString("pid"),FileStatusEnum.STATUS_AVAILABLE.getState());
                    checkShareDTO.setFile(parseDocFileToDTO(docFile));
                    checkShareDTO.setCode("4");//密码正确
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }
        }else {
            checkShareDTO.setCode("5");//密码错误
        }
        return checkShareDTO;
    }
    @Override
    public boolean  checkShareOutOfTime(FileShare fileShare){
        Timestamp createDate = fileShare.getCreateDate();
        long days=(System.currentTimeMillis()-createDate.getTime())/(1000*60*60);
        switch (fileShare.getType()){
            case "0":
                if(days>1){
                    return false;
                }
                break;
            case "1":
                if(days>3){
                    return false;
                }
                break;
            case "2":
                if(days>7){
                    return false;
                }
                break;
            case "3":
                if(days>30){
                    return false;
                }
                break;
            case "4":
                return true;
                default:
                    break;
        }
        return true;

    }
    private  ArrayList<LabelResponseDTO> getAboundandLabel(String userId){
        ArrayList<Label> labels = labelRepository.findByUser(userId);
        ArrayList<LabelResponseDTO> labelResponseDTOS = new ArrayList<LabelResponseDTO>();
        for(Label label:labels){
            try{
                JSONObject _labelType = new JSONObject(label.getType());
                if(_labelType.getString("name").equals("废弃标签")){
                    labelResponseDTOS.add(parseLabelToDTO(label));
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析失败");
            }
        }
        return labelResponseDTOS;
    }
    private  LabelTypeResponseDTO parseLabelTypeToDTO(LabelType labelType){
        LabelTypeResponseDTO labelTypeResponseDTO = new LabelTypeResponseDTO();
        labelTypeResponseDTO.setPid(labelType.getPid());
        labelTypeResponseDTO.setName(labelType.getName());
        labelTypeResponseDTO.setDescription(labelType.getDescription());
        return labelTypeResponseDTO;
    }
    private ArrayList<LabelTypeResponseDTO> parseLabelTypeListToDTO(ArrayList<LabelType> labelTypes){
        ArrayList<LabelTypeResponseDTO> labelTypeResponseDTOS = new ArrayList<LabelTypeResponseDTO>();
        for(LabelType labelType:labelTypes){
            labelTypeResponseDTOS.add(parseLabelTypeToDTO(labelType));
        }
        return labelTypeResponseDTOS;
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
        try {
            LabelTypeResponseDTO labelType = new LabelTypeResponseDTO();
            JSONObject _labelType = new JSONObject(label.getType());
            labelType.setPid(_labelType.getString("pid"));
            labelType.setName(_labelType.getString("name"));
            labelType.setDescription(_labelType.getString("name"));
            labelResponseDTO.setType(labelType);
        }catch (Exception e){
            e.printStackTrace();
        }
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
