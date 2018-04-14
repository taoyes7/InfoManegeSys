package com.infomanagesys.InfoManageSys.dataobject.enums;

public enum DocTypeEnum {
    DOC_TYPE_ENUM("DOC","world文档-Word2003以前版本","world","E:/Myfile/DOC.png")
    ,DOCX_TYPE_ENUM("DOCX","world文档-Office2007之后版本使用","world","E:/Myfile/DOCX.png")
    ,DIR_TYPE_ENUM("DIR","文件夹","dir","E:/Myfile/DIR.png");
    private String name;
    private String describe;
    private String type;
    private String iconPath;
    private DocTypeEnum(String name, String describe, String type, String iconPath){
        this.name=name;
        this.describe=describe;
        this.type=type;
        this.iconPath = iconPath;
    }

    public static DocTypeEnum getEnumByName(String name){
        switch(name){
            case "doc":
                return DOC_TYPE_ENUM;
            case "docx":
                return DOCX_TYPE_ENUM;
            case "dir":
                return DIR_TYPE_ENUM;
            default:
                 return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
