package seedu.address.storage.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.meetup.TypicalMeetUps.NETWORKING_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.PITCH_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.THIRD_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.MeetUpList;
import seedu.address.model.ReadOnlyMeetUpList;

public class JsonMeetUpListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMeetUpListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMeetUpList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMeetUpList(null));
    }

    private java.util.Optional<ReadOnlyMeetUpList> readMeetUpList(String filePath) throws Exception {
        return new JsonMeetUpListStorage(Paths.get(filePath)).readMeetUpList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMeetUpList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readMeetUpList("notJsonFormatMeetUpList.json"));
    }

    @Test
    public void readBuyerList_invalidMeetUpList_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readMeetUpList("invalidMeetUpList.json"));
    }

    @Test
    public void readBuyerList_invalidAndValidMeetUpList_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readMeetUpList("invalidAndValidMeetUpList.json"));
    }

    @Test
    public void readAndSaveMeetUpList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMeetUpList.json");
        MeetUpList original = getTypicalMeetUpList();
        JsonMeetUpListStorage jsonMeetUpListStorage = new JsonMeetUpListStorage(filePath);

        // Save in new file and read back
        jsonMeetUpListStorage.saveMeetUpList(original, filePath);
        ReadOnlyMeetUpList readBack = jsonMeetUpListStorage.readMeetUpList(filePath).get();
        assertEquals(original, new MeetUpList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMeetUp(NETWORKING_MEETUP);
        original.removeMeetUp(THIRD_MEETUP);
        jsonMeetUpListStorage.saveMeetUpList(original, filePath);
        readBack = jsonMeetUpListStorage.readMeetUpList(filePath).get();
        assertEquals(original, new MeetUpList(readBack));

        // Save and read without specifying file path
        original.addMeetUp(PITCH_MEETUP);
        jsonMeetUpListStorage.saveMeetUpList(original); // file path not specified
        readBack = jsonMeetUpListStorage.readMeetUpList().get(); // file path not specified
        assertEquals(original, new MeetUpList(readBack));

    }

    @Test
    public void saveMeetUpList_nullMeetUpList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMeetUpList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code buyerList} at the specified {@code filePath}.
     */
    private void saveMeetUpList(ReadOnlyMeetUpList meetUpList, String filePath) {
        try {
            new JsonMeetUpListStorage(Paths.get(filePath))
                    .saveMeetUpList(meetUpList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMeetUpList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMeetUpList(new MeetUpList(), null));
    }
}
