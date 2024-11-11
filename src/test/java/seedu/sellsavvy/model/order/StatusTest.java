package seedu.sellsavvy.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.sellsavvy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.exceptions.IllegalValueException;

public class StatusTest {

    @Test
    public void fromString_validInput_success() {
        try {
            // Pending status
            assertEquals(Status.PENDING, Status.fromString("PENDING"));
            assertEquals(Status.PENDING, Status.fromString("pending"));

            // Completed status
            assertEquals(Status.COMPLETED, Status.fromString("COMPLETED"));
            assertEquals(Status.COMPLETED, Status.fromString("completed"));
        } catch (IllegalValueException e) {
            fail("IllegalValueException should not be thrown");
        }
    }

    @Test
    public void fromString_invalidInput_illegalValueExceptionThrown() {
        String expected = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expected, () -> Status.fromString(""));
        assertThrows(IllegalValueException.class, expected, () -> Status.fromString(" Pending"));
        assertThrows(IllegalValueException.class, expected, () -> Status.fromString("some random string"));
    }

    @Test
    public void toStringMethod() {
        assertEquals("Pending", Status.PENDING.toString());
        assertEquals("Completed", Status.COMPLETED.toString());
    }
}
