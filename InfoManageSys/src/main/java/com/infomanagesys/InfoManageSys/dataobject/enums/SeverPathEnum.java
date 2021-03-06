package com.infomanagesys.InfoManageSys.dataobject.enums;

public enum SeverPathEnum {
    SEVER_PATH("http://127.0.0.1:8085","服务器地址"),
    FILE_SEVER_PATH("http://127.0.0.1:8080","文件服务器地址"),
    WEB_PATH("http://127.0.0.1:3030","前端地址"),
    FILE_PATH("E://Workspace//InfoManegeSys//doc-server//src//main//webapp//userData","文件根目录地址");
    private String path;
    private String description;
    private SeverPathEnum(String path,String description){
        this.path =path;
        this.description=description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
