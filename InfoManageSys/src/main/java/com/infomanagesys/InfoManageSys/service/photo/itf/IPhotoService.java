package com.infomanagesys.InfoManageSys.service.photo.itf;

import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDataDTOS;
import org.json.JSONArray;
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
}
