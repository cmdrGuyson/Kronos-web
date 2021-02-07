package com.guyson.kronos.controller;

import com.guyson.kronos.dto.ModuleDto;
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

}
