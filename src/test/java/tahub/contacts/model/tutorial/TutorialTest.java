package tahub.contacts.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;

public class TutorialTest {

    @Test
    public void getTutorialId() {
        Course course = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        Tutorial tutorial = new Tutorial("T1", course);
        assertEquals("T1", tutorial.getTutorialId());
    }

    @Test
    public void getCourse() {
        Course course = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        Tutorial tutorial = new Tutorial("T2", course);
        assertEquals(course, tutorial.getCourse());
    }

    @Test
    public void equals() {
        Course course = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        Tutorial tutorial1 = new Tutorial("T3", course);
        Tutorial tutorial2 = new Tutorial("T3", course);
        Tutorial tutorial3 = new Tutorial("T4", course);

        assertTrue(tutorial1.equals(tutorial2));
        assertFalse(tutorial1.equals(tutorial3));
    }

    @Test
    public void hashCodeTest() {
        Course course = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        Tutorial tutorial1 = new Tutorial("T5", course);
        Tutorial tutorial2 = new Tutorial("T5", course);
        Tutorial tutorial3 = new Tutorial("T6", course);

        assertEquals(tutorial1.hashCode(), tutorial2.hashCode());
        assertNotEquals(tutorial1.hashCode(), tutorial3.hashCode());
    }
}
