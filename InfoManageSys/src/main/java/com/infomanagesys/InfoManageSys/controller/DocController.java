package com.infomanagesys.InfoManageSys.controller;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.FileResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.FileResponseListDTO;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.doc.impl.DocServiceImpl;
import com.infomanagesys.InfoManageSys.service.user.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/doc/")
public class DocController {
    @Autowired
    private DocServiceImpl docService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/upLoadSingleFile", method = RequestMethod.POST)
    public FileResponseDTO upLoadSingleFile(@RequestParam("sessionId") String sessionId, HttpServletRequest req,
                                            MultipartHttpServletRequest multiReq) {
        if(userService.userCheck(sessionId)){
            MultipartFile file = multiReq.getFile("file1");
            FileResponseDTO fileResponseDTO = docService.uploadFile(userService.getUserId(sessionId),file);
            return fileResponseDTO;
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/createdir", method = RequestMethod.POST)
    public FileResponseDTO createDir(@RequestParam("sessionId") String sessionId, @RequestParam("name") String name) {
        if(userService.userCheck(sessionId)){
//            docService.createRootDir(userService.getUserId(sessionId));
                return docService.createDir(userService.getUserId(sessionId),name);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/open/rootdir",method = RequestMethod.POST)
    public FileResponseListDTO openRootDir(@RequestParam("sessionId") String sessionId){
        if(userService.userCheck(sessionId)){
            FileResponseListDTO fileResponseListDTO = new FileResponseListDTO();
            fileResponseListDTO.setFileResponseDTOArrayList(docService.openRootDir(userService.getUserId(sessionId)));
            return fileResponseListDTO;
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/open/dir", method = RequestMethod.POST)
    public FileResponseListDTO openDir(@RequestParam("sessionId") String sessionId, @RequestParam("dirId") String dirId){
        if(userService.userCheck(sessionId)){
            FileResponseListDTO fileResponseListDTO = new FileResponseListDTO();
            fileResponseListDTO.setFileResponseDTOArrayList(docService.openNewDir(userService.getUserId(sessionId),dirId));
            return fileResponseListDTO;
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/get/currentdir", method = RequestMethod.POST)
    public FileResponseDTO getCurrentDir(@RequestParam("sessionId") String sessionId){
        if(userService.userCheck(sessionId)){
            return docService.getCurrentDir(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }

}

