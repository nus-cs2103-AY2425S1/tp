package tahub.contacts.model.course;

import tahub.contacts.model.course.CourseName;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.testutil.Assert.assertThrows;

public class CourseNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CourseName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCourseName = "";
        assertThrows(IllegalArgumentException.class, () -> new CourseName(invalidCourseName));
    }

    @Test
    public void isValidCourseName() {
        // null course name
        assertThrows(NullPointerException.class, () -> CourseName.isValidCourseName(null));

        // invalid course names
        assertFalse(CourseName.isValidCourseName("")); // empty string
        assertFalse(CourseName.isValidCourseName(" ")); // spaces only
        assertFalse(CourseName.isValidCourseName("^")); // only non-alphanumeric characters
        assertFalse(CourseName.isValidCourseName("course*")); // contains non-alphanumeric characters

        // valid course names
        assertTrue(CourseName.isValidCourseName("course")); // alphabets only
        assertTrue(CourseName.isValidCourseName("12345")); // numbers only
        assertTrue(CourseName.isValidCourseName("course123")); // alphanumeric characters
        assertTrue(CourseName.isValidCourseName("Course123")); // with capital letters
    }

    @Test
    public void equals() {
        CourseName courseName = new CourseName("Programming");

        // same values -> returns true
        assertTrue(courseName.equals(new CourseName("Programming")));

        // same object -> returns true
        assertTrue(courseName.equals(courseName));

        // null -> returns false
        assertFalse(courseName.equals(null));

        // different types -> returns false
        assertFalse(courseName.equals(5.0f));

        // different values -> returns false
        assertFalse(courseName.equals(new CourseName("Data Structures")));
    }
}
