package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class AllLabelResponseDTO extends ResponseDTO implements Serializable {
    private ArrayList<LabelResponseDTO> labelResponseDTOArrayList;

    public ArrayList<LabelResponseDTO> getLabelResponseDTOArrayList() {
        return labelResponseDTOArrayList;
    }

    public void setLabelResponseDTOArrayList(ArrayList<LabelResponseDTO> labelResponseDTOArrayList) {
        this.labelResponseDTOArrayList = labelResponseDTOArrayList;
    }
}
