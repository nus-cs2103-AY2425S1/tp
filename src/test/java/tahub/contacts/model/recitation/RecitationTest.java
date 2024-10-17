package tahub.contacts.model.recitation;

import org.junit.jupiter.api.Test;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.courseclass.recitation.Recitation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test class contains unit tests for the Recitation class.
 */
public class RecitationTest {

    /**
     * Test case for the getRecitationId method in the Recitation class.
     * Aim: To ensure that the method correctly returns the Recitation ID when called.
     */
    @Test
    void getRecitationIdTest() {
        Course course = new Course("CS1101S", "Programming Methodology");
        Recitation recitation = new Recitation("R15", course);

        String expected = "R15";
        String actual = recitation.getRecitationId();

        assertEquals(expected, actual, "Expected and actual recitation IDs do not match.");
    }

    /**
     * Test case for the getCourse method in the Recitation class.
     * Aim: To make sure that the method correctly returns the Course object related to the Recitation when called.
     */
    @Test
    void getCourseTest() {
        Course expected = new Course("CS1101S", "Programming Methodology");
        Recitation recitation = new Recitation("R15", expected);

        Course actual = recitation.getCourse();

        assertEquals(expected, actual, "Expected and actual courses do not match.");
    }

    /**
     * Test case for the equals method in the Recitation class.
     * Aim: To affirm that the method accurately checks for equality between two Recitation objects.
     */
    @Test
    void equalsTest() {
        Course course = new Course("CS1101S", "Programming Methodology");
        Recitation recitation1 = new Recitation("R15", course);
        Recitation recitation2 = new Recitation("R15", course);

        assertTrue(recitation1.equals(recitation2), "Equal recitations were not identified as equal.");
    }

    /**
     * Test case for the equals method when the IDs differ for the Recitation objects.
     * Aim: To check if the equals method accurately identifies unequal Recitation objects.
     */
    @Test
    void equalsTestDifferentId() {
        Course course = new Course("CS1101S", "Programming Methodology");
        Recitation recitation1 = new Recitation("R15", course);
        Recitation recitation2 = new Recitation("R16", course);

        assertFalse(recitation1.equals(recitation2), "Unequal recitations were incorrectly identified as equal.");
    }
}
