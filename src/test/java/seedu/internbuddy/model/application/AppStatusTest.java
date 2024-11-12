package seedu.internbuddy.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppStatus(null));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> AppStatus.isValidStatus(null));

        // invalid statuses
        assertFalse(AppStatus.isValidStatus("")); // empty string
        assertFalse(AppStatus.isValidStatus(" ")); // spaces only
        assertFalse(AppStatus.isValidStatus("invalid")); // non-valid status
        assertFalse(AppStatus.isValidStatus("12345")); // numbers
        assertFalse(AppStatus.isValidStatus("INTERESTED")); // invalid status

        // valid statuses
        assertTrue(AppStatus.isValidStatus("INTERVIEWED"));
        assertTrue(AppStatus.isValidStatus("ACCEPTED"));
        assertTrue(AppStatus.isValidStatus("APPLIED"));
    }

    @Test
    public void equals() {
        AppStatus status1 = new AppStatus("INTERVIEWED");
        AppStatus status2 = new AppStatus("INTERVIEWED");
        AppStatus status3 = new AppStatus("APPLIED");

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
