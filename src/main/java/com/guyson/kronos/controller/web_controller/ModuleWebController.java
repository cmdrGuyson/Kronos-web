package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class ModuleWebController {

    private final ModuleService moduleService;

    @GetMapping("/all-modules")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ModelAndView viewAllModules() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("modules.jsp");
        mv.addObject("modules", moduleService.getAllModules());
        return mv;
    }

    @GetMapping("/student-modules")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ModelAndView viewStudentModules() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("modules.jsp");
        try {
            mv.addObject("modules", moduleService.getAllStudentModules());
        } catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }
        return mv;
    }

    private ModelAndView getMyModules() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("modules.jsp");
        try {
            mv.addObject("modules", moduleService.getMyModules());
            mv.addObject("myModules", "My Modules");
        } catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }
        return mv;
    }

    @PostMapping("/enroll")
    @PreAuthorize("hasRole('STUDENT')")
    public ModelAndView enroll(@RequestParam String moduleID) {

      ModelAndView mv = getMyModules();

      try {
          moduleService.enroll(Integer.parseInt(moduleID));
          mv = getMyModules();
          mv.addObject("success", new SimpleMessageDto("Successfully enrolled in module!"));
      }catch (KronosException e) {
          mv.addObject("error", new APIException(e.getMessage()));
      }

      return mv;
    }

    @PostMapping("/unroll")
    @PreAuthorize("hasRole('STUDENT')")
    public ModelAndView unroll(@RequestParam String moduleID) {

        ModelAndView mv = getMyModules();

        try {
            moduleService.unroll(Integer.parseInt(moduleID));
            mv = getMyModules();
            mv.addObject("success", new SimpleMessageDto("Successfully unrolled from module!"));
        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @GetMapping("/my-modules")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ModelAndView viewMyModules() {
        return getMyModules();
    }
}
