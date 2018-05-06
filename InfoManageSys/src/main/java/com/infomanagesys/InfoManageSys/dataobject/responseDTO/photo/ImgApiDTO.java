package com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class ImgApiDTO extends ResponseDTO implements Serializable {
    private String photo_src ;
    private ArrayList<PlantDTO> plantS = new ArrayList<PlantDTO>();
    private ArrayList<AnimalDTO> animalS = new ArrayList<AnimalDTO>();
    private ArrayList<FoodDTO> foodS= new ArrayList<FoodDTO>();
    private ArrayList<CarDTO> carS = new ArrayList<CarDTO>();
    private String typeCode;

    public String getPhoto_src() {
        return photo_src;
    }

    public void setPhoto_src(String photo_src) {
        this.photo_src = photo_src;
    }

    public ArrayList<PlantDTO> getPlantS() {
        return plantS;
    }

    public void setPlantS(ArrayList<PlantDTO> plantS) {
        this.plantS = plantS;
    }

    public ArrayList<AnimalDTO> getAnimalS() {
        return animalS;
    }

    public void setAnimalS(ArrayList<AnimalDTO> animalS) {
        this.animalS = animalS;
    }

    public ArrayList<FoodDTO> getFoodS() {
        return foodS;
    }

    public void setFoodS(ArrayList<FoodDTO> foodS) {
        this.foodS = foodS;
    }

    public ArrayList<CarDTO> getCarS() {
        return carS;
    }

    public void setCarS(ArrayList<CarDTO> carS) {
        this.carS = carS;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
