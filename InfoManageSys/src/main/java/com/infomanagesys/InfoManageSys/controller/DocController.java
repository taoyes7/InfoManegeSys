package com.infomanagesys.InfoManageSys.controller;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.*;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.doc.impl.DocServiceImpl;
import com.infomanagesys.InfoManageSys.service.user.impl.UserServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
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
    @RequestMapping(value = "/create/rootdir", method = RequestMethod.POST)
    public FileResponseDTO createRootDir(@RequestParam("sessionId") String sessionId) {
        if(userService.userCheck(sessionId)){
            return docService.createRootDir(userService.getUserId(sessionId));
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

    @RequestMapping(value="/back/parent", method = RequestMethod.POST)
    public FileResponseListDTO backToParent(@RequestParam("sessionId") String sessionId) {
        if (userService.userCheck(sessionId)) {
            FileResponseListDTO fileResponseListDTO = new FileResponseListDTO();
            String dirId = docService.getCurrentDir(userService.getUserId(sessionId)).getPid();
            dirId = docService.getParentFile(dirId).getPid();
            fileResponseListDTO.setFileResponseDTOArrayList(docService.openNewDir(userService.getUserId(sessionId), dirId));
            return fileResponseListDTO;
        } else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value="/add/label",method = RequestMethod.POST)
    public FileResponseDTO addLabel(@RequestParam("file") String file,
                                   @RequestParam("label") String label,
                                    @RequestParam("sessionId") String sessionId
    ){
        if (userService.userCheck(sessionId)) {
            try{
                JSONObject _file = new JSONObject(file);
                JSONObject _label= new JSONObject(label);
                JSONArray labels=new JSONArray(_label.getString("labels"));
                return docService.addLabelToDir(userService.getUserId(sessionId),_file,labels);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }

        }else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value = "/get/alllabels", method = RequestMethod.POST)
    public AllLabelResponseDTO getALlLabel(@RequestParam("sessionId") String sessionId){
        if (userService.userCheck(sessionId)) {
            return docService.getAllLabels(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/check/label", method = RequestMethod.POST)
    public LabelCheckResponseDTO checkLabel(@RequestParam("sessionId") String sessionId,@RequestParam("label") String label){
        if (userService.userCheck(sessionId)) {
            LabelCheckResponseDTO response = new LabelCheckResponseDTO();

                response.setData(docService.checkLabel(userService.getUserId(sessionId),label));
                return response;

        } else {
            throw new UserCheckException("用户校验失败");
        }
    }

}

