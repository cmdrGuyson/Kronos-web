package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.dto.StudentDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.ClassService;
import com.guyson.kronos.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class StudentWebController {

    private final StudentService studentService;
    private final ClassService classService;

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllStudents() {
        return getStudents();
    }

    // Reusable function to get all students in system as ModelAndView
    private ModelAndView getStudents() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("students.jsp");
        mv.addObject("students", studentService.getAllStudents());

        // Get list of classes available for "Add student" modal
        mv.addObject("classes", classService.getAllClasses());
        return mv;
    }

    @PostMapping("/add-student")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addStudent(@RequestParam String username, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String classID) {

        ModelAndView mv = getStudents();

        try {

            // Create Student dto with user input
            StudentDto dto = new StudentDto();
            dto.setUsername(username);
            dto.setFirstName(firstName);
            dto.setLastName(lastName);
            dto.setClassID(Integer.parseInt(classID));

            // Add student and send return user to "Students" page
            studentService.addStudent(dto);
            mv = getStudents();
            mv.addObject("success", new SimpleMessageDto("Student added successfully!"));

        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @PostMapping("/delete-student")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteStudent(@RequestParam("username") String username) {

        ModelAndView mv = getStudents();

        try {

            // Delete student and send return user to "Students" page
            studentService.deleteStudent(username);
            mv = getStudents();
            mv.addObject("success", new SimpleMessageDto("Student deleted successfully!"));

        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;

    }
}
