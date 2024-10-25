package tutorease.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;

public class JsonLessonScheduleStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonLessonScheduleStorageTest");
    private static final ReadOnlyTutorEase readOnlyTutorEase = getTypicalTutorEase();

    @TempDir
    public Path testFolder;
    private final Person validPerson = readOnlyTutorEase.getPersonList().get(0);

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readLessonSchedule_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readLessonSchedule(null));
    }

    @Test
    public void readLessonSchedule_missingFile_emptyResult() throws Exception {
        assertFalse(readLessonSchedule("NonExistentFile.json").isPresent());
    }

    @Test
    public void readLessonSchedule_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readLessonSchedule("notJsonFormatLessonSchedule.json"));
    }

    @Test
    public void readLessonSchedule_invalidLessonLessonSchedule_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readLessonSchedule("invalidLessonSchedule.json"));
    }

    @Test
    public void getLessonScheduleFilePath() {
        JsonLessonScheduleStorage jsonLessonScheduleStorage = new JsonLessonScheduleStorage(Paths.get("data"));
        assertEquals(Paths.get("data"), jsonLessonScheduleStorage.getLessonScheduleFilePath());
    }

    @Test
    public void readLessonSchedule_duplicateLessons_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readLessonSchedule("duplicateLessonSchedule.json"));
    }

    @Test
    public void readAndSaveLessonSchedule_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempLessonSchedule.json");
        LessonSchedule original = new LessonSchedule();
        JsonLessonScheduleStorage jsonLessonScheduleStorage = new JsonLessonScheduleStorage(filePath);

        // Save in new file and read back
        jsonLessonScheduleStorage.saveLessonSchedule(original, filePath);
        LessonSchedule readBack = jsonLessonScheduleStorage.readLessonSchedule(readOnlyTutorEase).get();
        assertEquals(original, readBack);

        // Modify data, overwrite existing file, and read back
        Lesson lesson = new LessonBuilder().withName(validPerson).build();
        original.addLesson(lesson);
        original.deleteLesson(lesson);
        jsonLessonScheduleStorage.saveLessonSchedule(original, filePath);
        readBack = jsonLessonScheduleStorage.readLessonSchedule(readOnlyTutorEase).get();
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.addLesson(new LessonBuilder().withName(validPerson).build());
        jsonLessonScheduleStorage.saveLessonSchedule(original); // file path not specified
        readBack = jsonLessonScheduleStorage.readLessonSchedule(readOnlyTutorEase).get(); // file path not specified
        assertEquals(original, readBack);
    }

    private java.util.Optional<LessonSchedule> readLessonSchedule(String filePath) throws Exception {
        return new JsonLessonScheduleStorage(Paths.get(filePath))
                .readLessonSchedule(addToTestDataPathIfNotNull(filePath), readOnlyTutorEase);
    }
}
