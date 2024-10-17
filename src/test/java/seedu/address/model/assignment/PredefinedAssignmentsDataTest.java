package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


class PredefinedAssignmentsDataTest {

    @Test
    public void addTest() {
        PredefinedAssignment sample = new PredefinedAssignment("Ex01", 0f);
        PredefinedAssignmentsData data = new PredefinedAssignmentsData();
        ArrayList<PredefinedAssignment> expected = new ArrayList<>(List.of(sample));
        assertEquals(data.add(sample), expected);
    }
}
