package tahub.contacts.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Course(null, null));
    }

    @Test
    public void constructor_invalidCourseCodeAndCourseName_throwsIllegalArgumentException() {
        String invalidCourseCode = "101";
        String invalidCourseName = "";
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourseCode, invalidCourseName));
    }

    @Test
    public void constructor_invalidCourseCode_throwsIllegalArgumentException() {
        String invalidCourseCode = "101";
        String validCourseName = "Programming Methodology";
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourseCode, validCourseName));
    }

    @Test
    public void constructor_invalidCourseName_throwsIllegalArgumentException() {
        String validCourseCode = "CS1101S";
        String invalidCourseName = "";
        assertThrows(IllegalArgumentException.class, () -> new Course(validCourseCode, invalidCourseName));
    }

    @Test
    public void isValidCourseCode() {
        // null course code
        assertThrows(NullPointerException.class, () -> Course.isValidCourseCode(null));

        // invalid course codes
        assertFalse(Course.isValidCourseCode("")); // empty string
        assertFalse(Course.isValidCourseCode(" ")); // spaces only
        assertFalse(Course.isValidCourseCode("^")); // only non-alphanumeric characters
        assertFalse(Course.isValidCourseCode("course*")); // contains non-alphanumeric characters
        assertFalse(Course.isValidCourseCode("cS1010")); // contains lowercase character at the start
        assertFalse(Course.isValidCourseCode("Cs1010")); // contains lowercase character at the start
        assertFalse(Course.isValidCourseCode("CS")); // contains no digits after uppercase alphabetical characters
        assertFalse(Course.isValidCourseCode("CS110")); // not enough digits
        assertFalse(Course.isValidCourseCode("CS1101SS")); // contains extra alphabetical characters at the end

        // valid course codes
        assertTrue(Course.isValidCourseCode("CS1101S"));
        assertTrue(Course.isValidCourseCode("BT1152"));
        assertTrue(Course.isValidCourseCode("ST2334"));
        assertTrue(Course.isValidCourseCode("PL9087"));
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
    public void isConflictCourse() {
        Course course1 = new Course("CS1011", "Programming");
        Course course2 = new Course("CS1011", "Data Structures");
        Course course3 = new Course("CS1021", "Programming");
        Course course4 = new Course("CS1021", "Data Structures");

        // same course code
        assertTrue(course1.isConflictCourse(course2));

        // same course name
        assertFalse(course1.isConflictCourse(course3));

        // different course code and name
        assertFalse(course1.isConflictCourse(course4));
    }

    @Test
    public void equals() {
        Course course1 = new Course("CS1011", "Programming");
        Course course2 = new Course("CS1011", "Programming");
        Course course3 = new Course("CS1021", "Programming");

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
        Course course1 = new Course("CS1011", "ProgrammingMethod");
        Course course2 = new Course("CS1011", "ProgrammingMethod");

        assertEquals(course1.hashCode(), course2.hashCode());
    }

    @Test
    public void toString_validCourse_correctFormat() {
        Course course = new Course("CS1011", "Programming");
        assertEquals("[CS1011: Programming]", course.toString());
    }
}
