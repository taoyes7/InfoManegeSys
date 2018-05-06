package com.infomanagesys.InfoManageSys.dataobject.responseDTO.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserInfo;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;

public class UserInfoDTO extends ResponseDTO implements Serializable {
    private UserInfo userInfo = new UserInfo();

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
