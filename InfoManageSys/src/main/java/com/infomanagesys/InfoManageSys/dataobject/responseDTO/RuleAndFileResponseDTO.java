package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;

public class RuleAndFileResponseDTO extends ResponseDTO implements Serializable {
    private FileResponseDTO file ;
    private LabelGroupResponseDTO labelGroup;

    public FileResponseDTO getFile() {
        return file;
    }

    public void setFile(FileResponseDTO file) {
        this.file = file;
    }

    public LabelGroupResponseDTO getLabelGroup() {
        return labelGroup;
    }

    public void setLabelGroup(LabelGroupResponseDTO labelGroup) {
        this.labelGroup = labelGroup;
    }
}
