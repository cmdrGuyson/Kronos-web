package com.guyson.kronos.repository;

import com.guyson.kronos.domain.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {

    Optional<Lecturer> findLecturerByEmail(String email);

}
