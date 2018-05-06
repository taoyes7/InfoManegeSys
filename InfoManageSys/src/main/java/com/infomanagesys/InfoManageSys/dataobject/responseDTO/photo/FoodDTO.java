package com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;

public class FoodDTO extends ResponseDTO implements Serializable {
    private Double calorie;
    private boolean has_calorie;
    private String name;
    private Double probability;

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public boolean isHas_calorie() {
        return has_calorie;
    }

    public void setHas_calorie(boolean has_calorie) {
        this.has_calorie = has_calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
