package com.guyson.kronos.service;

import com.guyson.kronos.domain.Lecturer;
import com.guyson.kronos.domain.Module;
import com.guyson.kronos.dto.ModuleDto;
import com.guyson.kronos.dto.StudentModuleDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.repository.LecturerRepository;
import com.guyson.kronos.repository.ModuleRepository;
import com.guyson.kronos.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ModuleService {

    private ModuleRepository moduleRepository;
    private LecturerRepository lecturerRepository;
    private UserRepository userRepository;

    @Transactional
    public ModuleDto addModule(ModuleDto dto) throws KronosException {

        //If module with same name exists
        Optional existing = moduleRepository.findByName(dto.getName());

        if (existing.isPresent()) {
            throw new KronosException("Module already exists");
        }


        //Find lecturer associated with new module or give exception
        Lecturer lecturer = lecturerRepository.findById(dto.getLecturerID()).orElseThrow(()->new KronosException("Lecturer not found"));

        Module module = map(dto, lecturer);

        moduleRepository.save(module);

        dto.setLecturer(lecturer);
        dto.setModuleID(module.getModuleID());

        return dto;

    }

    @Transactional
    public List<ModuleDto> getAllModules() {
        return moduleRepository.findAll().stream().map(this::mapDto).collect(Collectors.toList());
    }

    @Transactional
    public void enroll(int moduleID) throws KronosException {

        Module module = moduleRepository.findById(moduleID).orElseThrow(() -> new KronosException("Module not found"));

        //User object from security context holder to obtain current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If student is not found
        com.guyson.kronos.domain.User student = userRepository.findById(user.getUsername()).orElseThrow(()->new KronosException("Student not found"));

        student.addModule(module);

        //Update in database
        userRepository.save(student);

    }

    @Transactional
    public void unroll(int moduleID) throws KronosException {

        Module module = moduleRepository.findById(moduleID).orElseThrow(() -> new KronosException("Module not found"));

        //User object from security context holder to obtain current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If student is not found
        com.guyson.kronos.domain.User student = userRepository.findById(user.getUsername()).orElseThrow(()->new KronosException("Student not found"));

        student.removeModule(module);

        //Update in database
        userRepository.save(student);

    }

    @Transactional
    public Set<StudentModuleDto> getMyModules() throws KronosException {

        //User object from security context holder to obtain current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If student is not found
        com.guyson.kronos.domain.User student = userRepository.findById(user.getUsername()).orElseThrow(()->new KronosException("Student not found"));

        //Get all modules of student
        Set<Module> _modules = student.getModules();

        Set<StudentModuleDto> modules = new HashSet<>();

        for(Module module : _modules) {
            modules.add(mapDto(module, true));
        }

        return modules;

    }

    @Transactional
    public Set<StudentModuleDto> getAllStudentModules() throws KronosException {

        //User object from security context holder to obtain current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If student is not found
        com.guyson.kronos.domain.User student = userRepository.findById(user.getUsername()).orElseThrow(()->new KronosException("Student not found"));

        //Get all modules of student
        Set<Module> _modules = student.getModules();

        List<Module> allModules = moduleRepository.findAll();

        Set<StudentModuleDto> modules = new HashSet<>();

        for(Module module : allModules) {
            if(_modules.contains(module)){
                modules.add(mapDto(module, true));
            }else{
                modules.add(mapDto(module, false));
            }
        }

        return modules;
    }


    private Module map(ModuleDto dto, Lecturer lecturer) {
        return Module.builder().createdAt(Instant.now())
                .credits(dto.getCredits()).description(dto.getDescription())
                .name(dto.getName()).lecturer(lecturer).build();
    }

    private ModuleDto mapDto(Module module) {
        return new ModuleDto(module.getModuleID(), module.getLecturer().getLecturerID(), module.getCredits(), module.getDescription(), module.getName(), module.getLecturer());
    }

    private StudentModuleDto mapDto(Module module, boolean isEnrolled) {
        return new StudentModuleDto(module.getModuleID(), module.getLecturer().getLecturerID(), module.getCredits(), module.getDescription(), module.getName(), module.getLecturer(), isEnrolled);
    }

}
