package com.guyson.kronos.service;

import com.guyson.kronos.domain.Class;
import com.guyson.kronos.domain.User;
import com.guyson.kronos.dto.StudentDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.repository.ClassRepository;
import com.guyson.kronos.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    @Transactional
    public StudentDto addStudent(StudentDto dto) throws KronosException{

        Optional existing = userRepository.findById(dto.getUsername());

         if (existing.isPresent()) {
             throw new KronosException("Username already exists");
         }

        Class _class = classRepository.findById(dto.getClassID()).orElseThrow(() -> new KronosException("Class not found"));

        User student = map(dto, _class);

        //Return the hashed password and class data in response
        dto.setPassword(student.getPassword());
        dto.set_class(_class);

        //Save the student
        userRepository.save(student);

        return dto;
    }

    @Transactional
    public List<StudentDto> getAllStudents() {
        return userRepository.findByRoleEquals("student").stream().map(this::mapDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteStudent(String username) throws KronosException {

        User student = userRepository.findById(username).orElseThrow(() -> new KronosException("Student not found"));

        //If attempted to delete an administrator
        if (student.getRole().equals("admin")) throw new KronosException("You cannot delete an administrator");

        //If attempted to delete a student already enrolled in modules
        if(student.getModules().size()>0) throw new KronosException("This student is already enrolled in modules");

        userRepository.deleteById(username);

    }

    //Method to map data transfer object to domain class
    private User map(StudentDto dto, Class _class) {
        return User.builder().firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role("student")
                ._class(_class)
                .createdAt(Instant.now())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getUsername().toUpperCase())).build();
    }

    //Method to map domain class to data transfer object
    private StudentDto mapDto(User student) {
        return new StudentDto(student.get_class().getClassID(), student.getUsername(), student.getFirstName(), student.getLastName(), student.get_class());
    }

}
