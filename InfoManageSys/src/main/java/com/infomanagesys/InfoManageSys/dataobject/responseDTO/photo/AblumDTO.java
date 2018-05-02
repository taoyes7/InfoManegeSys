package com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class AblumDTO extends ResponseDTO implements Serializable {
    private String name ;
    private String description;
    private String pid;
    private String iconPath;
    private String type;
    private int level;
    /**文件对应标签*/
    private ArrayList<LabelResponseDTO> labels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public ArrayList<LabelResponseDTO> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<LabelResponseDTO> labels) {
        this.labels = labels;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
