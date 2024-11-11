package seedu.eventtory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.eventtory.testutil.Assert.assertThrows;
import static seedu.eventtory.testutil.TypicalVendors.ALICE;
import static seedu.eventtory.testutil.TypicalVendors.HOON;
import static seedu.eventtory.testutil.TypicalVendors.IDA;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalEventTory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eventtory.commons.exceptions.DataLoadingException;
import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.ReadOnlyEventTory;

public class JsonEventToryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEventToryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEventTory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEventTory(null));
    }

    private java.util.Optional<ReadOnlyEventTory> readEventTory(String filePath) throws Exception {
        return new JsonEventToryStorage(Paths.get(filePath)).readEventTory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventTory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readEventTory("notJsonFormatEventTory.json"));
    }

    @Test
    public void readEventTory_invalidVendorEventTory_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEventTory("invalidVendorEventTory.json"));
    }

    @Test
    public void readEventTory_invalidAndValidVendorEventTory_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEventTory("invalidAndValidVendorEventTory.json"));
    }

    @Test
    public void readAndSaveEventTory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempEventTory.json");
        EventTory original = getTypicalEventTory();
        JsonEventToryStorage jsonEventToryStorage = new JsonEventToryStorage(filePath);

        // Save in new file and read back
        jsonEventToryStorage.saveEventTory(original, filePath);
        ReadOnlyEventTory readBack = jsonEventToryStorage.readEventTory(filePath).get();
        assertEquals(original, new EventTory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addVendor(HOON);
        original.removeVendor(ALICE);
        jsonEventToryStorage.saveEventTory(original, filePath);
        readBack = jsonEventToryStorage.readEventTory(filePath).get();
        assertEquals(original, new EventTory(readBack));

        // Save and read without specifying file path
        original.addVendor(IDA);
        jsonEventToryStorage.saveEventTory(original); // file path not specified
        readBack = jsonEventToryStorage.readEventTory().get(); // file path not specified
        assertEquals(original, new EventTory(readBack));

    }

    @Test
    public void saveEventTory_nullEventTory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEventTory(null, "SomeFile.json"));
    }

    /**
     * Saves {@code eventTory} at the specified {@code filePath}.
     */
    private void saveEventTory(ReadOnlyEventTory eventTory, String filePath) {
        try {
            new JsonEventToryStorage(Paths.get(filePath))
                    .saveEventTory(eventTory, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEventTory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEventTory(new EventTory(), null));
    }
}
