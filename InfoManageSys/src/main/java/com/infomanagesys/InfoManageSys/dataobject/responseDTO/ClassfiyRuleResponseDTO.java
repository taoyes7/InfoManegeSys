package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassfiyRuleResponseDTO extends ResponseDTO implements Serializable {
    private String dirId;
    private String pid;
    private String name;
    private ArrayList<LabelGroupResponseDTO> labelGroups = new ArrayList<LabelGroupResponseDTO>();

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

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

    public ArrayList<LabelGroupResponseDTO> getLabelGroups() {
        return labelGroups;
    }

    public void setLabelGroups(ArrayList<LabelGroupResponseDTO> labelGroups) {
        this.labelGroups = labelGroups;
    }
}
