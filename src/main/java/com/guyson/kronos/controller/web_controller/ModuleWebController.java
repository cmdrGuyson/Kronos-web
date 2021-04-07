package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class ModuleWebController {

    private final ModuleService moduleService;

    @GetMapping("/modules")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ModelAndView viewAllModules() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("modules.jsp");
        mv.addObject("modules", moduleService.getAllModules());
        return mv;
    }
}
