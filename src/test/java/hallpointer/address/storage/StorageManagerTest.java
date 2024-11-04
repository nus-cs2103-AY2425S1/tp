package hallpointer.address.storage;

import static hallpointer.address.testutil.TypicalMembers.getTypicalHallPointer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hallpointer.address.commons.core.GuiSettings;
import hallpointer.address.model.HallPointer;
import hallpointer.address.model.ReadOnlyHallPointer;
import hallpointer.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonHallPointerStorage hallPointerStorage = new JsonHallPointerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(hallPointerStorage, userPrefsStorage);
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
    public void hallPointerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonHallPointerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonHallPointerStorageTest} class.
         */
        HallPointer original = getTypicalHallPointer();
        storageManager.saveHallPointer(original);
        ReadOnlyHallPointer retrieved = storageManager.readHallPointer().get();
        assertEquals(original, new HallPointer(retrieved));
    }

    @Test
    public void getHallPointerFilePath() {
        assertNotNull(storageManager.getHallPointerFilePath());
    }

}
