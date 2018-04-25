package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class LabelTypeDTOS extends ResponseDTO implements Serializable {
    private ArrayList<LabelTypeResponseDTO> labelTypes = new ArrayList<LabelTypeResponseDTO>();

    public ArrayList<LabelTypeResponseDTO> getLabelTypes() {
        return labelTypes;
    }

    public void setLabelTypes(ArrayList<LabelTypeResponseDTO> labelTypes) {
        this.labelTypes = labelTypes;
    }
}
