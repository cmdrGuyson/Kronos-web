package com.guyson.kronos.controller.api_controller;

import com.guyson.kronos.dto.RoomDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'ACADEMIC_ADMIN')")
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/room/{roomID}")
    public ResponseEntity<Object> deleteRoom(@PathVariable int roomID) {
        try{

            roomService.deleteRoom(roomID);
            return new ResponseEntity<>(new SimpleMessageDto("Deleted successfully", HttpStatus.OK), HttpStatus.OK);

        }catch(KronosException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

}