package com.infomanagesys.InfoManageSys.service.photo.impl;

import com.infomanagesys.InfoManageSys.dao.repository.TempTableRepository;
import com.infomanagesys.InfoManageSys.dao.repository.photo.PhotoAlbumRepository;
import com.infomanagesys.InfoManageSys.dao.repository.photo.PhotoRepository;
import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocDir;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFileInfo;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.Label;
import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import com.infomanagesys.InfoManageSys.dataobject.entity.photo.PhotoAlbum;
import com.infomanagesys.InfoManageSys.dataobject.enums.DocTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.FileStatusEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.SeverPathEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.TempTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelTypeResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDataDTOS;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.PhotoDTO;
import com.infomanagesys.InfoManageSys.exception.FileExistException;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.photo.itf.IPhotoService;
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

@Service
public class PhotoServiceImpl implements IPhotoService {


    @Autowired
    private ImgRecServiceImpl imgRecService;

    private final TempTableRepository tempTableRepository;
    private final PhotoRepository photoRepository;
    private final PhotoAlbumRepository photoAlbumRepository;
    @Autowired
    public PhotoServiceImpl(final TempTableRepository tempTableRepository,
                            final PhotoRepository photoRepository,
                            final PhotoAlbumRepository photoAlbumRepository){
        this.tempTableRepository=tempTableRepository;
        this.photoRepository=photoRepository;
        this.photoAlbumRepository=photoAlbumRepository;
    }

