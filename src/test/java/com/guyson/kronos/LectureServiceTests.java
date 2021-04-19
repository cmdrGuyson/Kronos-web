package com.guyson.kronos;

import com.guyson.kronos.dto.LectureDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LectureService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class LectureServiceTests {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private TestUtil testUtil;

    private int moduleId, roomId, lectureToBeDeleted;
    private LectureDto lectureToBeUpdated, overlappingLecture;

    @BeforeAll
    public void init() throws KronosException {
        moduleId = testUtil.createModuleForLecture();
        roomId = testUtil.createRoomForLecture();
        lectureToBeDeleted = testUtil.createLectureToBeDeleted(moduleId, roomId);
        lectureToBeUpdated = testUtil.createLectureToBeUpdated(moduleId, roomId);
        overlappingLecture = testUtil.createLectureWithOverlappingTime(moduleId, roomId);
    }

    @Test
    public void testAddLecture() throws KronosException {

        LectureDto lectureDto = new LectureDto();
        lectureDto.setDuration(3);
        lectureDto.setStartTime("15:00");
        lectureDto.setDate("10-04-2021");
        lectureDto.setModuleID(moduleId);
        lectureDto.setRoomID(roomId);

        LectureDto result = lectureService.addLecture(lectureDto, false);

        assertNotNull(result);

        System.out.println("[TEST] Adding a lecture [PASSED]");

    }

    @Test
    public void testAddLectureWithOverlappingTime() {
        LectureDto lectureDto = new LectureDto();
        lectureDto.setDuration(3);
        lectureDto.setStartTime(overlappingLecture.getStartTime());
        lectureDto.setDate(overlappingLecture.getDate());
        lectureDto.setModuleID(moduleId);
        lectureDto.setRoomID(roomId);

        boolean isTrue = false;

        try {
            lectureService.addLecture(lectureDto, false);
        } catch (KronosException e) {
            if (e.getMessage().equals("There is already a lecture of this module at this time")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to add lecture with overlapping time [PASSED]");
    }

    @Test
    public void testAddLectureWithInvalidDuration() {
        LectureDto lectureDto = new LectureDto();
        lectureDto.setDuration(69);
        lectureDto.setStartTime("15:00");
        lectureDto.setDate("10-04-2021");
        lectureDto.setModuleID(moduleId);
        lectureDto.setRoomID(roomId);

        boolean isTrue = false;

        try {
            lectureService.addLecture(lectureDto, false);
        } catch (KronosException e) {
            if (e.getMessage().equals("Duration is too long")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to add lecture with overlapping time [PASSED]");
    }

    @Test
    public void testGetAllLectures() {
        List<LectureDto> results = lectureService.getAllLecturesForTesting();

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all lectures [PASSED]");
    }

    @Test
    public void testUpdateLecture() throws KronosException {

        lectureToBeUpdated.setDate("14-04-2021");
        LectureDto result = lectureService.addLecture(lectureToBeUpdated, true);

        assertEquals(result.getDate(), "14-04-2021");

        System.out.println("[TEST] Update lecture [PASSED]");
    }

    @Test
    public void testDeleteLecture() throws KronosException {
        lectureService.deleteLecture(lectureToBeDeleted);

        List<LectureDto> results = lectureService.getAllLecturesForTesting();

        boolean isTrue = true;

        for (LectureDto dto : results) {
            if (dto.getLectureID() == lectureToBeDeleted) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete lecture [PASSED]");
    }

}
