package com.infomanagesys.InfoManageSys.service.itf;

import org.springframework.web.multipart.MultipartFile;

public interface ITestService {
     public void InsertIntoUser();
     public String getNameById();
     String getNameFromRedis();
     public Boolean uploadFile(MultipartFile file);
}
