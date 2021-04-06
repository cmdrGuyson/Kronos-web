package com.guyson.kronos.controller.api_controller;

import com.guyson.kronos.dto.ClassDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.ClassService;
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
public class ClassController {

    private final ClassService classService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/class")
    public ResponseEntity<ClassDto> addClass(@RequestBody ClassDto dto){

        ClassDto result = classService.addClass(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping("/classes")
    public ResponseEntity<List<ClassDto>> getAllClasses() {
        return new ResponseEntity<>(classService.getAllClasses(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/class/{classID}")
    public ResponseEntity<Object> deleteClass(@PathVariable int classID) {

        try{
            classService.deleteClass(classID);
            return new ResponseEntity<>(new SimpleMessageDto("Deleted successfully", HttpStatus.OK), HttpStatus.OK);
        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

    }

}
