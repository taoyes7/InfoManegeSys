package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class LabelGroupDTO extends ResponseDTO implements Serializable {
    private LabelTypeResponseDTO labelType = new LabelTypeResponseDTO();
    private ArrayList<LabelResponseDTO> labels = new ArrayList<LabelResponseDTO>();

    public LabelTypeResponseDTO getLabelType() {
        return labelType;
    }

    public void setLabelType(LabelTypeResponseDTO labelType) {
        this.labelType = labelType;
    }

    public ArrayList<LabelResponseDTO> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<LabelResponseDTO> labels) {
        this.labels = labels;
    }
}
