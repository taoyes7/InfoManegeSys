package com.infomanagesys.InfoManageSys.service.doc.impl;

import com.infomanagesys.InfoManageSys.dao.repository.TempTableRepository;
import com.infomanagesys.InfoManageSys.dao.repository.doc.*;
import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFileInfo;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.DirClassifyRules;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.Label;
import com.infomanagesys.InfoManageSys.dataobject.enums.DocTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.TempTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.AllLabelResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.FileResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
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

    @Autowired
    public DocServiceImpl(final DocFileRepository docFileRepository,
                          final DocDirRepository docDirRepository,
                          final TempTableRepository tempTableRepository,
                          final NLPServiceImpl nlpService,
                          final DocFileInfoRepository docFileInfoRepository,
                          final DirClassifyRulesRepository dirClassifyRulesRepository,
                          final LabelRepository labelRepository){
        this.docFileRepository=docFileRepository;
        this.docDirRepository=docDirRepository;
        this.tempTableRepository=tempTableRepository;
        this.nlpService=nlpService;
        this.docFileInfoRepository = docFileInfoRepository;
        this.dirClassifyRulesRepository = dirClassifyRulesRepository;
        this.labelRepository=labelRepository;
    }
    @Override
    public FileResponseDTO uploadFile(String userId, MultipartFile file){

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
        return fileResponseDTO;
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
    public ArrayList<FileResponseDTO> openNewDir(String userId, String dirId){
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

            return getChildFile(dirId);
    }
    @Override
    @Transactional
    public ArrayList<FileResponseDTO> openRootDir(String userId){
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

        return getChildFile(dirId);
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
