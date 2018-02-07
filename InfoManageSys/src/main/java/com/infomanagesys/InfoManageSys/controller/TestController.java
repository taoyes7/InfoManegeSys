package com.infomanagesys.InfoManageSys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.infomanagesys.InfoManageSys.service.impl.TestServiceImpl;

@RestController
@RequestMapping(value = "/test/")
public class TestController {
    @Autowired
    private TestServiceImpl testService;


    @GetMapping(value = "/hello")
    public String insertCustomer() {
//        testService.InsertIntoUser();
//        return "hello" + testService.getNameById();
        return testService.getNameFromRedis();
    }
}
