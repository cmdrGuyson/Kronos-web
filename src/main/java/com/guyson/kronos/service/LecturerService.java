package com.guyson.kronos.service;

import com.guyson.kronos.domain.Lecturer;
import com.guyson.kronos.dto.LecturerDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.repository.LecturerRepository;
import com.guyson.kronos.repository.ModuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final ModuleRepository moduleRepository;

    @Transactional
    public LecturerDto addLecturer(LecturerDto dto) throws KronosException {

        //Check if lecturer with email is present
        Optional<Lecturer> existing = lecturerRepository.findLecturerByEmail(dto.getEmail());

        if(existing.isPresent()) {
            throw new KronosException("Email already exists");
        }

        Lecturer lecturer = lecturerRepository.save(map(dto));

        dto.setLecturerID(lecturer.getLecturerID());
        return dto;
    }

    @Transactional
    public List<LecturerDto> getAllLecturers() {
        return lecturerRepository.findAll().stream().map(this::mapDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteLecturer(int lecturerID) throws KronosException {

        //Find lecturer to be deleted
        lecturerRepository.findById(lecturerID).orElseThrow(() -> new KronosException("Lecturer not found"));

        //If lecturer teaches any modules
        if(moduleRepository.findFirstByLecturer_LecturerID(lecturerID).isPresent()) throw new KronosException("Lecturer has associated modules");

        lecturerRepository.deleteById(lecturerID);

    }


    //Method to map data transfer object to domain class
    private Lecturer map(LecturerDto dto) {
        return Lecturer.builder().firstName(dto.getFirstName()).createdAt(Instant.now())
                .lastName(dto.getLastName()).email(dto.getEmail()).type(dto.getType()).build();
    }

    //Method to map domain class to data transfer object
    private LecturerDto mapDto(Lecturer lecturer) {
        return new LecturerDto(lecturer.getLecturerID(), lecturer.getFirstName(), lecturer.getLastName(), lecturer.getEmail(), lecturer.getType());
    }

}
