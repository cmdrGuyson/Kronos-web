package com.guyson.kronos.service;

import com.guyson.kronos.domain.Lecture;
import com.guyson.kronos.domain.Module;
import com.guyson.kronos.domain.Room;
import com.guyson.kronos.dto.LectureDto;
import com.guyson.kronos.dto.ModuleDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.repository.LectureRepository;
import com.guyson.kronos.repository.ModuleRepository;
import com.guyson.kronos.repository.RoomRepository;
import com.guyson.kronos.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final ModuleRepository moduleRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public LectureDto addLecture(LectureDto dto, boolean updateFlag) throws KronosException {

        Room room = roomRepository.findById(dto.getRoomID()).orElseThrow(() -> new KronosException("Room not found"));
        Module module = moduleRepository.findById(dto.getModuleID()).orElseThrow(() -> new KronosException("Module not found"));

        Lecture lecture = map(dto, module, room);

        //Find any overlaps with existing lectures from the same module
        List<Lecture> list_byModule = lectureRepository.findAllByModuleAndDate(lecture.getModule(), lecture.getDate());
        List<Lecture> list_byRoom = lectureRepository.findAllByRoomAndDate(lecture.getRoom(), lecture.getDate());

        //Start time and end time of lecture to be added
        LocalTime lecture_start = lecture.getStartTime();
        LocalTime lecture_end = lecture_start.plusHours(lecture.getDuration());

        //Find any overlaps with other lectures of same module
        findOverlaps(list_byModule, lecture_start, lecture_end, false);

        //Find any overlaps with other lectures in the same room
        findOverlaps(list_byRoom, lecture_start, lecture_end, true);

        //If used to update a lecture
        if (updateFlag) {
            lectureRepository.findById(dto.getLectureID()).orElseThrow(() -> new KronosException("Lecture not found"));
            lecture.setLectureID(dto.getLectureID());
        }

        Lecture result = lectureRepository.save(lecture);

        dto.setLectureID(result.getLectureID());
        dto.setModule(mapDto(module));
        dto.setRoom(room);

        return dto;
    }

    @Transactional
    public List<LectureDto> getAllLectures() throws KronosException{

        //User object from security context holder to obtain current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If user is not found
        com.guyson.kronos.domain.User _user = userRepository.findById(user.getUsername()).orElseThrow(()->new KronosException("User not found"));

        //If user is an admin return lectures of all modules
        if(_user.getRole().equals("admin")) {
            return lectureRepository.findAll().stream().map(this::mapDto).collect(Collectors.toList());
        }
        //If user is a student return lectures of enrolled modules
        else {
            Set<Module> modules = _user.getModules();
            return lectureRepository.findAllByModuleIn(modules).stream().map(this::mapDto).collect(Collectors.toList());
        }


    }

    @Transactional
    public List<LectureDto> getAllLecturesByDay(String day) throws KronosException {

        //User object from security context holder to obtain current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = null;

        try {
            date = LocalDate.parse(day, date_formatter);
        }catch (Exception e) {
            throw new KronosException("Invalid date/time format");
        }


        //If user is not found
        com.guyson.kronos.domain.User _user = userRepository.findById(user.getUsername()).orElseThrow(()->new KronosException("User not found"));

        //If user is an admin return lectures of all modules
        if(_user.getRole().equals("admin")) {
            return lectureRepository.findAllByDate(date).stream().map(this::mapDto).collect(Collectors.toList());
        }
        //If user is a student return lectures of enrolled modules
        else {

            Set<Module> modules = _user.getModules();

            return lectureRepository.findAllByDateAndModuleIn(date, modules).stream().map(this::mapDto).collect(Collectors.toList());
        }

    }

    //Map data transfer object to domain class
    private Lecture map(LectureDto dto, Module module, Room room) throws KronosException{

        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate date;
        LocalTime time;

        try {

            date = LocalDate.parse(dto.getDate(), date_formatter);
            time = LocalTime.parse(dto.getStartTime(), time_formatter);

        }catch(Exception e){
            throw new KronosException("Invalid date/time format");
        }

        return Lecture.builder().date(date).startTime(time)
                .duration(dto.getDuration()).module(module)
                .room(room).build();

    }

    //Map domain class to Dto
    private LectureDto mapDto(Lecture lecture) {

        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("HH:mm");

        String date = date_formatter.format(lecture.getDate());
        String time = time_formatter.format(lecture.getStartTime());


        return new LectureDto(lecture.getLectureID(), date, time, lecture.getDuration(),
                lecture.getRoom().getRoomID(), lecture.getModule().getModuleID(), mapDto(lecture.getModule()), lecture.getRoom());

    }

    //Utility to map domain to dto to avoid @JsonBackReference
    private ModuleDto mapDto(Module module) {
        return new ModuleDto(module.getModuleID(), module.getLecturer().getLecturerID(), module.getCredits(), module.getDescription(), module.getName(), module.getLecturer());
    }

    private void findOverlaps(List<Lecture> list, LocalTime lecture_start, LocalTime lecture_end, boolean flag) throws KronosException{

        String error = "There is already a lecture of this module at this time";

        //Checking by room
        if(flag) {
           error = "There is already a lecture in this room at this time";
        }

        if (list.size() > 0) {

            for (Lecture l : list) {

                //Start time and end time of considered lecture
                LocalTime l_start = l.getStartTime();
                LocalTime l_end = l_start.plusHours(l.getDuration());

                int value = lecture_start.compareTo(l_start);
                if(value > 0){
                    //lecture_start is greater than l_start
                    int next_value = l_end.compareTo(lecture_start);
                    //l_end is greater than lecture_start
                    if(next_value > 0) throw new KronosException(error);
                }
                else if(value == 0) throw new KronosException(error);
                else {
                    //l_start is greater than lecture_start
                    int next_value = l_start.compareTo(lecture_end);
                    if(next_value < 0) throw new KronosException(error);
                }

            }
        }
    }

}