    @Override
    public void  uploadImg(String userId,String description ,MultipartFile img) {

        double imgSize =0;
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
            fos = new FileOutputStream(new File( SeverPathEnum.FILE_PATH.getPath()+"//"+userId+"//photo//"+photo.getPid()+"."+photo.getType()));
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1){
                imgSize++;
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

        imgSize = imgSize/1024;
        photo.setLabels(LabelListToString(imgRecService.BaiDuImgRec(photo,userId,imgSize)));
        photo.setGroupId(getAblumByPhoto(photo,userId).getPid());
        photoRepository.save(photo);

//    FileResponseDTO fileResponseDTO = parseDocFileToDTO(docFile);
//    fileResponseDTO.setLabels(labelResponseDTOArrayList);
//    RuleAndFileResponseDTO ruleAndFileResponseDTO = new RuleAndFileResponseDTO();
//    ruleAndFileResponseDTO.setLabelGroup(getLabelGroupByFile(parentDir.getPid(),fileResponseDTO));
//    ruleAndFileResponseDTO.setFile(fileResponseDTO);
//    return ruleAndFileResponseDTO;
    }
    private AblumDTO getAblumByphoto(Photo photo,AblumDTO parentAblum,String userId){
        ArrayList<PhotoAlbum> photoAlbums = photoAlbumRepository.findByParentIdAndStatus(parentAblum.getPid(),FileStatusEnum.STATUS_AVAILABLE.getState());
        ArrayList<AblumDTO>  ablumDTOS = parsePhotoAlbumListToDTO(photoAlbums);
        PhotoDTO photoDTO = parsePhotoToDTO(photo);
        AblumDTO _ablumDTO = null;
        LabelResponseDTO _label=null;
        if(photoAlbums.size()>0){
            for(AblumDTO ablumDTO:ablumDTOS){
                LabelResponseDTO label = isBelongToAblum(photoDTO,ablumDTO);
                if(label!=null){
                    if(_label!=null){
                        if((label.getScore()-_label.getScore())>0.2){
                            _label=label;
                            _ablumDTO =ablumDTO;
                        }
                    }else {
                        _label=label;
                        _ablumDTO =ablumDTO;
                    }
                }
            }
            if(_ablumDTO==null){
                if(parentAblum.getName().equals("root")){
                    _ablumDTO = getWeiFenLeiAblum(userId);
                }else {
                    _ablumDTO = parentAblum;
                }
            }

        }else {
            _ablumDTO= parentAblum;
        }

        if(!_ablumDTO.getPid().equals(parentAblum.getPid())){
            _ablumDTO = getAblumByphoto(photo,_ablumDTO,userId);
        }
       return _ablumDTO;
    }
    @Override
    public AblumDTO getAblumByPhoto(Photo photo,String userId){
        AblumDTO ablumDTO = getCurrentAblum(userId);
        ablumDTO = getAblumByphoto(photo,ablumDTO,userId);
         return ablumDTO;
    }
    private LabelResponseDTO isBelongToAblum(PhotoDTO photoDTO,AblumDTO ablumDTO){
        LabelResponseDTO _label=null;
        for(LabelResponseDTO photoLabel:photoDTO.getLabels()){
            for(LabelResponseDTO label : ablumDTO.getLabels()){
                if(photoLabel.getPid().equals(label.getPid())){
                    if(_label!=null){
                        if(_label.getScore()<photoLabel.getScore()){
                            _label=photoLabel;
                            break;
                        }
                    }else {
                        _label=photoLabel;
                        break;
                    }
                }
            }
        }
        return _label;
    }
    @Override
    public AblumDataDTOS CreateAlbum(String userId, String description, String name, JSONArray labels){
        AblumDTO ablumDTO = getCurrentAblum(userId);
        PhotoAlbum photoAlbum = photoAlbumRepository.findFirstByUserAndNameAndStatusAndParentId(userId,name,FileStatusEnum.STATUS_AVAILABLE.getState(),ablumDTO.getPid());
        if(photoAlbum!=null){
            throw new FileExistException("已存在同名相册");
        }else {
            photoAlbum = photoAlbumRepository.save(
              PhotoAlbum.photoAlbumBuilder()
                      .withLabels(labels.toString())
                      .withName(name)
                      .withDescription(description)
                      .withParentId(ablumDTO.getPid())
                      .withUser(userId)
                      .withImg(SeverPathEnum.FILE_SEVER_PATH.getPath()+"/icon/DEFAULT_ABLUM.jpg")
                      .withStatus(FileStatusEnum.STATUS_AVAILABLE.getState())
                      .withPid(Pid.getPid())
                      .withLevel(photoAlbumRepository.countAllByParentId(ablumDTO.getPid()))
                      .build()
            );
        }
        ArrayList<Photo> photos= new ArrayList<Photo>();
        if(ablumDTO.getName().equals("root")){
            photos = photoRepository.findByGroupIdAndStatus(getWeiFenLeiAblum(userId).getPid(),FileStatusEnum.STATUS_AVAILABLE.getState());
        }else {
            photos = photoRepository.findByGroupIdAndStatus(ablumDTO.getPid(),FileStatusEnum.STATUS_AVAILABLE.getState());
        }
        for(Photo photo:photos){
            if(isBelongToAblum(parsePhotoToDTO(photo),parsePhotoAlbumToDTO(photoAlbum))!=null){
                photo.setGroupId(photoAlbum.getPid());
                photoRepository.save(photo);
            }
        }
        return getCurAblumData(ablumDTO.getPid());
    }
    @Override
    @Transactional
    public ResponseDTO openAblum(String userId,String ablumId){
        if(tempTableRepository.findFirstByTempUserAndTempType(userId, TempTypeEnum.TEMP_TYPE_CURRENTABLUM.getKey())!=null)
        {
            tempTableRepository.updateValue(ablumId,userId,TempTypeEnum.TEMP_TYPE_CURRENTABLUM.getKey());
        }else{
            tempTableRepository.save(TempTable.tempTableBuilder()
                    .withTempKey(userId)
                    .withTempValue(ablumId)
                    .withTempType(TempTypeEnum.TEMP_TYPE_CURRENTABLUM.getKey())
                    .withTempUser(userId).build());
        }
        return new ResponseDTO();
    }
    @Override
    public AblumDTO GetRootAblum(String userId){
        PhotoAlbum photoAlbum = photoAlbumRepository.findFirstByUserAndNameAndStatusAndParentId(userId,"root",FileStatusEnum.STATUS_AVAILABLE.getState(),"root");
        return parsePhotoAlbumToDTO(photoAlbum);
    }
    @Override
    public AblumDTO getCurrentAblum(String userId){
        TempTable tempTable= tempTableRepository.findFirstByTempUserAndTempType(userId,TempTypeEnum.TEMP_TYPE_CURRENTABLUM.getKey());
        PhotoAlbum photoAlbum = photoAlbumRepository.findFirstByPidAndStatus(tempTable.getTempValue(),FileStatusEnum.STATUS_AVAILABLE.getState());
        return parsePhotoAlbumToDTO(photoAlbum);
    }
    @Override
    public AblumDataDTOS getCurAblumData(String ablumId){
        AblumDataDTOS ablumDataDTOS = new AblumDataDTOS();
        ArrayList<PhotoAlbum> photoAlbums = photoAlbumRepository.findByParentIdAndStatus(ablumId,FileStatusEnum.STATUS_AVAILABLE.getState());
        ArrayList<Photo> photos = photoRepository.findByGroupIdAndStatus(ablumId,FileStatusEnum.STATUS_AVAILABLE.getState());
        ablumDataDTOS.setAblums(parsePhotoAlbumListToDTO(photoAlbums));
        ablumDataDTOS.setPhotos(parsePhotoListToDTO(photos));
        return ablumDataDTOS;
    }
    @Override
    public AblumDTO getWeiFenLeiAblum(String userId){
        PhotoAlbum photoAlbum = photoAlbumRepository.findFirstByUserAndStatusAndParentId(userId,FileStatusEnum.STATUS_AVAILABLE.getState(),"WEI_FEN_LEI");
        return parsePhotoAlbumToDTO(photoAlbum);
    }
    @Override
    public AblumDTO getParentAblum(String ablumId){
        PhotoAlbum photoAlbum = photoAlbumRepository.findFirstByPidAndStatus(ablumId,FileStatusEnum.STATUS_AVAILABLE.getState());
        PhotoAlbum parentAblum = photoAlbumRepository.findFirstByPidAndStatus(photoAlbum.getParentId(),FileStatusEnum.STATUS_AVAILABLE.getState());

        return parsePhotoAlbumToDTO(parentAblum);
    }
    @Override
    @Transactional
    public AblumDataDTOS changeAblumImg(String ablumId,String photoId){
        AblumDataDTOS ablumDataDTOS = new AblumDataDTOS();
        PhotoAlbum photoAlbum = photoAlbumRepository.findFirstByPidAndStatus(ablumId,FileStatusEnum.STATUS_AVAILABLE.getState());
        Photo photo = photoRepository.findFirstByPidAndStatus(photoId,FileStatusEnum.STATUS_AVAILABLE.getState());
        String img=SeverPathEnum.FILE_SEVER_PATH.getPath()+"/userData/"+photo.getUser()+"/photo/"+photo.getPid()+"."+photo.getType();
        photoAlbum.setImg(img);
        photoAlbumRepository.save(photoAlbum);
        ArrayList<PhotoAlbum> photoAlbums = photoAlbumRepository.findByParentIdAndStatus(getParentAblum(ablumId).getPid(),FileStatusEnum.STATUS_AVAILABLE.getState());
        ablumDataDTOS.setAblums(parsePhotoAlbumListToDTO(photoAlbums));
        return ablumDataDTOS;
    }

