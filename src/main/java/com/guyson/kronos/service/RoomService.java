package com.guyson.kronos.service;

import com.guyson.kronos.domain.Room;
import com.guyson.kronos.dto.RoomDto;
import com.guyson.kronos.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {

    public final RoomRepository roomRepository;

    @Transactional
    public RoomDto addRoom(RoomDto dto) {

        Room room = roomRepository.save(map(dto));
        dto.setRoomID(room.getRoomID());
        return dto;
    }

    @Transactional
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(this::mapDto).collect(Collectors.toList());
    }


    //Method to map data transfer object to domain class
    private Room map(RoomDto dto) {
        return Room.builder().type(dto.getType()).description(dto.getDescription()).createdAt(Instant.now()).build();
    }

    //Method to map domain class to data transfer object
    private RoomDto mapDto(Room room) {
        return new RoomDto(room.getRoomID(), room.getType(), room.getDescription());
    }

}
