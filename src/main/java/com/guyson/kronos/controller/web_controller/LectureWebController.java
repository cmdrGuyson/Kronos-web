package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.FilterLectureDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
public class LectureWebController {

    private final LectureService lectureService;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());
    private final DateTimeFormatter DATE_TIME_FORMATTER_2 = DateTimeFormatter.ofPattern("dd MMMM yyyy").withZone(ZoneId.systemDefault());
    private final DateTimeFormatter DATE_TIME_FORMATTER_HTML = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/lectures")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ModelAndView viewTodaysLectures() {

        Instant today = Instant.now();
        String day = DATE_TIME_FORMATTER.format(today);

        //day = "31-03-2021";

        System.out.println(DATE_TIME_FORMATTER_2.format(today));

        ModelAndView mv = new ModelAndView();
        mv.setViewName("lectures.jsp");
        try {
            mv.addObject("lectures", lectureService.getAllLecturesByDay(day, "time"));
            mv.addObject("day", DATE_TIME_FORMATTER_2.format(today));
        } catch (KronosException e) {
            e.printStackTrace();
        }

        return mv;
    }

    @PostMapping("/filter-lectures")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ModelAndView filterLectures(FilterLectureDto dto) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("lectures.jsp");

        try {
            System.out.println(dto.getDate());

            LocalDate date = LocalDate.parse( dto.getDate() , DATE_TIME_FORMATTER_HTML );
            String day = DATE_TIME_FORMATTER.format(date);

            mv.addObject("lectures", lectureService.getAllLecturesByDay(day, dto.getOrder()));
            mv.addObject("day", DATE_TIME_FORMATTER_2.format(date));
            mv.addObject("inputDate", dto.getDate());
        } catch (KronosException e) {
            e.printStackTrace();
        }

        return mv;
    }

}
