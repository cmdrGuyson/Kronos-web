package com.guyson.kronos.dto;

import com.guyson.kronos.domain.Class;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private int classID;
    private String username, firstName, lastName, password, joinedOn;
    private Class _class;

    public StudentDto(int classID, String username, String firstName, String lastName, Class _class, String joinedOn) {
        this.classID = classID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this._class = _class;
        this.joinedOn = joinedOn;
    }
}
