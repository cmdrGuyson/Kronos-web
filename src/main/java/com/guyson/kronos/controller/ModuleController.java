package com.guyson.kronos.controller;

import com.guyson.kronos.dto.ModuleDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.dto.StudentModuleDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.ModuleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ModuleController {

    private final ModuleService moduleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/module")
    public ResponseEntity<Object> addModule(@RequestBody ModuleDto dto) {

        try {

            ModuleDto result = moduleService.addModule(dto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modules")
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        return new ResponseEntity<>(moduleService.getAllModules(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/enroll/{moduleID}")
    public ResponseEntity<Object> enroll(@PathVariable int moduleID) {
        try {

            moduleService.enroll(moduleID);

            return new ResponseEntity<>(new SimpleMessageDto("Enrolled successfully", HttpStatus.OK), HttpStatus.OK);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/unroll/{moduleID}")
    public ResponseEntity<Object> unroll(@PathVariable int moduleID) {
        try {

            moduleService.unroll(moduleID);

            return new ResponseEntity<>(new SimpleMessageDto("Unrolled successfully", HttpStatus.OK), HttpStatus.OK);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my-modules")
    public ResponseEntity<Object> getMyModules() {
        try {

            Set<StudentModuleDto> modules = moduleService.getMyModules();

            return new ResponseEntity<>(modules, HttpStatus.OK);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student-modules")
    public ResponseEntity<Object> getAllStudentModules() {
        try {

            Set<StudentModuleDto> modules = moduleService.getAllStudentModules();

            return new ResponseEntity<>(modules, HttpStatus.OK);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/module/{moduleID}")
    public ResponseEntity<Object> deleteModule(@PathVariable int moduleID) {
        try{

            moduleService.deleteModule(moduleID);
            return new ResponseEntity<>(new SimpleMessageDto("Deleted successfully", HttpStatus.OK), HttpStatus.OK);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

}
