package seedu.address.model.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.task.Status.isValidStatus;

public class StatusTest {
    private static final Status PENDING = Status.PENDING;
    private static final Status COMPLETED = Status.COMPLETED;

    @Test
    public void isValidStatus_returnsStatus() {
        String expectedPendingString = "Pending";
        String expectedCompletedString = "Completed";
        String unexpectedIncorrectString = "Incorrect";
        assertEquals(isValidStatus(expectedPendingString), true);
        assertEquals(isValidStatus(expectedCompletedString), true);
        assertEquals(isValidStatus(unexpectedIncorrectString), false);
    }
}
