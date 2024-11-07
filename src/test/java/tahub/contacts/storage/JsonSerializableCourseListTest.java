package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;

public class JsonSerializableCourseListTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void toModelType_typicalCourseList_success() throws Exception {
        UniqueCourseList courses = new UniqueCourseList();
        courses.add(new Course(new CourseCode("CS1010"), new CourseName("Introduction to Computer Science")));
        courses.add(new Course(new CourseCode("MA1521"), new CourseName("Calculus I")));

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
