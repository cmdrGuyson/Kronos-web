package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class RoomWebController {

    private RoomService roomService;

    @GetMapping("/rooms")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllRooms() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("rooms.jsp");
        mv.addObject("rooms", roomService.getAllRooms());

        return mv;
    }

}
