package com.guyson.kronos.controller.api_controller;

import com.guyson.kronos.dto.LectureDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LectureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class LectureController {

    private final LectureService lectureService;

    @PreAuthorize("hasRole('ACADEMIC_ADMIN')")
    @PostMapping("/lecture")
    public ResponseEntity<Object> addLecture(@RequestBody LectureDto dto) {
        try {
            LectureDto result = lectureService.addLecture(dto, false);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'ACADEMIC_ADMIN')")
    @GetMapping("/lectures")
    public ResponseEntity<Object> getAllLectures() {
        try {
            return new ResponseEntity<>(lectureService.getAllLectures(), HttpStatus.OK);
        } catch (KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'ACADEMIC_ADMIN')")
    @GetMapping("/lectures/{date}")
    public ResponseEntity<Object> getAllLecturesByDay(@PathVariable String date) {
        try {
            return new ResponseEntity<>(lectureService.getAllLecturesByDay(date, "time"), HttpStatus.OK);
        } catch (KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ACADEMIC_ADMIN')")
    @PutMapping("/lecture/{lectureID}")
    public ResponseEntity<Object> updateLecture(@RequestBody LectureDto dto, @PathVariable int lectureID) {
        try {
            dto.setLectureID(lectureID);
            LectureDto result = lectureService.addLecture(dto, true);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ACADEMIC_ADMIN')")
    @DeleteMapping("/lecture/{lectureID}")
    public ResponseEntity<Object> deleteLecture(@PathVariable int lectureID) {
        try{

            lectureService.deleteLecture(lectureID);
            return new ResponseEntity<>(new SimpleMessageDto("Deleted successfully", HttpStatus.OK), HttpStatus.OK);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

}
