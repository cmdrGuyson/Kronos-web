package com.guyson.kronos;

import com.guyson.kronos.dto.ClassDto;
import com.guyson.kronos.dto.LecturerDto;
import com.guyson.kronos.enums.LecturerType;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.LecturerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class LecturerServiceTests {

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private TestUtil testUtil;

    @Test
    public void testAddLecturer() throws KronosException {
        LecturerDto dto = new LecturerDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail("lecturer@email.com");
        dto.setType(LecturerType.PERMANENT.getType());

        LecturerDto result = lecturerService.addLecturer(dto);

        assertNotNull(result);

        System.out.println("[TEST] Adding a lecturer [PASSED]");
    }

    @Test
    public void testAddLecturerWithExistingEmail() throws KronosException {

        boolean isTrue = false;

        LecturerDto dto = new LecturerDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail(testUtil.createLecturerForExistingEmail());
        dto.setType(LecturerType.PERMANENT.getType());

        try {
            lecturerService.addLecturer(dto);
        } catch (KronosException e) {
            if (e.getMessage().equals("Email already exists")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to add a lecturer with existing email [PASSED]");

    }

    @Test
    public void testAddLecturerWithInvalidEmail() {

        boolean isTrue = false;

        LecturerDto dto = new LecturerDto();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail("invalid");
        dto.setType(LecturerType.PERMANENT.getType());

        try {
            lecturerService.addLecturer(dto);
        } catch (KronosException e) {
            if (e.getMessage().equals("Invalid Email")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to add a lecturer with invalid email [PASSED]");

    }

    @Test
    public void testGetAllLecturers() {
        List<LecturerDto> results = lecturerService.getAllLecturers();

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all lecturers [PASSED]");

    }

    @Test
    public void testDeleteLecturer() throws KronosException {

        int deleteLecturerId = testUtil.createLecturerToBeDeleted();

        lecturerService.deleteLecturer(deleteLecturerId);
        List<LecturerDto> results = lecturerService.getAllLecturers();

        boolean isTrue = true;

        for (LecturerDto dto : results) {
            if (dto.getLecturerID() == deleteLecturerId) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete lecturer [PASSED]");
    }

    @Test
    public void testDeleteLecturerWithModule() {

        boolean isTrue = false;

        try {
            lecturerService.deleteLecturer(testUtil.createLecturerWithModule());
        } catch (KronosException e) {
            if (e.getMessage().equals("Lecturer has associated modules")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to delete lecturer with modules [PASSED]");

    }

}
