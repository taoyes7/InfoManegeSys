package com.infomanagesys.InfoManageSys.controller;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.User;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LoginResponseDTO;
import com.infomanagesys.InfoManageSys.service.doc.impl.DocServiceImpl;
import com.infomanagesys.InfoManageSys.service.user.impl.UserServiceImpl;
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
    public boolean register(@ModelAttribute User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseDTO login(@ModelAttribute User user) {
        return userService.login(user);
    }

}
