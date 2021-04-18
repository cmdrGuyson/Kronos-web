package com.guyson.kronos;

import com.guyson.kronos.dto.ClassDto;
import com.guyson.kronos.enums.ClassType;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.ClassService;
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
class ClassServiceTests {

    @Autowired
    private ClassService classService;

    private int deleteClassId, classIdWithStudent;

    @Autowired
    private TestUtil testUtil;

    @BeforeAll
    public void init() throws KronosException {

        deleteClassId = testUtil.createClassToBeDeleted();
        classIdWithStudent = testUtil.createClassWithStudent();
    }

    @Test
    public void testAddClass() {

        ClassDto dto = new ClassDto();
        dto.setType(ClassType.LAW.getType());
        dto.setDescription("This is a test description");

        ClassDto result = classService.addClass(dto);

        assertNotNull(result);

        System.out.println("[TEST] Adding a class [PASSED]");
    }

    @Test
    public void testGetAllClasses() {
        List<ClassDto> results = classService.getAllClasses();

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all classes [PASSED]");
    }

    @Test
    public void testDeleteClass() throws KronosException {
        classService.deleteClass(deleteClassId);
        List<ClassDto> results = classService.getAllClasses();

        boolean isTrue = true;

        for (ClassDto dto : results) {
            if (dto.getClassID() == deleteClassId) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete class [PASSED]");
    }

    @Test
    public void testDeleteClassWithStudents() {

        boolean isTrue = false;

        try {
            classService.deleteClass(classIdWithStudent);
        } catch (KronosException e) {
            if (e.getMessage().equals("Class cannot be deleted as it has students")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to delete class with students [PASSED]");

    }


}
