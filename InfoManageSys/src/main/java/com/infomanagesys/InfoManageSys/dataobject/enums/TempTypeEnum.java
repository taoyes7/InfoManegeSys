package com.infomanagesys.InfoManageSys.dataobject.enums;

public enum TempTypeEnum {
    TEMP_TYPE_SESSIONID(1,"sessionId","会话ID"),
    TEMP_TYPE_CURRENTDIR(2,"currentDir","用户当前目录");
    private int code;
    private String key;
    private String description;
    private TempTypeEnum(int code, String key, String description){
        this.code=code;
        this.key=key;
        this.description=description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
