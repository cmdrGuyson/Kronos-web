package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class StudentWebController {

    private final StudentService studentService;

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllStudents() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("students.jsp");
        mv.addObject("students", studentService.getAllStudents());
        return mv;
    }
}
