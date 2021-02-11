package com.guyson.kronos.dto;

import com.guyson.kronos.domain.Module;
import com.guyson.kronos.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LectureDto {

    private int lectureID;
    private String date, startTime;
    private int duration, roomID, moduleID;
    private ModuleDto module;
    private Room room;

}
