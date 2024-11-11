package seedu.eventtory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalEventTory;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eventtory.commons.core.GuiSettings;
import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonEventToryStorage eventToryStorage = new JsonEventToryStorage(getTempFilePath("et"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(eventToryStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void eventToryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonEventToryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonEventToryStorageTest} class.
         */
        EventTory original = getTypicalEventTory();
        storageManager.saveEventTory(original);
        ReadOnlyEventTory retrieved = storageManager.readEventTory().get();
        assertEquals(original, new EventTory(retrieved));
    }

    @Test
    public void getEventToryFilePath() {
        assertNotNull(storageManager.getEventToryFilePath());
    }

}
