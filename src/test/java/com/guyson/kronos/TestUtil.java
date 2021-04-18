package com.guyson.kronos;

import com.guyson.kronos.domain.Lecture;
import com.guyson.kronos.dto.*;
import com.guyson.kronos.enums.ClassType;
import com.guyson.kronos.enums.LecturerType;
import com.guyson.kronos.enums.RoomType;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestUtil {

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final ClassService classService;

    @Autowired
    private final LecturerService lecturerService;

    @Autowired
    private final ModuleService moduleService;

    @Autowired
    private final LectureService lectureService;

    @Autowired
    private final StudentService studentService;

    public int createClassToBeDeleted() {
        ClassDto dto = new ClassDto();
        dto.setType(ClassType.BUSINESS_MANAGEMENT.getType());
        dto.setDescription("This is a test description");

        ClassDto result = classService.addClass(dto);

        return result.getClassID();
    }

    public int createRoomToBeDeleted() {
        //Create a basic room to be deleted
        RoomDto dto = new RoomDto();
        dto.setType(RoomType.CLASSROOM.getType());
        dto.setDescription("This is a test description");

        RoomDto result = roomService.addRoom(dto);

        return result.getRoomID();
    }

    public String createLecturerForExistingEmail() throws KronosException {

        LecturerDto dto = new LecturerDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail("createLecturerForExistingEmail@email.com");
        dto.setType(LecturerType.PERMANENT.getType());

        lecturerService.addLecturer(dto);

        return dto.getEmail();
    }

    public int createLecturerToBeDeleted() throws KronosException {
        LecturerDto dto = new LecturerDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail("createLecturerToBeDeleted@email.com");
        dto.setType(LecturerType.PERMANENT.getType());

        LecturerDto result = lecturerService.addLecturer(dto);

        return result.getLecturerID();
    }

    public int createLecturerForModule() throws KronosException {
        LecturerDto dto = new LecturerDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail("createLecturerForModule@email.com");
        dto.setType(LecturerType.PERMANENT.getType());

        LecturerDto result = lecturerService.addLecturer(dto);

        return result.getLecturerID();
    }

    public String createModuleWithSameName() throws KronosException {

        LecturerDto dto = new LecturerDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail("createModuleWithSameName@email.com");
        dto.setType(LecturerType.PERMANENT.getType());

        LecturerDto lecturerDto = lecturerService.addLecturer(dto);

        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setLecturerID(lecturerDto.getLecturerID());
        moduleDto.setDescription("This is a description");
        moduleDto.setName("createModuleWithSameName");

        moduleService.addModule(moduleDto);

        return moduleDto.getName();

    }

    public int createModuleToBeDeleted(int lecturerId) throws KronosException {
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setLecturerID(lecturerId);
        moduleDto.setDescription("This is a description");
        moduleDto.setName("createModuleToBeDeleted");

        moduleService.addModule(moduleDto);

        return moduleDto.getModuleID();
    }

    public int createLecturerWithModule() throws KronosException {

        LecturerDto dto = new LecturerDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail("createLecturerWithModule@email.com");
        dto.setType(LecturerType.PERMANENT.getType());

        LecturerDto result = lecturerService.addLecturer(dto);

        //Add Module
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setLecturerID(result.getLecturerID());
        moduleDto.setDescription("This is a description");
        moduleDto.setName("createLecturerWithModule Module");

        moduleService.addModule(moduleDto);

        return result.getLecturerID();

    }

    public int createRoomWithLectures() throws KronosException {
        //Create lecturer
        LecturerDto lecturerDto = new LecturerDto();
        lecturerDto.setType(LecturerType.VISITING.getType());
        lecturerDto.setEmail("createRoomWithLectures@email.com");
        lecturerDto.setFirstName("First");
        lecturerDto.setLastName("Last");

        LecturerDto lecturerResult = lecturerService.addLecturer(lecturerDto);

        //Add Module
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setLecturerID(lecturerResult.getLecturerID());
        moduleDto.setDescription("This is a description");
        moduleDto.setName("createRoomWithLectures Module");

        ModuleDto moduleResult = moduleService.addModule(moduleDto);

        //Create room for lecture
        RoomDto dto = new RoomDto();
        dto.setType(RoomType.CLASSROOM.getType());
        dto.setDescription("This is a test description");

        RoomDto result = roomService.addRoom(dto);

        //Add lecture to room
        LectureDto lectureDto = new LectureDto();
        lectureDto.setDuration(3);
        lectureDto.setStartTime("15:00");
        lectureDto.setDate("01-02-2021");
        lectureDto.setModuleID(moduleDto.getModuleID());
        lectureDto.setRoomID(result.getRoomID());

        lectureService.addLecture(lectureDto, false);

        return result.getRoomID();
    }

    public int createClassWithStudent() throws KronosException {
        ClassDto classDto = new ClassDto();
        classDto.setType(ClassType.SOFTWARE_ENGINEERING.getType());
        classDto.setDescription("This is a test description");

        ClassDto classResult = classService.addClass(classDto);

        StudentDto dto = new StudentDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setUsername("createClassWithStudent");
        dto.setClassID(classResult.getClassID());

        studentService.addStudent(dto);

        return classResult.getClassID();
    }

}
