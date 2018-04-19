package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;

public class LabelCheckResponseDTO extends ResponseDTO implements Serializable {
    private boolean data ;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
