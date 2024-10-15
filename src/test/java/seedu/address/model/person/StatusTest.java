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
        String invalidStatus = "Recruited";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null email
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // blank status
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // invalid statuses
        assertFalse(Status.isValidStatus("Recruited")); // not in valid list
        assertFalse(Status.isValidStatus("Applied ")); // trailing space
        assertFalse(Status.isValidStatus(" Applied")); // leading space

        // valid statuses
        assertTrue(Status.isValidStatus("Applied"));
        assertTrue(Status.isValidStatus("Screening"));
        assertTrue(Status.isValidStatus("Interview Scheduled"));
        assertTrue(Status.isValidStatus("Interviewed"));
        assertTrue(Status.isValidStatus("Offer"));
        assertTrue(Status.isValidStatus("Onboarding"));
        assertTrue(Status.isValidStatus("Hired"));
        assertTrue(Status.isValidStatus("Rejected"));
    }

    @Test
    public void equals() {
        Status status = new Status("Applied");

        // same values -> returns true
        assertTrue(status.equals(new Status("Applied")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // null -> returns false
        assertFalse(status.equals(null));

        // different types -> returns false
        assertFalse(status.equals(5.0f));

        // different values -> returns false
        assertFalse(status.equals(new Status("Rejected")));
    }
}
