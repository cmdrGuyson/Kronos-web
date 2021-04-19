package com.guyson.kronos;

import com.guyson.kronos.dto.StudentDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class StudentServiceTests {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TestUtil testUtil;

    private int classId;
    private String studentToBeDeleted, studentWithSameUsername;

    @BeforeAll
    public void init() throws KronosException {
        classId = testUtil.createClassForStudent();
        studentToBeDeleted = testUtil.createStudentToBeDeleted(classId);
        studentWithSameUsername = testUtil.createStudentWithSameUsername(classId);
    }

    @Test
    public void testAddStudent() throws KronosException {

        StudentDto dto = new StudentDto();
        dto.setUsername("testAddStudent");
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setClassID(classId);

        StudentDto result = studentService.addStudent(dto);

        assertNotNull(result);

        System.out.println("[TEST] Adding a student [PASSED]");

    }

    @Test
    public void testAddStudentWithExistingUsername() {

        StudentDto dto = new StudentDto();
        dto.setUsername(studentWithSameUsername);
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setClassID(classId);

        boolean isTrue = false;

        try {
            studentService.addStudent(dto);
        } catch (KronosException e) {
            if (e.getMessage().equals("Username already exists")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to add student with existing username [PASSED]");

    }

    @Test
    public void testGetAllStudents() {
        List<StudentDto> results = studentService.getAllStudents();

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all students [PASSED]");

    }

    @Test
    public void testDeleteStudent() throws KronosException {
        studentService.deleteStudent(studentToBeDeleted);

        List<StudentDto> results = studentService.getAllStudents();

        boolean isTrue = true;

        for (StudentDto dto : results) {
            if (dto.getUsername() == studentToBeDeleted) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete student [PASSED]");
    }
}
