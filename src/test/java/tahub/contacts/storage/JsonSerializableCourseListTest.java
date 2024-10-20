package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.UniqueCourseList;

public class JsonSerializableCourseListTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @TempDir
    public Path testFolder;

    @Test
    public void toModelType_typicalCourseList_success() throws Exception {
        UniqueCourseList courses = new UniqueCourseList();
        courses.add(new Course("CS1010", "Introduction to Computer Science"));
        courses.add(new Course("MA1521", "Calculus I"));

        JsonSerializableCourseList jsonSerialisedCourseList = new JsonSerializableCourseList(courses);
        UniqueCourseList courseList = jsonSerialisedCourseList.toModelType();

        assertEquals(courses, courseList);
    }

    @Test
    public void toModelType_invalidCourseList_throwsIllegalValueException() {
        String invalidJson = "{\"courses\": [{\"courseCode\": \"cs234\",\"courseName\": \"lol\"}]}";
        // Invalid course name

        assertThrows(IllegalValueException.class, () -> {
            JsonSerializableCourseList dataFromFile = MAPPER.readValue(invalidJson, JsonSerializableCourseList.class);
            dataFromFile.toModelType();
        });
    }

    @Test
    public void toModelType_emptyCourseList_success() throws Exception {
        String emptyJson = "{\"courses\":[]}";

        JsonSerializableCourseList dataFromFile = MAPPER.readValue(emptyJson, JsonSerializableCourseList.class);
        UniqueCourseList courseList = dataFromFile.toModelType();

        assertEquals(0, courseList.getCourseList().size());
    }

    @Test
    public void toModelType_nullCourseList_throwsJsonProcessingException() {
        String nullJson = "{\"courses\":null}";

        assertThrows(JsonProcessingException.class, () -> {
            MAPPER.readValue(nullJson, JsonSerializableCourseList.class);
        });
    }
}
