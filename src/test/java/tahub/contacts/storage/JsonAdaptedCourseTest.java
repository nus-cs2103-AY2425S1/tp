package tahub.contacts.storage;

import org.junit.jupiter.api.Test;
import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;

import static org.junit.jupiter.api.Assertions.*;

public class JsonAdaptedCourseTest {
    private static final String INVALID_COURSE_CODE = "101";
    private static final String INVALID_COURSE_NAME = "";

    private static final String VALID_COURSE_CODE = "CS1101S";
    private static final String VALID_COURSE_NAME = "Programming Methodology";

    @Test
    public void toModelType_validCourseDetails_returnsCourse() throws Exception {
        JsonAdaptedCourse course = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        assertEquals(new Course(VALID_COURSE_CODE, VALID_COURSE_NAME), course.toModelType());
    }

    @Test
    public void toModelType_invalidCourseCode_throwsIllegalValueException() {
        JsonAdaptedCourse course = new JsonAdaptedCourse(INVALID_COURSE_CODE, VALID_COURSE_NAME);
        assertThrows(IllegalValueException.class, course::toModelType);
    }

    @Test
    public void toModelType_nullCourseCode_throwsIllegalValueException() {
        JsonAdaptedCourse course = new JsonAdaptedCourse(null, VALID_COURSE_NAME);
        assertThrows(IllegalValueException.class, course::toModelType);
    }

    @Test
    public void toModelType_invalidCourseName_throwsIllegalValueException() {
        JsonAdaptedCourse course = new JsonAdaptedCourse(VALID_COURSE_CODE, INVALID_COURSE_NAME);
        assertThrows(IllegalValueException.class, course::toModelType);
    }

    @Test
    public void toModelType_nullCourseName_throwsIllegalValueException() {
        JsonAdaptedCourse course = new JsonAdaptedCourse(VALID_COURSE_CODE, null);
        assertThrows(IllegalValueException.class, course::toModelType);
    }

    @Test
    public void isConflictCourse_sameCourse_returnsTrue() throws Exception {
        JsonAdaptedCourse course = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        Course modelCourse = course.toModelType();
        assertTrue(modelCourse.isConflictCourse(new Course(VALID_COURSE_CODE, VALID_COURSE_NAME)));
    }

    @Test
    public void isConflictCourse_differentCourseCode_returnsFalse() throws Exception {
        JsonAdaptedCourse course = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        Course modelCourse = course.toModelType();
        assertFalse(modelCourse.isConflictCourse(new Course("CS2101", VALID_COURSE_NAME)));
    }

    @Test
    public void isConflictCourse_differentCourseName_returnsFalse() throws Exception {
        JsonAdaptedCourse course = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        Course modelCourse = course.toModelType();
        assertFalse(modelCourse.isConflictCourse(new Course(VALID_COURSE_CODE, "Data Structures")));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        JsonAdaptedCourse course = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        assertTrue(course.equals(course));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        JsonAdaptedCourse course1 = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        JsonAdaptedCourse course2 = new JsonAdaptedCourse("CS2101", "Effective Communication");
        assertFalse(course1.equals(course2));
    }

    @Test
    public void hashCode_sameObject_returnsSameHashCode() {
        JsonAdaptedCourse course = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        assertEquals(course.hashCode(), course.hashCode());
    }

    @Test
    public void hashCode_differentObject_returnsDifferentHashCode() {
        JsonAdaptedCourse course1 = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        JsonAdaptedCourse course2 = new JsonAdaptedCourse("CS2101", "Effective Communication");
        assertNotEquals(course1.hashCode(), course2.hashCode());
    }
}