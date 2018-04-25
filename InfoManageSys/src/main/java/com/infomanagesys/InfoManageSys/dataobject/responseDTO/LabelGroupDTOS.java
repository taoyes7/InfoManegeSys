package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class LabelGroupDTOS extends ResponseDTO implements Serializable {
    private ArrayList<LabelGroupDTO> labelGroups = new ArrayList<LabelGroupDTO>();

    public ArrayList<LabelGroupDTO> getLabelGroups() {
        return labelGroups;
    }

    public void setLabelGroups(ArrayList<LabelGroupDTO> labelGroups) {
        this.labelGroups = labelGroups;
    }
}
