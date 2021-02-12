package com.guyson.kronos.controller;

import com.guyson.kronos.dto.LecturerDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LecturerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class LecturerController {

    private final LecturerService lecturerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/lecturer")
    public ResponseEntity<Object> addLecturer(@RequestBody LecturerDto dto) {
        LecturerDto result = null;
        try {
            result = lecturerService.addLecturer(dto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lecturers")
    public ResponseEntity<List<LecturerDto>> getAllLecturers() {
        return new ResponseEntity<>(lecturerService.getAllLecturers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/lecturer/{lecturerID}")
    public ResponseEntity<Object> deleteLecturer(@PathVariable int lecturerID) {
        try{

            lecturerService.deleteLecturer(lecturerID);
            return new ResponseEntity<>(new SimpleMessageDto("Deleted successfully", HttpStatus.OK), HttpStatus.OK);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

}
