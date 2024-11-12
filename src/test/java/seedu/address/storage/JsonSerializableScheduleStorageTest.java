package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ScheduleList;
import seedu.address.testutil.TypicalMeetings;

public class JsonSerializableScheduleStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonSerializableScheduleStorageTest");
    private static final Path TYPICAL_SCHEDULES_FILE = TEST_DATA_FOLDER
            .resolve("typicalMeetingsSchedule.json");
    private static final Path INVALID_ENTRIES_SCHEDULE_FILE = TEST_DATA_FOLDER
            .resolve("invalidEntriesMeetingsSchedule.json");
    private static final Path DUPLICATE_ENTRIES_SCHEDULE_FILE = TEST_DATA_FOLDER
            .resolve("duplicateMeetingsSchedule.json");

    @TempDir
    public Path testFolder;

    @Test
    public void toModelType_success() throws Exception {
        JsonSerializableScheduleStorage dataFromFile = JsonUtil
            .readJsonFile(TYPICAL_SCHEDULES_FILE,
                    JsonSerializableScheduleStorage.class).get();
        assertDoesNotThrow(dataFromFile::toModelType);
        ScheduleList scheduleListFromFile = dataFromFile.toModelType();
        ScheduleList typicalScheduleList = (ScheduleList) TypicalMeetings.getTypicalMeetings();
        assertEquals(scheduleListFromFile, typicalScheduleList);
        assertFalse(JsonSerializableScheduleStorage.hasErrorConvertingToModelType());
    }

    @Test
    public void toModelType_invalidEntriesFile_success() throws Exception {
        JsonSerializableScheduleStorage dataFromFile = JsonUtil
            .readJsonFile(INVALID_ENTRIES_SCHEDULE_FILE,
                    JsonSerializableScheduleStorage.class).get();
        assertDoesNotThrow(dataFromFile::toModelType);
        ScheduleList scheduleListFromFile = dataFromFile.toModelType();
        ScheduleList expectedScheduleList = new ScheduleList();
        expectedScheduleList.addMeeting(TypicalMeetings.CLIENT_REVIEW);
        assertEquals(scheduleListFromFile, expectedScheduleList);
        assertTrue(JsonSerializableScheduleStorage.hasErrorConvertingToModelType());
    }

    @Test
    public void toModelType_duplicateEntriesFile_success() throws Exception {
        JsonSerializableScheduleStorage dataFromFile = JsonUtil
            .readJsonFile(DUPLICATE_ENTRIES_SCHEDULE_FILE,
                    JsonSerializableScheduleStorage.class).get();
        assertDoesNotThrow(dataFromFile::toModelType);
        ScheduleList scheduleListFromFile = dataFromFile.toModelType();
        ScheduleList typicalScheduleList = (ScheduleList) TypicalMeetings.getTypicalMeetings();
        assertEquals(scheduleListFromFile, typicalScheduleList);
        assertTrue(JsonSerializableScheduleStorage.hasErrorConvertingToModelType());
    }
}
