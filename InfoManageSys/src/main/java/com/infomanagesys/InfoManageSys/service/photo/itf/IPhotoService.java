package com.infomanagesys.InfoManageSys.service.photo.itf;

import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDataDTOS;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.ImgApiDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface IPhotoService {
    public void  uploadImg(String userId, String description, MultipartFile img);
    public AblumDataDTOS CreateAlbum(String userId, String description, String name, JSONArray labels);
    public AblumDTO GetRootAblum(String userId);
    public ResponseDTO openAblum(String userId,String ablumId);
    public AblumDTO getCurrentAblum(String userId);
    public AblumDataDTOS getCurAblumData(String ablumId);
    public AblumDTO getWeiFenLeiAblum(String userId);
    public AblumDTO getParentAblum(String ablumId);
    public AblumDTO getAblumByPhoto(Photo photo,String userId);
    public AblumDataDTOS changeAblumImg(String ablumId,String photoId);
    public ResponseDTO addLabelToAblum(String ablumId,String labelId, String labelContent);
    public ResponseDTO removeLabelFromAblum(String ablumId,String labelId);
    public ResponseDTO addLabelToPhoto(String photoId, JSONObject label);
    public ResponseDTO removeLabelFromPhoto(String photoId,String labelId);
    public AblumDataDTOS deleteAblum(String userId,String ablumId);
    public AblumDataDTOS deletePhoto(String userId,String photoId);
    public ImgApiDTO uploadAndRecImg(String userId, MultipartFile img,String typeCode);
}
