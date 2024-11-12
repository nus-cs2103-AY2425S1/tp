package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


class PredefinedAssignmentsDataTest {
    private final PredefinedAssignment sample =
            new PredefinedAssignment("Ex01", 0f);

    @Test
    public void addTest() {
        PredefinedAssignmentsData data = new PredefinedAssignmentsData();
        ArrayList<PredefinedAssignment> expected = new ArrayList<>(List.of(sample));
        assertEquals(data.add(sample), expected);
    }

    @Test
    public void test_to_string() {
        PredefinedAssignmentsData data = new PredefinedAssignmentsData();
        assertEquals(data.toString(),
                "No assignments specified in database.");

        data.add(sample);
        assertEquals(data.toString(), sample + "\n");
    }

    @Test
    public void test_equals() {
        PredefinedAssignmentsData data = new PredefinedAssignmentsData();
        data.add(sample);
        PredefinedAssignmentsData dataT = new PredefinedAssignmentsData();
        dataT.add(sample);
        assertEquals(data, dataT);
        assertNotEquals(data, new Object());
    }
}
