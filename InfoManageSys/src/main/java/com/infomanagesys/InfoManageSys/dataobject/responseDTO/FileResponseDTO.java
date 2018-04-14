package com.infomanagesys.InfoManageSys.dataobject.responseDTO;


import org.json.JSONArray;

import java.util.ArrayList;

public class FileResponseDTO extends ResponseDTO{

    /**文件或文件夹名*/
    private String name;
    /**文件类型*/
    private String type;
    /**文件识别id*/
    private String pid;
    /**文件逻辑路径*/
    private String path;
    /**文件对应标签*/
    private ArrayList<LabelResponseDTO> labels;
    /**文件显示对应的图标文件路径*/
    private String iconPath;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<LabelResponseDTO> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<LabelResponseDTO> labels) {
        this.labels = labels;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
