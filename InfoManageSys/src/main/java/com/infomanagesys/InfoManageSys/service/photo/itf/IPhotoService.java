package com.infomanagesys.InfoManageSys.service.photo.itf;

import org.springframework.web.multipart.MultipartFile;

public interface IPhotoService {
    public void  uploadImg(String userId, String description, MultipartFile img);
}
