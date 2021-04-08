package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.service.ClassService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class ClassWebController {

    private ClassService classService;

    @GetMapping("/classes")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllClasses() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("classes.jsp");
        mv.addObject("classes", classService.getAllClasses());

        return mv;
    }

}
