package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class RulesAndFileLIstResponseDTO extends ResponseDTO implements Serializable {
    private LabelGroupResponseDTO labelGroup = new LabelGroupResponseDTO();
    private ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();

    public LabelGroupResponseDTO getLabelGroup() {
        return labelGroup;
    }

    public void setLabelGroup(LabelGroupResponseDTO labelGroup) {
        this.labelGroup = labelGroup;
    }

    public ArrayList<FileResponseDTO> getFileResponseDTOArrayList() {
        return fileResponseDTOArrayList;
    }

    public void setFileResponseDTOArrayList(ArrayList<FileResponseDTO> fileResponseDTOArrayList) {
        this.fileResponseDTOArrayList = fileResponseDTOArrayList;
    }
}
