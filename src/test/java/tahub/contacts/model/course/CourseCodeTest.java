package tahub.contacts.model.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CourseCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CourseCode(null));
    }

    @Test
    public void constructor_invalidCourseCode_throwsIllegalArgumentException() {
        String invalidCourseCode = "101";
        assertThrows(IllegalArgumentException.class, () -> new CourseCode(invalidCourseCode));
    }

    @Test
    public void isValidCourseCode() {
        // null course code
        assertThrows(NullPointerException.class, () -> CourseCode.isValidCourseCode(null));

        // invalid course codes
        assertFalse(CourseCode.isValidCourseCode("")); // empty string
        assertFalse(CourseCode.isValidCourseCode(" ")); // spaces only
        assertFalse(CourseCode.isValidCourseCode("^")); // only non-alphanumeric characters
        assertFalse(CourseCode.isValidCourseCode("course*")); // contains non-alphanumeric characters
        assertFalse(CourseCode.isValidCourseCode("cS1010")); // contains lowercase character at the start
        assertFalse(CourseCode.isValidCourseCode("Cs1010")); // contains lowercase character at the start
        assertFalse(CourseCode.isValidCourseCode("CS")); // contains no digits after uppercase alphabetical characters
        assertFalse(CourseCode.isValidCourseCode("CS110")); // not enough digits
        assertFalse(CourseCode.isValidCourseCode("CS1101SS")); // contains extra alphabetical characters at the end

        // valid course codes
        assertTrue(CourseCode.isValidCourseCode("CS1101S"));
        assertTrue(CourseCode.isValidCourseCode("BT1152"));
        assertTrue(CourseCode.isValidCourseCode("ST2334"));
        assertTrue(CourseCode.isValidCourseCode("PL9087"));
    }

    @Test
    public void equals() {
        CourseCode courseCode = new CourseCode("CS1011");

        // same values -> returns true
        assertTrue(courseCode.equals(new CourseCode("CS1011")));

        // same object -> returns true
        assertTrue(courseCode.equals(courseCode));

        // null -> returns false
        assertFalse(courseCode.equals(null));

        // different types -> returns false
        assertFalse(courseCode.equals(5.0f));

        // different values -> returns false
        assertFalse(courseCode.equals(new CourseCode("CS1021")));
    }
}
