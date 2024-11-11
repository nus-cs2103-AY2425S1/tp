package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "sick";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid phone numbers
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" "));
        assertTrue(Status.isValidStatus("low"));

        assertTrue(Status.isValidStatus("LOW"));
    }

    @Test
    public void equals() {
        Status status = new Status("NEW");

        // same values -> returns true
        assertTrue(status.equals(new Status("NEW")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // null -> returns false
        assertFalse(status.equals(null));

        // different values -> returns false
        assertFalse(status.equals(new Status("HIGH")));
    }
}
