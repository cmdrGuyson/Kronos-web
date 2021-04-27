package com.guyson.kronos.service;

import com.guyson.kronos.domain.Class;
import com.guyson.kronos.dto.ClassDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.repository.ClassRepository;
import com.guyson.kronos.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public ClassDto addClass(ClassDto dto) {

        Class _class = classRepository.save(map(dto));
        dto.setClassID(_class.getClassID());
        return dto;
    }

    public List<ClassDto> getAllClasses() {
        return classRepository.findAll().stream().map(this::mapDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteClass(int classID) throws KronosException{

        classRepository.findById(classID).orElseThrow(() -> new KronosException("Class not found"));

        //If class has any students
        if (userRepository.findFirstBy_class_ClassID(classID).isPresent()) throw new KronosException("Class cannot be deleted as it has students");

        classRepository.deleteById(classID);

    }

    //Method to map data transfer object to domain class
    private Class map(ClassDto dto) {
        return Class.builder().type(dto.getType()).description(dto.getDescription()).createdAt(Instant.now()).build();
    }

    //Method to map domain class to data transfer object
    private ClassDto mapDto(Class _class) {
        return new ClassDto(_class.getClassID(), _class.getType(), _class.getDescription());
    }

}
