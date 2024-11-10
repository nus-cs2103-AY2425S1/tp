package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PredefinedAssignmentTest {
    @Test
    public void test_equals() {
        PredefinedAssignment one = new PredefinedAssignment("g", 0f);
        PredefinedAssignment two = new PredefinedAssignment("g", 0f);
        assertEquals(one, two);
        assertNotEquals(one, new Object());
    }



    @Test
    public void test_to_string() {
        PredefinedAssignment one = new PredefinedAssignment("g", 0f);
        assertEquals(one.toString(),
                "Assignment: " + "g" + ", " + "MaxScore: " + 0f);
    }

}
