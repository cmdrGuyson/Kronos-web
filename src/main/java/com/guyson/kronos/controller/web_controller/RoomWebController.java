package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.ClassDto;
import com.guyson.kronos.dto.RoomDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.enums.ClassType;
import com.guyson.kronos.enums.RoomType;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class RoomWebController {

    private RoomService roomService;

    @GetMapping("/rooms")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllRooms() {
        return getRooms();
    }

    private ModelAndView getRooms() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("rooms.jsp");

        List<String> types = new ArrayList<>();

        for (RoomType roomType : RoomType.values()) {
            types.add(roomType.getType());
        }

        mv.addObject("types", types);
        mv.addObject("rooms", roomService.getAllRooms());

        return mv;
    }

    @PostMapping("/add-room")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addRoom(@RequestParam String type, @RequestParam String description) {


        RoomDto dto = new RoomDto();
        dto.setType(type);
        dto.setDescription(description);

        roomService.addRoom(dto);
        ModelAndView mv = getRooms();
        mv.addObject("success", new SimpleMessageDto("Room added successfully!"));

        return mv;
    }

    @PostMapping("/delete-room")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteRoom(@RequestParam String roomID) {

        ModelAndView mv = getRooms();
        try {
            roomService.deleteRoom(Integer.parseInt(roomID));
            mv = getRooms();
            mv.addObject("success", new SimpleMessageDto("Room deleted successfully!"));
        } catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

}
