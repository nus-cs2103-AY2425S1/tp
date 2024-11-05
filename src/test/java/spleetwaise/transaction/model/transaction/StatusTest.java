package spleetwaise.transaction.model.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String testString = null;

        assertThrows(NullPointerException.class, () -> new Status(testString));
    }

    @Test
    public void constructor_empty_throwsIllegalArgumentException() {
        String testString = "";

        assertThrows(IllegalArgumentException.class, () -> new Status(testString));
    }

    @Test
    public void constructor_validInput_success() {
        assertDoesNotThrow(() -> new Status(Status.DONE_STATUS));
        assertDoesNotThrow(() -> new Status(Status.NOT_DONE_STATUS));
        assertDoesNotThrow(() -> new Status(true));
        assertDoesNotThrow(() -> new Status(false));
    }

    @Test
    public void isDone_returnCorrectOutput() {
        assertTrue(new Status(Status.DONE_STATUS).isDone());
        assertFalse(new Status(Status.NOT_DONE_STATUS).isDone());
    }

    @Test
    public void isValidStatus_validInput_returnsTrue() {
        assertTrue(Status.isValidStatus(Status.DONE_STATUS));
        assertTrue(Status.isValidStatus(Status.NOT_DONE_STATUS));
    }

    @Test
    public void isValidStatus_invalidInput_returnsFalse() {
        assertFalse(Status.isValidStatus(""));
        assertFalse(Status.isValidStatus(" "));
        assertFalse(Status.isValidStatus("done"));
        assertFalse(Status.isValidStatus("not done"));
        assertFalse(Status.isValidStatus("not done "));
        assertFalse(Status.isValidStatus(" done"));
        assertFalse(Status.isValidStatus("not done "));
        assertFalse(Status.isValidStatus("invalid"));
    }

    @Test
    public void equals_validArgument_returnsTrue() {
        Status status = new Status(Status.DONE_STATUS);
        Status status2 = new Status(Status.DONE_STATUS);
        Status status3 = new Status(true);
        assertEquals(status, status);
        assertEquals(status, status2);
        assertEquals(status, status3);

        status = new Status(Status.NOT_DONE_STATUS);
        status2 = new Status(Status.NOT_DONE_STATUS);
        status3 = new Status(false);
        assertEquals(status, status);
        assertEquals(status, status2);
        assertEquals(status, status3);
    }

    @Test
    public void equals_invalidArgument_returnsFalse() {
        Status status = new Status(Status.DONE_STATUS);
        Status status2 = new Status(true);
        Status status3 = new Status(Status.NOT_DONE_STATUS);
        Status status4 = new Status(false);

        assertNotEquals(status, null);
        assertNotEquals(status, new Object());
        assertNotEquals(status, status3);
        assertNotEquals(status, status4);
        assertNotEquals(status2, status3);
        assertNotEquals(status2, status4);
    }

    @Test
    public void toString_returnsCorrectOutput() {
        Status status = new Status(Status.DONE_STATUS);
        assertEquals(Status.DONE_STATUS, status.toString());
        status = new Status(Status.NOT_DONE_STATUS);
        assertEquals(Status.NOT_DONE_STATUS, status.toString());
    }
}
