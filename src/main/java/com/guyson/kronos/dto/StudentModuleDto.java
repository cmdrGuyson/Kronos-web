package com.guyson.kronos.dto;

import com.guyson.kronos.domain.Lecturer;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StudentModuleDto {

    private int moduleID, lecturerID, credits;
    private String description, name;
    private Lecturer lecturer;
    private boolean enrolled;

}
