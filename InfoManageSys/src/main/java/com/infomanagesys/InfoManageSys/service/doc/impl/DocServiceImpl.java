package com.infomanagesys.InfoManageSys.service.doc.impl;

import com.infomanagesys.InfoManageSys.dao.repository.TempTableRepository;
import com.infomanagesys.InfoManageSys.dao.repository.doc.DocDirRepository;
import com.infomanagesys.InfoManageSys.dao.repository.doc.DocFileInfoRepository;
import com.infomanagesys.InfoManageSys.dao.repository.doc.DocFileRepository;
import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFileInfo;
import com.infomanagesys.InfoManageSys.dataobject.enums.DocTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.TempTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.FileResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
import com.infomanagesys.InfoManageSys.exception.FileExistException;
import com.infomanagesys.InfoManageSys.service.doc.itf.IDocService;
import com.infomanagesys.InfoManageSys.util.Pid;
import org.json.JSONArray;
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

    @Autowired
    public DocServiceImpl(final DocFileRepository docFileRepository,
                          final DocDirRepository docDirRepository,
                          final TempTableRepository tempTableRepository,
                          final NLPServiceImpl nlpService,
                          final DocFileInfoRepository docFileInfoRepository){
        this.docFileRepository=docFileRepository;
        this.docDirRepository=docDirRepository;
        this.tempTableRepository=tempTableRepository;
        this.nlpService=nlpService;
        this.docFileInfoRepository = docFileInfoRepository;
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

        DocFile docFile1=docFileRepository.findFirstByNameAndPostfix(uploadFileName,DocTypeEnum.getEnumByName(uploadFileSuffix).getName());
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

        TempTable tempTable=tempTableRepository.findFirstByTempUserAndTempType(userId, TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey());
        DocDir parentDir=docDirRepository.findFirstByPid(tempTable.getTempValue());
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
        ArrayList<LabelResponseDTO> labelResponseDTOArrayList=nlpService.GetLabelsByWorldFile(docFile);

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
        if(docDirRepository.findFirstByName(dirName)!=null){
            throw new FileExistException("已存在同名文件夹");
        }
        TempTable tempTable=tempTableRepository.findFirstByTempUserAndTempType(userId, TempTypeEnum.TEMP_TYPE_CURRENTDIR.getKey());
        if(tempTable!=null){
            DocDir parentDir=docDirRepository.findFirstByPid(tempTable.getTempValue());
            DocDir docDir = DocDir.docDirBuilder()
                    .withPid(Pid.getPid())
                    .withName(dirName)
                    .withtParent(parentDir.getPid())
                    .withPath(parentDir.getPath()+"/"+dirName)
                    .withUser(userId)
                    .withStatus("0")
                    .withLevel(parentDir.getLevel()+1).build();
            docDirRepository.save(docDir);
            return parseDocDirToDTO(docDir);
        }else {
            return null;
        }
    }
    @Override
    public Boolean createRootDir(String userId){

        UUID uuid = UUID.randomUUID();
        docDirRepository.save(DocDir.docDirBuilder().withName("root")
                .withPath("/")
                .withtParent("null")
                .withUser(userId)
                .withStatus("0")
                .withLevel(0)
                .withPid(uuid.toString().replaceAll("-",""))
                .build());

        return true;
    }
    @Override
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
    private FileResponseDTO parseDocDirToDTO(DocDir docDir){
        FileResponseDTO fileResponseDTO = new FileResponseDTO();
        fileResponseDTO.setName(docDir.getName());
        fileResponseDTO.setPath(docDir.getPath());
        fileResponseDTO.setPid(docDir.getPid());
        fileResponseDTO.setType(DocTypeEnum.DIR_TYPE_ENUM.getType());
        return fileResponseDTO;
    }
    private FileResponseDTO parseDocFileToDTO(DocFile docFile){
        FileResponseDTO fileResponseDTO = new FileResponseDTO();
        fileResponseDTO.setName(docFile.getName());
        fileResponseDTO.setPath(docFile.getPath());
        fileResponseDTO.setPid(docFile.getPid());
        fileResponseDTO.setType(docFile.getType());
        fileResponseDTO.setIconPath(DocTypeEnum.getEnumByName(docFile.getPostfix().toLowerCase()).getIconPath());
        return fileResponseDTO;
    }
    private  ArrayList<FileResponseDTO> parseDocFileListToDTO(ArrayList<DocFile> docFileArrayList){
        ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();
        for (DocFile docFile:docFileArrayList) {
            FileResponseDTO fileResponseDTO = new FileResponseDTO();
            fileResponseDTO.setName(docFile.getName());
            fileResponseDTO.setPath(docFile.getPath());
            fileResponseDTO.setPid(docFile.getPid());
            fileResponseDTO.setType(docFile.getType());
            fileResponseDTOArrayList.add(fileResponseDTO);
        }
        return fileResponseDTOArrayList;
    }
    private ArrayList<FileResponseDTO> parseDocDirListToDTO(ArrayList<DocDir> docDirArrayList){
        ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();
        for (DocDir docDir: docDirArrayList) {
            FileResponseDTO fileResponseDTO = new FileResponseDTO();
            fileResponseDTO.setName(docDir.getName());
            fileResponseDTO.setPath(docDir.getPath());
            fileResponseDTO.setPid(docDir.getPid());
            fileResponseDTO.setType(DocTypeEnum.DIR_TYPE_ENUM.getType());
            fileResponseDTOArrayList.add(fileResponseDTO);
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
}
