package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

public class Axios {
    private String data;
    private boolean success=false;

    public String getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
