package com.infomanagesys.InfoManageSys.dataobject.responseDTO.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserDiary;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;

public class UserDiaryDTO extends ResponseDTO implements Serializable {
    private UserDiary userDiary = new UserDiary();

    public UserDiary getUserDiary() {
        return userDiary;
    }

    public void setUserDiary(UserDiary userDiary) {
        this.userDiary = userDiary;
    }
}
