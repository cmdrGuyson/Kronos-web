package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.service.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class LecturerWebController {

    private final LecturerService lecturerService;

    @GetMapping("/lecturers")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllLecturers() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("lecturers.jsp");
        mv.addObject("lecturers", lecturerService.getAllLecturers());
        return mv;
    }
}
