package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.util.ExtraUtilities;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeFacadeController {

    //Facade to direct user type to respective controller to load home page
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ModelAndView sendUserHome() {

        //Check if user is an administrator
        boolean isAdmin = ExtraUtilities.hasRole("ROLE_ADMIN");

        ModelAndView mv = new ModelAndView();

        if(isAdmin) {
            mv.setViewName("redirect:/home-admin");
        }else{
            mv.setViewName("redirect:/home-student");
        }

        return mv;
    }
}