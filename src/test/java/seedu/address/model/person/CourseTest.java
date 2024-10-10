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

        // invalid course
        assertFalse(Course.isValidCourse("")); // empty string
        assertFalse(Course.isValidCourse(" ")); // spaces only
        assertFalse(Course.isValidCourse("^")); // only non-alphabetic character
        assertFalse(Course.isValidCourse("1")); // only numeric character
        assertFalse(Course.isValidCourse("Computer Science*")); // contains non-alphabetic characters
        assertFalse(Course.isValidCourse("Computer Science1")); // contains numeric characters
        assertFalse(Course.isValidCourse(" Computer Science")); // begins with whitespace

        // valid course
        assertTrue(Course.isValidCourse("Computer Science")); // alphabets only
        assertTrue(Course.isValidCourse("Geography")); // one word only
    }

    @Test
    public void equals() {
        Course course = new Course("Valid Course");

        // same values -> returns true
        assertTrue(course.equals(new Course("Valid Course")));

        // same object -> returns true
        assertTrue(course.equals(course));

        // null -> returns false
        assertFalse(course.equals(null));

        // different types -> returns false
        assertFalse(course.equals(5.0f));

        // different values -> returns false
        assertFalse(course.equals(new Course("Other Valid Course")));
    }
}
