package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
public class LectureWebController {

    private LectureService lectureService;

    @GetMapping("/lectures")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ModelAndView viewTodaysLectures() {


        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());
        DateTimeFormatter DATE_TIME_FORMATTER_2 = DateTimeFormatter.ofPattern("dd MMMM yyyy").withZone(ZoneId.systemDefault());
        Instant today = Instant.now();
        String day = DATE_TIME_FORMATTER.format(today);

        day = "31-03-2021";

        System.out.println(DATE_TIME_FORMATTER_2.format(today));

        ModelAndView mv = new ModelAndView();
        mv.setViewName("lectures.jsp");
        try {
            mv.addObject("lectures", lectureService.getAllLecturesByDay(day));
            mv.addObject("day", DATE_TIME_FORMATTER_2.format(today));
        } catch (KronosException e) {
            e.printStackTrace();
        }

        return mv;
    }

}
