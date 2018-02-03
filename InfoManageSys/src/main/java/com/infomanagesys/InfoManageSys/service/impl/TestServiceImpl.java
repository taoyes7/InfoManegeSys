package com.infomanagesys.InfoManageSys.service.impl;

import com.infomanagesys.InfoManageSys.exception.TestException;
import com.infomanagesys.InfoManageSys.service.itf.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class TestServiceImpl implements ITestService {
    @Autowired private JdbcTemplate jdbcTemplate;
    @Override
    public void InsertIntoUser(){
        try {
            jdbcTemplate.update("INSERT INTO USER VALUES ('','pt2',22,'female')");
        }catch(Exception e){
            throw new TestException("you are wrong");
        }
    }
}
