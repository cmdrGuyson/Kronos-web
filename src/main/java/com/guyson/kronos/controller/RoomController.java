package com.guyson.kronos.controller;

import com.guyson.kronos.dto.RoomDto;
import com.guyson.kronos.service.RoomService;
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
public class RoomController {

    private final RoomService roomService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/room")
    public ResponseEntity<RoomDto> addRoom(@RequestBody RoomDto dto){

        RoomDto result = roomService.addRoom(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDto>> getAllClasses() {
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

}