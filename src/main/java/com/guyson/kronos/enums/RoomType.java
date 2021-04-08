package com.guyson.kronos.enums;

public enum RoomType {

    LABORATORY("Laboratory"), HALL("Hall"), CLASSROOM("Classroom");

    private final String type;

    RoomType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
