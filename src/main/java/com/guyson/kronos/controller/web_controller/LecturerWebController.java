package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.LecturerDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.enums.LecturerType;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LecturerService;
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
public class LecturerWebController {

    private final LecturerService lecturerService;

    @GetMapping("/lecturers")
    @PreAuthorize("hasRole('ACADEMIC_ADMIN')")
    public ModelAndView viewAllLecturers() {
        return getLecturers();
    }

    //Reusable function to get ModelAndView with all lecturers in system
    private ModelAndView getLecturers() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("lecturers.jsp");

        List<String> types = new ArrayList<>();

        // All lecturer types to be displayed in "Add Lecturer" modal
        for (LecturerType lecturerType : LecturerType.values()) {
            types.add(lecturerType.getType());
        }

        mv.addObject("types", types);
        mv.addObject("lecturers", lecturerService.getAllLecturers());
        return mv;
    }

    @PostMapping("/add-lecturer")
    @PreAuthorize("hasRole('ACADEMIC_ADMIN')")
    public ModelAndView addLecturer(@RequestParam String type, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {

        ModelAndView mv = getLecturers();

        try {

            // Create lecturer dto with user input
            LecturerDto dto = new LecturerDto();
            dto.setType(type);
            dto.setFirstName(firstName);
            dto.setLastName(lastName);
            dto.setEmail(email);


            lecturerService.addLecturer(dto);

            //Return user to "Lecturers" page
            mv = getLecturers();
            mv.addObject("success", new SimpleMessageDto("Lecturer added successfully!"));

        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @PostMapping("/delete-lecturer")
    @PreAuthorize("hasRole('ACADEMIC_ADMIN')")
    public ModelAndView deleteLecturer(@RequestParam("lecturerID") String lecturerID) {

        ModelAndView mv = getLecturers();

        try {

            lecturerService.deleteLecturer(Integer.parseInt(lecturerID));

            //Return user to "Lecturers" page
            mv = getLecturers();
            mv.addObject("success", new SimpleMessageDto("Lecturer deleted successfully!"));

        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;

    }
}
