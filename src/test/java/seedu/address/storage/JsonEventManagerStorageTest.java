package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.ReadOnlyEventManager;
import seedu.address.testutil.TypicalEvents;

public class JsonEventManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEventManagerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEventManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEventManager(null));
    }

    private java.util.Optional<ReadOnlyEventManager> readEventManager(String filePath) throws Exception {
        return new JsonEventManagerStorage(Paths.get(filePath)).readEventManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readEventManager("notJsonFormatEventManager.json"));
    }

    @Test
    public void readEventManager_invalidEventManager_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEventManager("invalidEventEventManager.json"));
    }

    @Test
    public void readEventManager_invalidAndValidEventManager_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEventManager("invalidAndValidEventEventManager.json"));
    }

    @Test
    public void readAndSaveEventManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempEventManager.json");
        EventManager original = getTypicalEventManager();
        JsonEventManagerStorage jsonEventManagerStorage = new JsonEventManagerStorage(filePath);

        // Save in new file and read back
        jsonEventManagerStorage.saveEventManager(original, filePath);
        ReadOnlyEventManager readBack = jsonEventManagerStorage.readEventManager(filePath).get();
        assertEquals(original, new EventManager(readBack));

        // Modify data, overwrite existing file, and read back
        original.addEvent(TypicalEvents.TEST_EVENT);
        original.removeEvent(TypicalEvents.TECH_CONFERENCE);
        jsonEventManagerStorage.saveEventManager(original, filePath);
        readBack = jsonEventManagerStorage.readEventManager(filePath).get();
        assertEquals(original, new EventManager(readBack));

        // Save and read without specifying file path
        original.addEvent(TypicalEvents.TEST_EVENT_2);
        jsonEventManagerStorage.saveEventManager(original); // file path not specified
        readBack = jsonEventManagerStorage.readEventManager().get(); // file path not specified
        assertEquals(original, new EventManager(readBack));
    }

    @Test
    public void saveEventManager_nullEventManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEventManager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code eventManager} at the specified {@code filePath}.
     */
    private void saveEventManager(ReadOnlyEventManager eventManager, String filePath) {
        try {
            new JsonEventManagerStorage(Paths.get(filePath))
                    .saveEventManager(eventManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEventManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEventManager(new EventManager(), null));
    }
}
