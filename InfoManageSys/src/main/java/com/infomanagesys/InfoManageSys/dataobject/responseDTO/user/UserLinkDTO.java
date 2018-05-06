package com.infomanagesys.InfoManageSys.dataobject.responseDTO.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserLink;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;

public class UserLinkDTO extends ResponseDTO implements Serializable {
    private UserLink userLink = new UserLink();

    public UserLink getUserLink() {
        return userLink;
    }

    public void setUserLink(UserLink userLink) {
        this.userLink = userLink;
    }
}
