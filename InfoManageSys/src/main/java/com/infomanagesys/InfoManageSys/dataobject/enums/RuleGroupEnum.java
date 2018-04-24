package com.infomanagesys.InfoManageSys.dataobject.enums;

public enum RuleGroupEnum {
    GROUP_AND("and","red"),
    GROUP_OR("or","green"),
    GROUP_NO("no","#52575c");
    private String group;
    private String color;

    private RuleGroupEnum(String group,String color){
        this.group=group;
        this.color=color;
    }
    public static RuleGroupEnum getEnumByGroup(String group){
        switch (group){
            case "and":
                return GROUP_AND;
            case "or":
                return GROUP_OR;
            case "no":
                return GROUP_NO;
            default:
                return null;
        }
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}