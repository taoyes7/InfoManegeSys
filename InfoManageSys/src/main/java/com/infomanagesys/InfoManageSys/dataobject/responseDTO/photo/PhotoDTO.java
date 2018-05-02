package com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class PhotoDTO extends ResponseDTO implements Serializable {
    private String pid;
    private String description;
    private String type;
    private String name;
    private ArrayList<LabelResponseDTO> labels = new ArrayList<>();
    private String group_id;
    private String src;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<LabelResponseDTO> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<LabelResponseDTO> labels) {
        this.labels = labels;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
