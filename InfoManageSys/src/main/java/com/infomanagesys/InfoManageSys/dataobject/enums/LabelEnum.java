package com.infomanagesys.InfoManageSys.dataobject.enums;

public enum LabelEnum {
    LABEL_ONE_ENUM(1,"red"),
    LABEL_TWO_ENUM(2,"orange"),
    LABEL_THREE_ENUM(3,"yellow"),
    LABEL_FOUR_ENUM(4,"green");
    private int level;
    private String color;
    private LabelEnum(int level, String color){
        this.level=level;
        this.color=color;
    }

    public static LabelEnum GetEnumByLevel(int level){
        switch (level){
            case 1:
                return LABEL_ONE_ENUM;
            case 2:
                return LABEL_TWO_ENUM;
            case 3:
                return LABEL_THREE_ENUM;
            case 4:
                return LABEL_FOUR_ENUM;
                default:
                    return null;
        }
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
}
