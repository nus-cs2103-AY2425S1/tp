package keycontacts.storage;

import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import keycontacts.commons.core.GuiSettings;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonStudentDirectoryStorage studentDirectoryStorage = new JsonStudentDirectoryStorage(getTempFilePath("sd"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(studentDirectoryStorage, userPrefsStorage);
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
    public void studentDirectoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonStudentDirectoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonStudentDirectoryStorageTest} class.
         */
        StudentDirectory original = getTypicalStudentDirectory();
        storageManager.saveStudentDirectory(original);
        ReadOnlyStudentDirectory retrieved = storageManager.readStudentDirectory().get();
        assertEquals(original, new StudentDirectory(retrieved));
    }

    @Test
    public void getStudentDirectoryFilePath() {
        assertNotNull(storageManager.getStudentDirectoryFilePath());
    }

}
