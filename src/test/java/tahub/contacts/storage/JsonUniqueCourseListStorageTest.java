package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tahub.contacts.commons.exceptions.DataLoadingException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.UniqueCourseList;

public class JsonUniqueCourseListStorageTest {

    @TempDir
    public Path testFolder;

    private JsonUniqueCourseListStorage storage;

    @BeforeEach
    public void setUp() {
        storage = new JsonUniqueCourseListStorage(testFolder);
    }

    @Test
    public void saveCourseList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> storage.saveCourseList(new UniqueCourseList(), null));
    }

    @Test
    public void saveCourseList_nullCourseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> storage.saveCourseList(null, testFolder.resolve("dummy.json")));
    }

    @Test
    public void saveCourseList_validData_success() throws IOException {
        UniqueCourseList courses = new UniqueCourseList();
        courses.add(new Course("CS1010", "Introduction to CS"));
        Path filePath = testFolder.resolve("courses.json");

        storage.saveCourseList(courses, filePath);

        assertTrue(filePath.toFile().exists());
    }

    @Test
    public void readCourseList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> storage.readCourseList(null));
    }

    @Test
    public void readCourseList_missingFile_returnsEmptyList() throws DataLoadingException {
        Path filePath = testFolder.resolve("nonexistent.json");

        Optional<UniqueCourseList> courses = storage.readCourseList(filePath);

        assertTrue(courses.isEmpty());
    }

    @Test
    public void readCourseList_validData_success() throws IOException, DataLoadingException {
        UniqueCourseList courses = new UniqueCourseList();
        courses.add(new Course("CS1010", "Introduction to CS"));
        Path filePath = testFolder.resolve("courses.json");
        storage.saveCourseList(courses, filePath);

        Optional<UniqueCourseList> readCoursesOptional = storage.readCourseList(filePath);
        assertTrue(readCoursesOptional.isPresent());
        UniqueCourseList readCourses = readCoursesOptional.get();

        assertEquals(1, readCourses.asUnmodifiableObservableList().size());
        assertEquals("CS1010", readCourses.asUnmodifiableObservableList().get(0).courseCode);
        assertEquals("Introduction to CS", readCourses.asUnmodifiableObservableList().get(0).courseName);
    }
}
