package com.infomanagesys.InfoManageSys.dataobject.enums;

public enum  ShareTypeEnum {
    SHARE_ONE_DAY("0","一天有效"),
    SHARE_THREE_DAY("1","三天有效"),
    SHARE_ONE_WEEK("2","一周有效"),
    SHARE_ONE_MONTH("3","一月有效"),
    SHARE_FOREVER("4","永久有效");
    private String code ;
    private String description;

    private ShareTypeEnum(String code,String description){
        this.code=code;
        this.description=description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
