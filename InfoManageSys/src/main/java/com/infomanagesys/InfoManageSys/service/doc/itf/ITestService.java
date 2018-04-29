package com.infomanagesys.InfoManageSys.service.doc.itf;

import org.springframework.web.multipart.MultipartFile;

public interface ITestService {
     public void InsertIntoUser();
     public String getNameById();
     String getNameFromRedis();

//     public void testApi();
     public void testNlpApi(String title,String content);
     public void readWorldForDoc();
     public void readWorldForDocX();
}
