package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.ChangePasswordRequest;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.AuthService;
import com.guyson.kronos.service.LectureService;
import com.guyson.kronos.service.StudentService;
import com.guyson.kronos.service.EmailService;
import com.guyson.kronos.util.ExtraUtilities;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final EmailService emailService;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());

    @GetMapping("/login")
    public ModelAndView login(String error) {
        ModelAndView mv = new ModelAndView();

        if(error != null) mv.addObject("error", "Invalid login credentials");
        System.out.println(error);

        mv.setViewName("login.jsp");
        return mv;
    }

    @GetMapping("/home-admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACADEMIC_ADMIN')")
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
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN', 'ACADEMIC_ADMIN')")
    public ModelAndView changePassword(ChangePasswordRequest dto) {
        ModelAndView mv = new ModelAndView();

        //Check if user is an administrator
        boolean isAdmin = ExtraUtilities.hasRole("ROLE_ADMIN");
        boolean isAcademicAdmin = ExtraUtilities.hasRole("ROLE_ACADEMIC_ADMIN");

        mv.setViewName("/home_student.jsp");

        if(isAdmin || isAcademicAdmin) {
            mv.setViewName("/home_admin.jsp");
            mv.addObject("recent_students", studentService.getAllRecentStudents());
        }else {
            Instant today = Instant.now();
            String day = DATE_TIME_FORMATTER.format(today);
            try {
                mv.addObject("lectures", lectureService.getAllLecturesByDay(day, "time"));
            } catch (KronosException e) {
                e.printStackTrace();
            }
        }

        try {
            authService.changePassword(dto);
            mv.addObject("successSetting", new SimpleMessageDto("Successfully changed password!"));
        }catch(KronosException e) {
            mv.addObject("errorSetting", new APIException(e.getMessage()));
        }

        try {
            mv.addObject("name", studentService.getName());
        } catch (KronosException e) {
            e.printStackTrace();
        }

        return mv;
    }

    @PostMapping("/contact")
    public ModelAndView contact(@RequestParam String email, @RequestParam String body) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login.jsp");

        System.out.println("posted");

       emailService.sendSimpleMessage(email, body);

       mv.addObject("success", new SimpleMessageDto("Successfully sent email!"));
       return mv;
    }
}
