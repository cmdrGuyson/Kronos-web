package com.guyson.kronos.dto;

import com.guyson.kronos.domain.Lecturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto {

    private int moduleID, lecturerID, credits;
    private String description, name;
    private Lecturer lecturer;

}
