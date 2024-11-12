package seedu.hiredfiredpro.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.hiredfiredpro.testutil.TypicalPersons.getTypicalHiredFiredPro;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.hiredfiredpro.commons.core.GuiSettings;
import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;
import seedu.hiredfiredpro.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonHiredFiredProStorage hiredFiredProStorage = new JsonHiredFiredProStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(hiredFiredProStorage, userPrefsStorage);
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
    public void hiredFiredProReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonHiredFiredProStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonHiredFiredProStorageTest} class.
         */
        HiredFiredPro original = getTypicalHiredFiredPro();
        storageManager.saveHiredFiredPro(original);
        ReadOnlyHiredFiredPro retrieved = storageManager.readHiredFiredPro().get();
        assertEquals(original, new HiredFiredPro(retrieved));
    }

    @Test
    public void getHiredFiredProFilePath() {
        assertNotNull(storageManager.getHiredFiredProFilePath());
    }

}
