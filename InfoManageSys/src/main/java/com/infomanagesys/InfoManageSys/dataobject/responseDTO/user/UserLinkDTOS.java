package com.infomanagesys.InfoManageSys.dataobject.responseDTO.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserLink;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class UserLinkDTOS extends ResponseDTO implements Serializable {
    private ArrayList<UserLink> userLinkS = new ArrayList<UserLink>();

    public ArrayList<UserLink> getUserLinkS() {
        return userLinkS;
    }

    public void setUserLinkS(ArrayList<UserLink> userLinkS) {
        this.userLinkS = userLinkS;
    }
}
