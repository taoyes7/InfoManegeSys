package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;

public class ResponseDTO implements Serializable {
    /**请求响应标志*/
    private boolean success = true;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
