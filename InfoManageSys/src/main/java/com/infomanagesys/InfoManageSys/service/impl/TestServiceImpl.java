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

import javax.annotation.Resource;


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
        String value = testMapper.selectNameById(userId);
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

}
