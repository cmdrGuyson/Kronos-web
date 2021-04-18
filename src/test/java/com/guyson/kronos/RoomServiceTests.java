package com.guyson.kronos;

import com.guyson.kronos.dto.RoomDto;
import com.guyson.kronos.enums.RoomType;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.service.RoomService;
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
public class RoomServiceTests {

    @Autowired
    private RoomService roomService;

    private int deleteRoomId, roomIdWithLecture;

    @Autowired
    private TestUtil testUtil;

    @BeforeAll
    public void init() throws KronosException {

        deleteRoomId = testUtil.createRoomToBeDeleted();
        roomIdWithLecture = testUtil.createRoomWithLectures();

    }

    @Test
    public void testAddRoom() {
        RoomDto dto = new RoomDto();
        dto.setType(RoomType.HALL.getType());
        dto.setDescription("This is a test description");

        RoomDto result = roomService.addRoom(dto);
        assertNotNull(result);

        System.out.println("[TEST] Adding a room [PASSED]");
    }

    @Test
    public void testGetAllRooms() {
        List<RoomDto> results = roomService.getAllRooms();

        System.out.println(results.size());

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all rooms [PASSED]");
    }

    @Test
    public void testDeleteRoom() throws KronosException {
        roomService.deleteRoom(deleteRoomId);
        List<RoomDto> results = roomService.getAllRooms();

        boolean isTrue = true;

        for (RoomDto dto : results) {
            if (dto.getRoomID() == deleteRoomId) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete room [PASSED]");
    }

    @Test
    public void testDeleteRoomWithLecture() {

        boolean isTrue = false;

        try {
            roomService.deleteRoom(roomIdWithLecture);
        } catch (KronosException e) {
            if (e.getMessage().equals("Room has lectures")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to delete room with lecture [PASSED]");
    }

}
