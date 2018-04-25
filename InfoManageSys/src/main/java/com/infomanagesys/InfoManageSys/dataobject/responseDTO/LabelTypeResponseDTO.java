package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;

public class LabelTypeResponseDTO extends ResponseDTO implements Serializable {
    private String pid;
    private String name;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
