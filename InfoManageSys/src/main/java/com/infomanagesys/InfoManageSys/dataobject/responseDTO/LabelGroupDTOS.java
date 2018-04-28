package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class LabelGroupDTOS extends ResponseDTO implements Serializable {
    private ArrayList<LabelGroupDTO> labelGroups = new ArrayList<LabelGroupDTO>();
    private LabelGroupDTO abandon_labelGroup = new LabelGroupDTO();

    public ArrayList<LabelGroupDTO> getLabelGroups() {
        return labelGroups;
    }

    public void setLabelGroups(ArrayList<LabelGroupDTO> labelGroups) {
        this.labelGroups = labelGroups;
    }

    public LabelGroupDTO getAbandon_labelGroup() {
        return abandon_labelGroup;
    }

    public void setAbandon_labelGroup(LabelGroupDTO abandon_labelGroup) {
        this.abandon_labelGroup = abandon_labelGroup;
    }
}
