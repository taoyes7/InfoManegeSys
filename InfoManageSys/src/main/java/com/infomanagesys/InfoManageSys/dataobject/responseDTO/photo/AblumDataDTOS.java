package com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class AblumDataDTOS extends ResponseDTO implements Serializable {
    private ArrayList<AblumDTO> ablums = new ArrayList<>();
    private ArrayList<PhotoDTO> photos = new ArrayList<>();

    public ArrayList<AblumDTO> getAblums() {
        return ablums;
    }

    public void setAblums(ArrayList<AblumDTO> ablums) {
        this.ablums = ablums;
    }

    public ArrayList<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PhotoDTO> photos) {
        this.photos = photos;
    }
}
