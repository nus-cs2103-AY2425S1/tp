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
    public void constructor_invalidStatusName_throwsIllegalArgumentException() {
        String invalidStatusName = "";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatusName));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus("^")); // only non-alphanumeric characters
        assertFalse(Status.isValidStatus("peter*")); // contains non-alphanumeric characters

        // valid status
        assertTrue(Status.isValidStatus("recovering")); // alphabets only
        assertTrue(Status.isValidStatus("discharged in 1 week")); // alphanumeric characters
        assertTrue(Status.isValidStatus("Critical")); // with capital letters
    }

    @Test
    public void equals() {
        Status status = new Status("Valid Status");

        // same values -> returns true
        assertTrue(status.equals(new Status("Valid Status")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // null -> returns false
        assertFalse(status.equals(null));

        // different types -> returns false
        assertFalse(status.equals(5.0f));

        // different values -> returns false
        assertFalse(status.equals(new Status("Other Valid Status")));
    }

}
