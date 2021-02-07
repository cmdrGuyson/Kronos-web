package com.guyson.kronos.dto;

import com.guyson.kronos.domain.Lecturer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModuleDto {

    private int moduleID, lecturerID, credits;
    private String description, name;
    private Lecturer lecturer;

}
