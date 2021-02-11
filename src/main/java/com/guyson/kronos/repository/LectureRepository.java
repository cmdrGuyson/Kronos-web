package com.guyson.kronos.repository;

import com.guyson.kronos.domain.Lecture;
import com.guyson.kronos.domain.Module;
import com.guyson.kronos.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    List<Lecture> findAllByModuleAndDate(Module module, LocalDate date);

    List<Lecture> findAllByRoomAndDate(Room room, LocalDate date);

    List<Lecture> findAllByModuleIn(Set<Module> modules);

    List<Lecture> findAllByDate(LocalDate date);

    List<Lecture> findAllByDateAndModuleIn(LocalDate date, Set<Module> modules);

}
