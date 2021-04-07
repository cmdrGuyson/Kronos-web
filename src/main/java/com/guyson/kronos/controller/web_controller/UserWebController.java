package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.domain.Class;
import com.guyson.kronos.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UserWebController {

    private final StudentService studentService;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login.jsp");
        return mv;
    }

    @GetMapping("/home-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView homeAdmin() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("home_admin.jsp");
        mv.addObject("recent_students", studentService.getAllRecentStudents());
        return mv;
    }

    @GetMapping("/home-student")
    public ModelAndView homeStudent() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home_student.jsp");
        return mv;
    }

}
