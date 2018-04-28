package com.infomanagesys.InfoManageSys.dataobject.enums;

public enum FileStatusEnum {
    STATUS_AVAILABLE("0","可正常使用"),
    STATUS_DELETED("1","已删除");

    private  String state;
    private  String description;
    private  FileStatusEnum(String state,String description){
        this.state = state;
        this.description=description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
