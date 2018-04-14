package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

public class  LoginResponseDTO extends ResponseDTO{
    private boolean state;
    private String sessionId;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
