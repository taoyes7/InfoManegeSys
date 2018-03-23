package com.infomanagesys.InfoManageSys.service.impl;

import com.infomanagesys.InfoManageSys.dao.mapper.TestMapper;
import com.infomanagesys.InfoManageSys.exception.TestException;
import com.infomanagesys.InfoManageSys.service.itf.ITestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class TestServiceImpl implements ITestService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private TestMapper testMapper;

    private final static Logger logger = LoggerFactory.getLogger("com");
    @Override
    public void InsertIntoUser(){
        try {
            jdbcTemplate.update("INSERT INTO USER(name, age, sex) VALUES ('pt2',22,'female')");
        }catch(Exception e){
            throw new TestException(e.getMessage());
        }
    }
    public String getNameById(){
        Long userId = Long.parseLong("1");
        return testMapper.selectNameById(userId);
    }
    public String getNameFromRedis(){
        Long userId = Long.parseLong("1");
        String key = "1";
        String value = "ni hao";
        redisTemplate.opsForValue().set(key,value);
        String nameFromRedis = redisTemplate.opsForValue().get(key);
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
//        logger.fatal("fatal level");
        return nameFromRedis;
    }
    @Override
    public Boolean uploadFile(MultipartFile file){
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
        } finally {
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
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;

    }

}
