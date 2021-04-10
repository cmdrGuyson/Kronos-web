package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.FilterLectureDto;
import com.guyson.kronos.dto.LectureDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LectureService;
import com.guyson.kronos.service.ModuleService;
import com.guyson.kronos.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
public class LectureWebController {

    private final LectureService lectureService;
    private final ModuleService moduleService;
    private final RoomService roomService;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());
    private final DateTimeFormatter DATE_TIME_FORMATTER_2 = DateTimeFormatter.ofPattern("dd MMMM yyyy").withZone(ZoneId.systemDefault());
    private final DateTimeFormatter DATE_TIME_FORMATTER_HTML = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

    private ModelAndView getToday(ModelAndView _mv) {

        Instant today = Instant.now();
        String day = DATE_TIME_FORMATTER.format(today);
        System.out.println(DATE_TIME_FORMATTER_2.format(today));

        ModelAndView mv = _mv;

        if(mv == null) mv = new ModelAndView();

        mv.setViewName("lectures.jsp");

        try {
            mv.addObject("lectures", lectureService.getAllLecturesByDay(day, "time"));
            mv.addObject("day", DATE_TIME_FORMATTER_2.format(today));

            mv.addObject("inputDate", DATE_TIME_FORMATTER_HTML.format(today));
            mv = getDropdownInfo(mv);
        } catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
            e.printStackTrace();
        }

        return mv;
    }

    private ModelAndView getDropdownInfo(ModelAndView mv) {
        mv.addObject("rooms", roomService.getAllRooms());
        mv.addObject("modules", moduleService.getAllModules());

        return mv;
    }

    @GetMapping("/lectures")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ModelAndView viewTodaysLectures() {
        return getToday(null);
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
            mv = getDropdownInfo(mv);
        } catch (KronosException e) {
            e.printStackTrace();
        }

        return mv;
    }

    @PostMapping("/add-lecture")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addLecture(@RequestParam String date, @RequestParam String moduleID, @RequestParam String roomID, @RequestParam String startTime, @RequestParam String duration) {

        ModelAndView mv = getToday(null);

        try {
            LectureDto dto = new LectureDto();
            dto.setRoomID(Integer.parseInt(roomID));
            dto.setModuleID(Integer.parseInt(moduleID));
            dto.setDuration(Integer.parseInt(duration));

            LocalDate localDate = LocalDate.parse(date, DATE_TIME_FORMATTER_HTML);

            dto.setDate(DATE_TIME_FORMATTER.format(localDate));
            dto.setStartTime(startTime);


            lectureService.addLecture(dto, false);
            mv = getToday(null);
            mv.addObject("success", new SimpleMessageDto("Lecture added successfully!"));
        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @PostMapping("/delete-lecture")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteLecture(@RequestParam("lectureID") int lectureID) {

        ModelAndView mv = getToday(null);

        try {

            lectureService.deleteLecture(lectureID);
            mv = getToday(null);
            mv.addObject("success", new SimpleMessageDto("Lecture deleted successfully!"));

        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;

    }


}
