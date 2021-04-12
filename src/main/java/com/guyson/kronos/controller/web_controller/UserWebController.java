package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.domain.Class;
import com.guyson.kronos.dto.ChangePasswordRequest;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.AuthService;
import com.guyson.kronos.service.LectureService;
import com.guyson.kronos.service.StudentService;
import com.guyson.kronos.service.UserDetailsServiceImplementation;
import com.guyson.kronos.util.ExtraUtilities;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
public class UserWebController {

    private final StudentService studentService;
    private final LectureService lectureService;
    private final AuthService authService;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());

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
        try {
            mv.addObject("name", studentService.getName());
        } catch (KronosException e) {
            e.printStackTrace();
        }
        return mv;
    }

    @GetMapping("/home-student")
    @PreAuthorize("hasRole('STUDENT')")
    public ModelAndView homeStudent() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home_student.jsp");

        Instant today = Instant.now();
        String day = DATE_TIME_FORMATTER.format(today);

        try {
            mv.addObject("lectures", lectureService.getAllLecturesByDay(day, "time"));
            mv.addObject("name", studentService.getName());
        } catch (KronosException e) {
            e.printStackTrace();
        }
        return mv;
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ModelAndView changePassword(ChangePasswordRequest dto) {
        ModelAndView mv = new ModelAndView();

        //Check if user is an administrator
        boolean isAdmin = ExtraUtilities.hasRole("ROLE_ADMIN");

        mv.setViewName("/home-student.jsp");
        if(isAdmin) mv.setViewName("/home_admin.jsp");

        try {
            authService.changePassword(dto);
            mv.addObject("successSetting", new SimpleMessageDto("Successfully changed password!"));
        }catch(KronosException e) {
            mv.addObject("errorSetting", new APIException(e.getMessage()));
        }

        return mv;
    }
}
