package com.infomanagesys.InfoManageSys.dataobject.responseDTO.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserDiary;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDiaryDTOS extends ResponseDTO implements Serializable {
    private ArrayList<UserDiary> userDiarieS = new ArrayList<UserDiary>();
    private int pages=0;
    private int items=0;

    public ArrayList<UserDiary> getUserDiarieS() {
        return userDiarieS;
    }

    public void setUserDiarieS(ArrayList<UserDiary> userDiarieS) {
        this.userDiarieS = userDiarieS;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }
}
