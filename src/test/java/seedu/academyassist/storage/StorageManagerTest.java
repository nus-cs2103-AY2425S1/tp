package seedu.academyassist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.academyassist.commons.core.GuiSettings;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAcademyAssistStorage academyAssistStorage = new JsonAcademyAssistStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(academyAssistStorage, userPrefsStorage);
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
    public void academyAssistReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAcademyAssistStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAcademyAssistStorageTest} class.
         */
        AcademyAssist original = getTypicalAcademyAssist();
        storageManager.saveAcademyAssist(original);
        ReadOnlyAcademyAssist retrieved = storageManager.readAcademyAssist().get();
        assertEquals(original, new AcademyAssist(retrieved));
    }

    @Test
    public void getAcademyAssistFilePath() {
        assertNotNull(storageManager.getAcademyAssistFilePath());
    }

}
