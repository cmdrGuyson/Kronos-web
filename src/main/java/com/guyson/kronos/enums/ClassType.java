package com.guyson.kronos.enums;

public enum ClassType {

    COMPUTER_SCIENCE("Computer Science"), SOFTWARE_ENGINEERING("Software Engineering"), BUSINESS_MANAGEMENT("Business Management"), LAW("Law");

    private final String type;

    ClassType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
