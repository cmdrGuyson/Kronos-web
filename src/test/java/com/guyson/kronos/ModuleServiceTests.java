package com.guyson.kronos;

import com.guyson.kronos.dto.LecturerDto;
import com.guyson.kronos.dto.ModuleDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.ModuleService;
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
public class ModuleServiceTests {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private TestUtil testUtil;

    private int lecturerId, deleteModuleId;

    @BeforeAll
    public void init() throws KronosException {
        lecturerId = testUtil.createLecturerForModule();
        deleteModuleId = testUtil.createModuleToBeDeleted(lecturerId);
    }

    @Test
    public void testAddModule() throws KronosException {

        ModuleDto dto = new ModuleDto();
        dto.setName("testAddModule");
        dto.setDescription("This is a test description");
        dto.setCredits(20);
        dto.setLecturerID(lecturerId);

        ModuleDto result = moduleService.addModule(dto);

        assertNotNull(result);

        System.out.println("[TEST] Adding a module [PASSED]");

    }

    @Test
    public void testAddModuleWithExistingName() throws KronosException {

        boolean isTrue = false;

        ModuleDto dto = new ModuleDto();
        dto.setName(testUtil.createModuleWithSameName());
        dto.setDescription("This is a test description");
        dto.setCredits(20);
        dto.setLecturerID(lecturerId);

        try {
            moduleService.addModule(dto);
        } catch (KronosException e) {
            if (e.getMessage().equals("Module already exists")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to add module with existing name [PASSED]");

    }

    @Test
    public void testGetAllModules() {
        List<ModuleDto> results = moduleService.getAllModules();

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all modules [PASSED]");

    }

    @Test
    public void testDeleteModule() throws KronosException {
        moduleService.deleteModule(deleteModuleId);

        List<ModuleDto> results = moduleService.getAllModules();

        boolean isTrue = true;

        for (ModuleDto dto : results) {
            if (dto.getModuleID() == deleteModuleId) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete module [PASSED]");
    }

}
