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
    public void isConflictCourse() {
        Course course1 = new Course(new CourseCode("CS1011"), new CourseName("Programming"));
        Course course2 = new Course(new CourseCode("CS1011"), new CourseName("Data Structures"));
        Course course3 = new Course(new CourseCode("CS1021"), new CourseName("Programming"));
        Course course4 = new Course(new CourseCode("CS1021"), new CourseName("Data Structures"));

        // same course code
        assertTrue(course1.isConflictCourse(course2));

        // same course name
        assertFalse(course1.isConflictCourse(course3));

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
