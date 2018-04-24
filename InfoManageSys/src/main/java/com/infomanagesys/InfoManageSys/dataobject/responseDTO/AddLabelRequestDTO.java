package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;

public class AddLabelRequestDTO extends ResponseDTO implements Serializable {
    private String sessionId;
    private FileResponseDTO file;
    private LabelResponseDTO label;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public FileResponseDTO getFile() {
        return file;
    }

    public void setFile(FileResponseDTO file) {
        this.file = file;
    }

    public LabelResponseDTO getLabel() {
        return label;
    }

    public void setLabel(LabelResponseDTO label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "AddLabelRequestDTO{" +
                "sessionId='" + sessionId + '\'' +
                ", file=" + file +
                ", label=" + label +
                '}';
    }
}