    private ArrayList<PhotoDTO> parsePhotoListToDTO(ArrayList<Photo> photos){
        ArrayList<PhotoDTO> photoDTOS = new ArrayList<PhotoDTO>();
        for (Photo photo:photos){
            photoDTOS.add(parsePhotoToDTO(photo));
        }
        return photoDTOS;
    }
    private PhotoDTO parsePhotoToDTO(Photo photo){
        PhotoDTO photoDTO = new PhotoDTO();
        if(photo.getDescription()!=null){
            photoDTO.setDescription(photo.getDescription());
        }
        photoDTO.setPid(photo.getPid());
        photoDTO.setGroup_id(photo.getGroupId());
        photoDTO.setName(photo.getName());
        photoDTO.setType(photo.getType());
        photoDTO.setSrc(SeverPathEnum.FILE_SEVER_PATH.getPath()+"/userData/"+photo.getUser()+"/photo/"+photo.getPid()+"."+photo.getType());
        if(photo.getLabels()!=null){
            try {
                ArrayList<LabelResponseDTO> labelResponseDTOArrayList = new ArrayList<LabelResponseDTO>();
                JSONArray labels = new JSONArray(photo.getLabels());
                for(int i=0;i<labels.length();i++){
                    JSONObject label= new JSONObject(labels.getString(i));
                    LabelResponseDTO labelResponseDTO = new LabelResponseDTO();

                    labelResponseDTO.setContent(label.getString("content"));
                    labelResponseDTO.setPid(label.getString("pid"));
                    try{
                        if(label.getString("discription")!=null) {
                            labelResponseDTO.setDiscription(label.getString("discription"));
                        }
                    }catch (Exception e){
                    }
                    labelResponseDTOArrayList.add(labelResponseDTO);
                }
                photoDTO.setLabels(labelResponseDTOArrayList);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析失败");
            }
        }
        return photoDTO;
    }
    private ArrayList<AblumDTO> parsePhotoAlbumListToDTO(ArrayList<PhotoAlbum> photoAlbums){
        ArrayList<AblumDTO> albumDTOs = new ArrayList<AblumDTO>();
        for(PhotoAlbum photoAlbum:photoAlbums){
            albumDTOs.add(parsePhotoAlbumToDTO(photoAlbum));
        }
        return albumDTOs;
    }
    private AblumDTO parsePhotoAlbumToDTO(PhotoAlbum photoAlbum){
        AblumDTO ablumDTO = new AblumDTO();
        if(photoAlbum.getDescription()!=null){
            ablumDTO.setDescription(photoAlbum.getDescription());
        }
        try {
            ArrayList<LabelResponseDTO> labelResponseDTOArrayList = new ArrayList<LabelResponseDTO>();
            JSONArray labels = new JSONArray(photoAlbum.getLabels());
            for(int i=0;i<labels.length();i++){
                JSONObject label= new JSONObject(labels.getString(i));
                LabelResponseDTO labelResponseDTO = new LabelResponseDTO();

                labelResponseDTO.setContent(label.getString("content"));
                labelResponseDTO.setPid(label.getString("pid"));
                try{
                    if(label.getString("discription")!=null) {
                        labelResponseDTO.setDiscription(label.getString("discription"));
                    }
                }catch (Exception e){
                }

                labelResponseDTOArrayList.add(labelResponseDTO);
            }
            ablumDTO.setLabels(labelResponseDTOArrayList);
            ablumDTO.setName(photoAlbum.getName());
            ablumDTO.setPid(photoAlbum.getPid());
            ablumDTO.setType(DocTypeEnum.ABLUM_TYPE_ENUM.getType());
            ablumDTO.setLevel(photoAlbum.getLevel());
            ablumDTO.setIconPath(photoAlbum.getImg());
            return ablumDTO;
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析失败");
        }
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

    private String LabelListToString(ArrayList<LabelResponseDTO> labelResponseDTOArrayList){
        JSONArray labels = new JSONArray();
        for (LabelResponseDTO labelResponseDTO: labelResponseDTOArrayList) {
            labels.put(labelResponseDTO.toString());
        }
        return labels.toString();
    }

}
