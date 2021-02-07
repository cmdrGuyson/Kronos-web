package com.guyson.kronos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDto {

    private int lecturerID;
    private String firstName, lastName, email, type;

}
