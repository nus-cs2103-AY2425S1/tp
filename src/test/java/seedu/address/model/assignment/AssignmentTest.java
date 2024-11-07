package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE;

import org.junit.jupiter.api.Test;

public class AssignmentTest {
    private final Assignment assignment = new Assignment(VALID_ASSIGNMENT_ONE, VALID_SCORE);

    @Test
    public void toStringMethod() {
        String expected = "Assignment: " + VALID_ASSIGNMENT_ONE + " " + String.format("%.2f", VALID_SCORE);
        assertEquals(expected, assignment.toString());
    }
}
