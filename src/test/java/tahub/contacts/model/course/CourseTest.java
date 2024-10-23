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
        CourseCode invalidCourseCode = new CourseCode("101");
        CourseName invalidCourseName = new CourseName("");
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourseCode, invalidCourseName));
    }

    @Test
    public void constructor_invalidCourseCode_throwsIllegalArgumentException() {
        CourseCode invalidCourseCode = new CourseCode("101");
        CourseName validCourseName = new CourseName("Programming Methodology");
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourseCode, validCourseName));
    }

    @Test
    public void constructor_invalidCourseName_throwsIllegalArgumentException() {
        CourseCode validCourseCode = new CourseCode("CS1101S");
        CourseName invalidCourseName = new CourseName("");
        assertThrows(IllegalArgumentException.class, () -> new Course(validCourseCode, invalidCourseName));
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
    public void isConflictCourse() {
        Course course1 = new Course(new CourseCode("CS1011"), new CourseName("Programming"));
        Course course2 = new Course(new CourseCode("CS1011"), new CourseName("Data Structures"));
        Course course3 = new Course(new CourseCode("CS1021"), new CourseName("Programming"));  
        Course course4 = new Course(new CourseCode("CS1021"), new CourseName("Data Structures"));

        // same course code
        assertTrue(course1.isConflictCourse(course2));

        // same course name
        assertTrue(course1.isConflictCourse(course3));

        // different course code and name
        assertFalse(course1.isConflictCourse(course4));
    }

    @Test
    public void equals() {
        Course course1 = new Course(new CourseCode("CS1011"), new CourseName("Programming"));
        Course course2 = new Course(new CourseCode("CS1011"), new CourseName("Programming"));
        Course course3 = new Course(new CourseCode("CS1021"), new CourseName("Programming"));

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
        Course course1 = new Course(new CourseCode("CS1011"), new CourseName("ProgrammingMethod"));
        Course course2 = new Course(new CourseCode("CS1011"), new CourseName("ProgrammingMethod"));

        assertEquals(course1.hashCode(), course2.hashCode());
    }

    @Test
    public void toString_validCourse_correctFormat() {
        Course course = new Course(new CourseCode("CS1011"), new CourseName("Programming"));
        assertEquals("[CS1011: Programming]", course.toString());
    }
}
