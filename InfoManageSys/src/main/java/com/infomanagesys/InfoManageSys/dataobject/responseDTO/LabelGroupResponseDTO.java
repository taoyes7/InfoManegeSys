package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class LabelGroupResponseDTO extends ResponseDTO implements Serializable {
    private String pid;
    private String name;
    private int priorityLevel;
    private ArrayList<String> fileTypes = new ArrayList<String>();
    private ArrayList<LabelResponseDTO> labels= new ArrayList<LabelResponseDTO>();

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public ArrayList<String> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(ArrayList<String> fileTypes) {
        this.fileTypes = fileTypes;
    }

    public ArrayList<LabelResponseDTO> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<LabelResponseDTO> labels) {
        this.labels = labels;
    }
}
