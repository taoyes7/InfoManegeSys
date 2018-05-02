package com.infomanagesys.InfoManageSys.controller;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.RuleAndFileResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.AblumDataDTOS;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.photo.impl.PhotoServiceImpl;
import com.infomanagesys.InfoManageSys.service.user.impl.UserServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/photo/")
public class PhotoController {
    @Autowired
    private PhotoServiceImpl photoService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/upload/img", method = RequestMethod.POST)
    public ResponseDTO upLoadImg(@RequestParam("sessionId") String sessionId, @RequestParam("description") String description , HttpServletRequest req,
                                 MultipartHttpServletRequest multiReq) {
        if(userService.userCheck(sessionId)){
            MultipartFile img = multiReq.getFile("img");
              photoService.uploadImg(userService.getUserId(sessionId),description,img);
              return new ResponseDTO();
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/create/album", method = RequestMethod.POST)
    public AblumDataDTOS CreateAlbum(@RequestParam("sessionId") String sessionId, @RequestParam("description") String description ,  @RequestParam("name") String name, @RequestParam("label") String label) {
        if(userService.userCheck(sessionId)){
            JSONArray labels;
            try{
                JSONObject _labels= new JSONObject(label);
                labels = _labels.getJSONArray("labels");
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析错误");
            }
            return photoService.CreateAlbum(userService.getUserId(sessionId),description,name,labels);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/root/ablum", method = RequestMethod.POST)
    public AblumDTO GetRootAblum(@RequestParam("sessionId") String sessionId) {
        if(userService.userCheck(sessionId)){
            return photoService.GetRootAblum(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/open/ablum", method = RequestMethod.POST)
    public ResponseDTO openAblum(@RequestParam("sessionId") String sessionId,@RequestParam("ablumId") String ablumId) {
        if(userService.userCheck(sessionId)){
            return photoService.openAblum(userService.getUserId(sessionId),ablumId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/current/ablum", method = RequestMethod.POST)
    public AblumDTO getCurrentAblum(@RequestParam("sessionId") String sessionId ){
        if(userService.userCheck(sessionId)){
            return photoService.getCurrentAblum(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/ablum/data", method = RequestMethod.POST)
    public AblumDataDTOS getAblumData(@RequestParam("sessionId") String sessionId, @RequestParam("ablumId") String ablumId){
        if(userService.userCheck(sessionId)){
            return photoService.getCurAblumData(ablumId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/ablum/weifenlei", method = RequestMethod.POST)
    public AblumDTO getWeiFenLeiAblum(@RequestParam("sessionId") String sessionId){
        if(userService.userCheck(sessionId)){
            return photoService.getWeiFenLeiAblum(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/parent/ablum", method = RequestMethod.POST)
    public AblumDTO getParentAblum(@RequestParam("sessionId") String sessionId,@RequestParam("ablumId") String ablumId){
        if(userService.userCheck(sessionId)){
            return photoService.getParentAblum(ablumId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/chang/ablum/img", method = RequestMethod.POST)
    public AblumDataDTOS changeAblumImg(@RequestParam("sessionId") String sessionId,@RequestParam("ablumId") String ablumId,@RequestParam("photoId") String photoId){
        if(userService.userCheck(sessionId)){
            return photoService.changeAblumImg(ablumId,photoId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/add/label/ablum", method = RequestMethod.POST)
    public AblumDataDTOS addLabelToAblum(@RequestParam("sessionId") String sessionId,@RequestParam("ablumId") String ablumId,@RequestParam("photoId") String photoId){
        if(userService.userCheck(sessionId)){
            return photoService.addLabelToAblum(ablumId,photoId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }


}
