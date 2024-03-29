package com.guyson.kronos.repository;

import com.guyson.kronos.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

    Optional<Module> findByName(String name);

    Optional<Module> findFirstByLecturer_LecturerID(int lecturerID);
}
