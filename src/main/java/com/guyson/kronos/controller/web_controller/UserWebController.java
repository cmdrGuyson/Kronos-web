package com.guyson.kronos.controller.web_controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserWebController {

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
        return mv;
    }

    @GetMapping("/home-student")
    public ModelAndView homeStudent() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home_student.jsp");
        return mv;
    }

}
