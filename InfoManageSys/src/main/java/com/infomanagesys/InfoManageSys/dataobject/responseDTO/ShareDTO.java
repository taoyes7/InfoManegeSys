package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;

public class ShareDTO extends ResponseDTO implements Serializable {
    private String share_url;
    private String passworld;

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getPassworld() {
        return passworld;
    }

    public void setPassworld(String passworld) {
        this.passworld = passworld;
    }
}
