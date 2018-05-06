package com.infomanagesys.InfoManageSys.controller;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.User;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LoginResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.user.*;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.doc.impl.DocServiceImpl;
import com.infomanagesys.InfoManageSys.service.user.impl.UserServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/user/")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseDTO register(@RequestParam("userInfo") String  userInfo) {
        try {
            JSONObject _userInfo = new JSONObject(userInfo);
            return userService.register(_userInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析失败");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseDTO login(@ModelAttribute User user) {
        return userService.login(user)  ;
    }
    @RequestMapping(value = "/get/userInfo", method = RequestMethod.POST)
    public UserInfoDTO getUserInfo(@RequestParam("sessionId") String sessionId) {
        if(userService.userCheck(sessionId)){
            return userService.getUserInfo(userService.getUserId(sessionId))  ;

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/change/userInfo/img", method = RequestMethod.POST)
    public UserInfoDTO changeUserInfoImg(@RequestParam("sessionId") String sessionId,@RequestParam("photoId") String photoId) {
        if(userService.userCheck(sessionId)){
            return userService.changeUserInfoImg(userService.getUserId(sessionId),photoId)  ;

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/change/userInfo", method = RequestMethod.POST)
    public ResponseDTO changeUserInfo(@RequestParam("sessionId") String sessionId,@RequestParam("userInfo") String userInfo) {
        if(userService.userCheck(sessionId)){
            try{
                JSONObject _userInfo = new JSONObject(userInfo);
                return userService.changeUserInfo(userService.getUserId(sessionId),_userInfo)  ;
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析失败");
            }

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/create/userLink", method = RequestMethod.POST)
    public UserLinkDTO CreateUserLink(@RequestParam("sessionId") String sessionId, @RequestParam("userLink") String userLink) {
        if(userService.userCheck(sessionId)){
            try{
                JSONObject _userLink = new JSONObject(userLink);
                return userService.CreateUserLink(userService.getUserId(sessionId),_userLink)  ;
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析失败");
            }

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }

    @RequestMapping(value = "/delete/userLink", method = RequestMethod.POST)
    public ResponseDTO DeleteUserLink(@RequestParam("sessionId") String sessionId, @RequestParam("userLinkId") String userLinkId) {
        if(userService.userCheck(sessionId)){
           return userService.DeleteUserLink(userLinkId)  ;

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/userLinks", method = RequestMethod.POST)
    public UserLinkDTOS GetUserLinkS(@RequestParam("sessionId") String sessionId) {
        if(userService.userCheck(sessionId)){
            return userService.GetUserLinkS(userService.getUserId(sessionId))  ;

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/create/userDiary", method = RequestMethod.POST)
    public UserDiaryDTO CreateUserDiary(@RequestParam("sessionId") String sessionId, @RequestParam("title") String title, @RequestParam("content") String content) {
        if(userService.userCheck(sessionId)){
            return userService.CreateUserDiary(userService.getUserId(sessionId),title,content);

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/create/userPlan", method = RequestMethod.POST)
    public UserPlanDTO CreateUserPlan(@RequestParam("sessionId") String sessionId, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("level") String level) {
        if(userService.userCheck(sessionId)){
            return userService.CreateUserPlan(userService.getUserId(sessionId),title,content,Integer.parseInt(level));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/userDiary", method = RequestMethod.POST)
    public UserDiaryDTOS GetUserDiaryS(@RequestParam("sessionId") String sessionId) {
        if(userService.userCheck(sessionId)){
            return userService.GetUserDiaryS(userService.getUserId(sessionId))  ;
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/change/userDiary", method = RequestMethod.POST)
    public UserDiaryDTO changeUaerDiary(@RequestParam("sessionId") String sessionId,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("diaryId") String diaryId) {
        if(userService.userCheck(sessionId)){
            return userService.changeUaerDiary(diaryId,title,content);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/userPlan", method = RequestMethod.POST)
    public UserPlanDTOS GetUseruserPlanS(@RequestParam("sessionId") String sessionId) {
        if(userService.userCheck(sessionId)){
            return userService.GetUseruserPlanS(userService.getUserId(sessionId))  ;

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/end/userPlan", method = RequestMethod.POST)
    public ResponseDTO EndUseruserPlan(@RequestParam("sessionId") String sessionId,@RequestParam("planId") String planId) {
        if(userService.userCheck(sessionId)){
            return userService.EndUseruserPlan(planId)  ;

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }

}
