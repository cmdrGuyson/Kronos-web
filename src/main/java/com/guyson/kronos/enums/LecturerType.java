package com.guyson.kronos.enums;

public enum LecturerType {

    VISITING("Visiting"), PERMANENT("Permanent");

    private final String type;

    LecturerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
