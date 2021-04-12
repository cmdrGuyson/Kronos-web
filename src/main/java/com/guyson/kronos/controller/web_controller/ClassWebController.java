package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.ClassDto;
import com.guyson.kronos.dto.LecturerDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.enums.ClassType;
import com.guyson.kronos.enums.LecturerType;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.ClassService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
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
public class ClassWebController {

    private ClassService classService;

    @GetMapping("/classes")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllClasses() {
        return getClasses();
    }

    private ModelAndView getClasses() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("classes.jsp");
        mv.addObject("classes", classService.getAllClasses());

        List<String> types = new ArrayList<>();

        for (ClassType classType : ClassType.values()) {
            types.add(classType.getType());
        }

        mv.addObject("types", types);

        return mv;
    }

    @PostMapping("/add-class")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addClass(@RequestParam String type, @RequestParam String description) {


        ClassDto dto = new ClassDto();
        dto.setType(type);
        dto.setDescription(description);

        classService.addClass(dto);
        ModelAndView mv = getClasses();
        mv.addObject("success", new SimpleMessageDto("Class added successfully!"));

        return mv;
    }

    @PostMapping("/delete-class")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addClass(@RequestParam String classID) {

        ModelAndView mv = getClasses();
        try {
            classService.deleteClass(Integer.parseInt(classID));
            mv = getClasses();
            mv.addObject("success", new SimpleMessageDto("Class deleted successfully!"));
        } catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

}
