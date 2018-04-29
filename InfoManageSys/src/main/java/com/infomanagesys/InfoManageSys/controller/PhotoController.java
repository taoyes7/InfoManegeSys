package com.infomanagesys.InfoManageSys.controller;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.RuleAndFileResponseDTO;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.photo.impl.PhotoServiceImpl;
import com.infomanagesys.InfoManageSys.service.user.impl.UserServiceImpl;
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
}
