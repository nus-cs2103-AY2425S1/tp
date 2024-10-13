package tahub.contacts.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Course(null));
    }

    @Test
    public void constructor_invalidCourseName_throwsIllegalArgumentException() {
        String invalidCourseName = "";
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourseName));
    }

    @Test
    public void isValidCourseName() {
        // null course name
        assertThrows(NullPointerException.class, () -> Course.isValidCourseName(null));

        // invalid course names
        assertFalse(Course.isValidCourseName("")); // empty string
        assertFalse(Course.isValidCourseName(" ")); // spaces only
        assertFalse(Course.isValidCourseName("^")); // only non-alphanumeric characters
        assertFalse(Course.isValidCourseName("course*")); // contains non-alphanumeric characters

        // valid course names
        assertTrue(Course.isValidCourseName("course")); // alphabets only
        assertTrue(Course.isValidCourseName("12345")); // numbers only
        assertTrue(Course.isValidCourseName("course123")); // alphanumeric characters
        assertTrue(Course.isValidCourseName("Course123")); // with capital letters
    }

    @Test
    public void equals() {
        Course course1 = new Course("CS101");
        Course course2 = new Course("CS101");
        Course course3 = new Course("CS102");

        // same object
        assertTrue(course1.equals(course1));

        // same values
        assertTrue(course1.equals(course2));

        // different values
        assertFalse(course1.equals(course3));

        // null
        assertFalse(course1.equals(null));

        // different type
        assertFalse(course1.equals("CS101"));
    }

    @Test
    public void hashCode_sameCourseName_sameHashCode() {
        Course course1 = new Course("CS101");
        Course course2 = new Course("CS101");

        assertEquals(course1.hashCode(), course2.hashCode());
    }

    @Test
    public void toString_validCourse_correctFormat() {
        Course course = new Course("CS101");
        assertEquals("[CS101]", course.toString());
    }
}
