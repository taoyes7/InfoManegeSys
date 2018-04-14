package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import org.json.JSONObject;

public class LabelResponseDTO extends ResponseDTO{
    private String content;
    private int level;
    private String color;
    private double score;

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

    @Override
    public String toString() {
        JSONObject label = new JSONObject();
        try {
            label.put("content",content);
            label.put("level",level);
            label.put("color",color);
            label.put("score",score);
        }catch (Exception e){
            e.printStackTrace();
        }
        return label.toString();
    }
}
