package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "INVALID";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid statuses
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("invalid")); // non-valid status
        assertFalse(Status.isValidStatus("12345")); // numbers
        assertFalse(Status.isValidStatus("INTEREST")); // partial valid status

        // valid statuses
        assertTrue(Status.isValidStatus("INTERESTED"));
        assertTrue(Status.isValidStatus("APPLIED"));
        assertTrue(Status.isValidStatus("CLOSED"));
    }

    @Test
    public void equals() {
        Status status1 = new Status("INTERESTED");
        Status status2 = new Status("INTERESTED");
        Status status3 = new Status("APPLIED");

        // same object -> returns true
        assertTrue(status1.equals(status1));

        // same values -> returns true
        assertTrue(status1.equals(status2));

        // different values -> returns false
        assertFalse(status1.equals(status3));

        // null -> returns false
        assertFalse(status1.equals(null));

        // different type -> returns false
        assertFalse(status1.equals(5));
    }
}
