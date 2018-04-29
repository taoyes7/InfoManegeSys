package com.infomanagesys.InfoManageSys.service.doc.impl;

import com.baidu.aip.nlp.AipNlp;
import com.infomanagesys.InfoManageSys.dao.mapper.TestMapper;
import com.infomanagesys.InfoManageSys.exception.TestException;
import com.infomanagesys.InfoManageSys.service.doc.itf.ITestService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;


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

//    @Override
//    public void testApi(){
////        String body= "{\"type\":0\n\"image_url\":\"https://img.alicdn.com/tfs/TB1VHjASpXXXXcPaXXXXXXXXXXX-780-300.jpg\"}";
//        JSONObject body = new JSONObject();
//        try{
//            body.put("type",0);
//            body.put("image_url","https://www.crifan.com/files/pic/uploads/2013/09/after-byte-array-cast-to-string-go-run-ok_thumb.png");
//        }catch (Exception e){
//            ;
//        }
//        byte[] _body =  body.toString().getBytes();
//
//        AsyncDemo_智能图像_图像识别_图像打标 apidemo = new AsyncDemo_智能图像_图像识别_图像打标();
//        apidemo.智能图像_图像识别_图像打标Demo(_body);
//    }
    @Override
    public void testNlpApi(String title,String content){
        AipNlp client = new AipNlp("11003509", "uVzOkw5KkPpmyzrmbkyGXSjN", "hAkCrjUKjhWhwTNG4plqPWEsIIEcsWLp");
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
//        String title = "iphone手机出现“白苹果”原因及解决办法，用苹果手机的可以看下";
//        String content = "如果下面的方法还是没有解决你的问题建议来我们门店看下成都市锦江区红星路三段99号银石广场24层01室。";

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();

        // 文章标签
        JSONObject res = client.keyword(title, content, options);
        System.out.println(res);

    }
    @Override
    public void readWorldForDoc(){
        File file = new File("D:\\彭涛\\个人信息\\工作实习\\彭涛实习报告.doc");
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            HWPFDocument doc = new HWPFDocument(fis);
            String doc1 = doc.getDocumentText();
            System.out.println(doc1);
            testNlpApi("彭涛实习报告",doc1);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void readWorldForDocX() {
        File file = new File("D:\\彭涛\\个人信息\\工作实习\\彭涛_校外实习申请.docx");
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String doc1 = extractor.getText();
            System.out.println(doc1);
            testNlpApi("彭涛_校外实习申请",doc1);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
