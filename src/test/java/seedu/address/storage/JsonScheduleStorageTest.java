package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalMeetings.TEAM_SYNC;
import static seedu.address.testutil.TypicalMeetings.TEAM_SYNC_1;
import static seedu.address.testutil.TypicalMeetings.TEAM_SYNC_2;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyScheduleList;
import seedu.address.model.ScheduleList;

public class JsonScheduleStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonScheduleStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readScheduleList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readScheduleList(null));
    }

    private java.util.Optional<ReadOnlyScheduleList> readScheduleList(String filePath) throws Exception {
        return new JsonScheduleStorage(Paths.get(filePath))
                .readScheduleList(TEST_DATA_FOLDER.resolve(filePath));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readScheduleList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () ->
                readScheduleList("notJsonFormatSchedule.json"));
    }

    @Test
    public void read_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempSchedule.json");
        ScheduleList original = (ScheduleList) getTypicalMeetings();
        JsonScheduleStorage jsonScheduleStorage = new JsonScheduleStorage(filePath);

        // Save in new file and read back
        jsonScheduleStorage.saveScheduleList(original, filePath);
        ReadOnlyScheduleList readBack = jsonScheduleStorage.readScheduleList(filePath).get();
        assertEquals(original, new ScheduleList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMeeting(TEAM_SYNC_1);
        original.removeMeeting(TEAM_SYNC);
        jsonScheduleStorage.saveScheduleList(original, filePath);
        readBack = jsonScheduleStorage.readScheduleList(filePath).get();
        assertEquals(original, new ScheduleList(readBack));

        // Save and read without specifying file path
        original.addMeeting(TEAM_SYNC_2);
        jsonScheduleStorage.saveScheduleList(original); // file path not specified
        readBack = jsonScheduleStorage.readScheduleList().get(); // file path not specified
        assertEquals(original, new ScheduleList(readBack));
    }

    @Test
    public void getScheduleListFilePath() {
        Path filePath = testFolder.resolve("TempSchedule.json");
        JsonScheduleStorage jsonScheduleStorage = new JsonScheduleStorage(filePath);
        assertEquals(filePath, jsonScheduleStorage.getScheduleListFilePath());
    }

    @Test
    public void handleCorruptedFile() throws Exception {
        Path filePath = TEST_DATA_FOLDER.resolve("invalidEntriesMeetingsSchedule.json");
        JsonScheduleStorage jsonScheduleStorage = new JsonScheduleStorage(filePath);
        jsonScheduleStorage.handleCorruptedFile();
        boolean hasBakFile = false;
        Stream<Path> files = Files.list(TEST_DATA_FOLDER);
        Iterator<Path> iter = files.iterator();
        Path bakFile = null;
        while (iter.hasNext()) {
            Path file = iter.next();
            if (Pattern.matches(".*invalidEntriesMeetingsSchedule.json.*.bak", file.toString())) {
                hasBakFile = true;
                bakFile = file;
            }
        }
        files.close();
        assertTrue(hasBakFile);
        bakFile.toFile().delete();
    }
}
