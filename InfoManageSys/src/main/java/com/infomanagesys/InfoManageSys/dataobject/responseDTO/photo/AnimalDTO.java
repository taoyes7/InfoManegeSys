package com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;

public class AnimalDTO extends ResponseDTO implements Serializable {
    private Double score;
    private String name;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
