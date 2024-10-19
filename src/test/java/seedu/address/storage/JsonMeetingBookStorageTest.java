package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.MEETING_BEDOK;
import static seedu.address.testutil.TypicalMeetings.MEETING_CLEMENTI;
import static seedu.address.testutil.TypicalMeetings.MEETING_DOVER;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.MeetingBook;
import seedu.address.model.ReadOnlyMeetingBook;

public class JsonMeetingBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMeetingBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMeetingBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMeetingBook(null));
    }

    private java.util.Optional<ReadOnlyMeetingBook> readMeetingBook(String filePath) throws Exception {
        return new JsonMeetingBookStorage(Paths.get(filePath)).readMeetingBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMeetingBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readMeetingBook("notJsonFormatMeetingBook.json"));
    }

    @Test
    public void readMeetingBook_invalidMeetingMeetingBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readMeetingBook("invalidMeetingMeetingBook.json"));
    }

    @Test
    public void readMeetingBook_invalidAndValidMeetingMeetingBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readMeetingBook("invalidAndValidMeetingBook.json"));
    }

    @Test
    public void readAndSaveMeetingBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMeetingBook.json");
        MeetingBook original = getTypicalMeetingBook();
        JsonMeetingBookStorage jsonMeetingBookStorage = new JsonMeetingBookStorage(filePath);

        // Save in new file and read back
        jsonMeetingBookStorage.saveMeetingBook(original, filePath);
        ReadOnlyMeetingBook readBack = jsonMeetingBookStorage.readMeetingBook(filePath).get();
        assertEquals(original, new MeetingBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMeeting(MEETING_CLEMENTI);
        original.removeMeeting(MEETING_BEDOK);
        jsonMeetingBookStorage.saveMeetingBook(original, filePath);
        readBack = jsonMeetingBookStorage.readMeetingBook(filePath).get();
        assertEquals(original, new MeetingBook(readBack));

        // Save and read without specifying file path
        original.addMeeting(MEETING_DOVER);
        jsonMeetingBookStorage.saveMeetingBook(original); // file path not specified
        readBack = jsonMeetingBookStorage.readMeetingBook().get(); // file path not specified
        assertEquals(original, new MeetingBook(readBack));

    }

    @Test
    public void saveMeetingBook_nullMeetingBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMeetingBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code meetingBook} at the specified {@code filePath}.
     */
    private void saveMeetingBook(ReadOnlyMeetingBook meetingBook, String filePath) {
        try {
            new JsonMeetingBookStorage(Paths.get(filePath))
                    .saveMeetingBook(meetingBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMeetingBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMeetingBook(new MeetingBook(), null));
    }
}
