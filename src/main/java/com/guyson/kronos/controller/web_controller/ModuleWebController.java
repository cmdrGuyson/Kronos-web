package com.guyson.kronos.controller.web_controller;

import com.guyson.kronos.dto.LecturerDto;
import com.guyson.kronos.dto.ModuleDto;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.APIException;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LecturerService;
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
    private final LecturerService lecturerService;

    @GetMapping("/all-modules")
    @PreAuthorize("hasAnyRole('ACADEMIC_ADMIN', 'STUDENT')")
    public ModelAndView viewAllModules() {
        return getModules();
    }

    // Reusable function to get all modules as ModelAndView
    private ModelAndView getModules() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("modules.jsp");
        mv.addObject("modules", moduleService.getAllModules());
        mv.addObject("lecturers", lecturerService.getAllLecturers());
        return mv;
    }

    @GetMapping("/student-modules")
    @PreAuthorize("hasAnyRole('ACADEMIC_ADMIN', 'STUDENT')")
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

    // Reusable function to get all modules of logged in student as ModelAndView
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

          // Enroll in module and return user to "Modules" page
          moduleService.enroll(Integer.parseInt(moduleID));
          mv = getMyModules();
          mv.addObject("success", new SimpleMessageDto("Successfully enrolled in module!"));
      } catch (KronosException e) {
          mv.addObject("error", new APIException(e.getMessage()));
      }

      return mv;
    }

    @PostMapping("/unroll")
    @PreAuthorize("hasRole('STUDENT')")
    public ModelAndView unroll(@RequestParam String moduleID) {

        ModelAndView mv = getMyModules();

        try {

            // Enroll from module and return user to "Modules" page
            moduleService.unroll(Integer.parseInt(moduleID));
            mv = getMyModules();
            mv.addObject("success", new SimpleMessageDto("Successfully unrolled from module!"));
        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @GetMapping("/my-modules")
    @PreAuthorize("hasRole('STUDENT')")
    public ModelAndView viewMyModules() {
        return getMyModules();
    }

    @PostMapping("/add-module")
    @PreAuthorize("hasRole('ACADEMIC_ADMIN')")
    public ModelAndView addModule(@RequestParam String lecturerID, @RequestParam String name, @RequestParam String description, @RequestParam String credits) {

        ModelAndView mv = getModules();

        try {

            // Create Module dto from user input
            ModuleDto dto = new ModuleDto();
            dto.setCredits(Integer.parseInt(credits));
            dto.setName(name);
            dto.setDescription(description);
            dto.setLecturerID(Integer.parseInt(lecturerID));

            // Add module and return user to "Modules" page
            moduleService.addModule(dto);
            mv = getModules();
            mv.addObject("success", new SimpleMessageDto("Module added successfully!"));

        } catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @PostMapping("/delete-module")
    @PreAuthorize("hasRole('ACADEMIC_ADMIN')")
    public ModelAndView deleteModule(@RequestParam("moduleID") int moduleID) {

        ModelAndView mv = getModules();

        try {

            // Delete module and return user to "Modules" page
            moduleService.deleteModule(moduleID);
            mv = getModules();
            mv.addObject("success", new SimpleMessageDto("Module deleted successfully!"));

        }catch (KronosException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;

    }
}
