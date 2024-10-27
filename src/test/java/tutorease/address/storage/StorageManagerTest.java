package tutorease.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tutorease.address.commons.core.GuiSettings;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.ReadOnlyLessonSchedule;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.TutorEase;
import tutorease.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonTutorEaseStorage addressBookStorage = new JsonTutorEaseStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonLessonScheduleStorage lessonScheduleStorage = new JsonLessonScheduleStorage(getTempFilePath("ls"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage, lessonScheduleStorage);
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
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonTutorEaseStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonTutorEaseStorageTest} class.
         */
        TutorEase original = getTypicalTutorEase();
        storageManager.saveTutorEase(original);
        ReadOnlyTutorEase retrieved = storageManager.readTutorEase().get();
        assertEquals(original, new TutorEase(retrieved));
    }

    @Test
    public void getTutorEaseFilePath() {
        assertNotNull(storageManager.getTutorEaseFilePath());
    }

    @Test
    public void getLessonScheduleFilePath() {
        assertNotNull(storageManager.getLessonScheduleFilePath());
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void lessonScheduleReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonLessonScheduleStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonLessonScheduleStorageTest} class.
         */
        LessonSchedule original = new LessonSchedule();
        storageManager.saveLessonSchedule(original);
        ReadOnlyLessonSchedule retrieved = storageManager.readLessonSchedule(getTypicalTutorEase()).get();
        assertEquals(original, retrieved);
    }
}
