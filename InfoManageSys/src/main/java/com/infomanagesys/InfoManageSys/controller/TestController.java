package com.infomanagesys.InfoManageSys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.infomanagesys.InfoManageSys.service.doc.impl.TestServiceImpl;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.Axios;
@RestController
@RequestMapping(value = "/test/")
public class TestController {
    @Autowired
    private TestServiceImpl testService;


    @GetMapping(value = "/hello")
    public String insertCustomer() {
//        testService.InsertIntoUser();
//        Axios axios = new Axios();
//        axios.setData(" Taoyes 7");
//        axios.setSuccess(true);
//        return axios;
//        testService.readWorldForDocX();
        return testService.getNameFromRedis();
//        return testService.getNameById();
    }

    @RequestMapping(value = "/upLoadSingleFile", method = RequestMethod.POST)
    public void upLoadSingleFile(HttpServletRequest req,
                                 MultipartHttpServletRequest multiReq) {
        MultipartFile file = multiReq.getFile("file1");
//        testService.uploadFile(file);
    }

    @RequestMapping(value = "/Axios", method = RequestMethod.POST)
    public Axios TestAxios(@RequestParam("data") String data) {
        Axios axios = new Axios();
        axios.setData(data+" Taoyes 7");
        axios.setSuccess(false);
        return axios;
    }
}

