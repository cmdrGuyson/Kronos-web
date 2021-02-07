package com.guyson.kronos.service;

import com.guyson.kronos.domain.Lecturer;
import com.guyson.kronos.domain.Module;
import com.guyson.kronos.dto.ModuleDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.repository.LecturerRepository;
import com.guyson.kronos.repository.ModuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ModuleService {

    private ModuleRepository moduleRepository;
    private LecturerRepository lecturerRepository;

    @Transactional
    public ModuleDto addModule(ModuleDto dto) throws KronosException {

        //Find lecturer associated with new module or give exception
        Lecturer lecturer = lecturerRepository.findById(dto.getLecturerID()).orElseThrow(()->new KronosException("Lecturer not found"));

        Module module = map(dto, lecturer);

        dto.setLecturer(lecturer);

        moduleRepository.save(module);

        return dto;

    }

    @Transactional
    public List<ModuleDto> getAllModules() {
        return moduleRepository.findAll().stream().map(this::mapDto).collect(Collectors.toList());
    }


    private Module map(ModuleDto dto, Lecturer lecturer) {
        return Module.builder().createdAt(Instant.now())
                .credits(dto.getCredits()).description(dto.getDescription())
                .name(dto.getName()).lecturer(lecturer).build();
    }

    private ModuleDto mapDto(Module module) {
        return new ModuleDto(module.getModuleID(), module.getLecturer().getLecturerID(), module.getCredits(), module.getDescription(), module.getName(), module.getLecturer());
    }

}
