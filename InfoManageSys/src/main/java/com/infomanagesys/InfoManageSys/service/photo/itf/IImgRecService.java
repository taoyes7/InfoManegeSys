package com.infomanagesys.InfoManageSys.service.photo.itf;

import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDTO;
import org.json.JSONArray;

import java.util.ArrayList;

public interface IImgRecService {
    public Object ImgRec(Photo photo, String userId,String url);
    public ArrayList<LabelResponseDTO> BaiDuImgRec(Photo photo, String userId,double imgsize);


}
