package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Course(null));
    }

    @Test
    public void constructor_invalidCourse_throwsIllegalArgumentException() {
        String invalidCourse = "";
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourse));
    }

    @Test
    public void isValidCourse() {
        // null course
        assertThrows(NullPointerException.class, () -> Course.isValidCourse(null));

        // invalid courses
        assertFalse(Course.isValidCourse("")); // empty string
        assertFalse(Course.isValidCourse(" ")); // spaces only

        // valid courses
        assertTrue(Course.isValidCourse("CS2103/T"));
        assertTrue(Course.isValidCourse("-")); // one character
        assertTrue(Course.isValidCourse("CS2103/T LG27 - Software Engineering")); // long courses
    }

    @Test
    public void equals() {
        Course course = new Course("CS2103/T LG27 - Software Engineering");

        // same values -> returns true
        assertTrue(course.equals(new Course("CS2103/T LG27 - Software Engineering")));

        // same object -> returns true
        assertTrue(course.equals(course));

        // null -> returns false
        assertFalse(course.equals(null));

        // different types -> returns false
        assertFalse(course.equals(5.0f));

        // different values -> returns false
        assertFalse(course.equals(new Course("CS2103/T")));
    }
}
