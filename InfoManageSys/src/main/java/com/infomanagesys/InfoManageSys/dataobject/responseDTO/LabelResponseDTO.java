package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import org.json.JSONObject;

import java.io.Serializable;

public class LabelResponseDTO extends ResponseDTO implements Serializable {
    private String content;
    private String discription;
    private LabelTypeResponseDTO type;
    private int level;
    private String color;
    private double score;
    private String pid;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public LabelTypeResponseDTO getType() {
        return type;
    }

    public void setType(LabelTypeResponseDTO type) {
        this.type = type;
    }

    @Override
    public String toString() {
        JSONObject label = new JSONObject();
        try {
            label.put("content",content);
            label.put("level",level);
            label.put("color",color);
            label.put("score",score);
            label.put("pid",pid);
            label.put("type",type);
            label.put("discription",discription);
        }catch (Exception e){
            e.printStackTrace();
        }
        return label.toString();
    }
}
