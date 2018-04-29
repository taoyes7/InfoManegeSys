package com.infomanagesys.InfoManageSys.service.photo.impl;

import com.infomanagesys.InfoManageSys.dao.repository.TempTableRepository;
import com.infomanagesys.InfoManageSys.dao.repository.photo.PhotoRepository;
import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFileInfo;
import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import com.infomanagesys.InfoManageSys.dataobject.enums.DocTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.FileStatusEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.SeverPathEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.TempTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
import com.infomanagesys.InfoManageSys.exception.FileExistException;
import com.infomanagesys.InfoManageSys.service.photo.itf.IPhotoService;
import com.infomanagesys.InfoManageSys.util.Pid;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class PhotoServiceImpl implements IPhotoService {


    @Autowired
    private ImgRecServiceImpl imgRecService;

    private final TempTableRepository tempTableRepository;
    private final PhotoRepository photoRepository;
    @Autowired
    public PhotoServiceImpl(final TempTableRepository tempTableRepository,
                            final PhotoRepository photoRepository){
        this.tempTableRepository=tempTableRepository;
        this.photoRepository=photoRepository;
    }

    @Override
    public void  uploadImg(String userId,String description ,MultipartFile img) {


        // 获取上传文件的全名(包含后缀名)
        String imgFullName = img.getOriginalFilename();
        System.out.println("imgFullName:" + imgFullName);
        // 截取上传文件的文件名
        String imgName = imgFullName.substring(
                imgFullName.lastIndexOf('\\') + 1, imgFullName.indexOf('.'));
        System.out.println("multiReq.getFile()" + imgName);
        // 截取上传文件的后缀
        String imgSuffix = imgFullName.substring(
                imgFullName.indexOf('.') + 1, imgFullName.length());
        System.out.println("uploadFileSuffix:" + imgSuffix);


        Photo photo = Photo.photoBuilder()
                .withDescription(description)
                .withGroupId("GroupId")
                .withPid(Pid.getPid())
                .withName(imgName)
                .withType(imgSuffix)
                .withStatus(FileStatusEnum.STATUS_AVAILABLE.getState())
                .withUser(userId)
                .withDescription(description)
                .build();

        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = (FileInputStream) img.getInputStream();
            fos = new FileOutputStream(new File( SeverPathEnum.FILE_PATH.getPath()+"/"+userId+"/photo/"+photo.getPid()+"."+photo.getType()));
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


        photo.setLabels(LabelListToString(imgRecService.BaiDuImgRec(photo,userId)));
        photoRepository.save(photo);

//    FileResponseDTO fileResponseDTO = parseDocFileToDTO(docFile);
//    fileResponseDTO.setLabels(labelResponseDTOArrayList);
//    RuleAndFileResponseDTO ruleAndFileResponseDTO = new RuleAndFileResponseDTO();
//    ruleAndFileResponseDTO.setLabelGroup(getLabelGroupByFile(parentDir.getPid(),fileResponseDTO));
//    ruleAndFileResponseDTO.setFile(fileResponseDTO);
//    return ruleAndFileResponseDTO;
    }

    private String LabelListToString(ArrayList<LabelResponseDTO> labelResponseDTOArrayList){
        JSONArray labels = new JSONArray();
        for (LabelResponseDTO labelResponseDTO: labelResponseDTOArrayList) {
            labels.put(labelResponseDTO.toString());
        }
        return labels.toString();
    }
}
