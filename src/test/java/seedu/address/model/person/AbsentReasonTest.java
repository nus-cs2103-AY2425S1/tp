package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AbsentReasonTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AbsentReason(null));
    }

    @Test
    public void constructor_invalidReason_throwsIllegalArgumentException() {
        String invalidReason = ""; // Empty reason is invalid
        assertThrows(IllegalArgumentException.class, () -> new AbsentReason(invalidReason));
    }

    @Test
    public void isValidReason() {
        // null reason
        assertThrows(NullPointerException.class, () -> AbsentReason.isValidAbsentReason(null));

        // invalid reasons
        assertFalse(AbsentReason.isValidAbsentReason("123")); // numeric characters not allowed
        assertFalse(AbsentReason.isValidAbsentReason("!@#")); // special characters not allowed

        // valid reasons
        assertTrue(AbsentReason.isValidAbsentReason("")); // empty string allowed
        assertTrue(AbsentReason.isValidAbsentReason("Sick")); // valid reason
        assertTrue(AbsentReason.isValidAbsentReason("Holiday")); // valid reason
        assertTrue(AbsentReason.isValidAbsentReason("Just a test")); // valid reason with spaces
    }

    @Test
    public void equals() {
        AbsentReason absentReason = new AbsentReason("Sick");

        // same values -> returns true
        assertTrue(absentReason.equals(new AbsentReason("Sick")));

        // same object -> returns true
        assertTrue(absentReason.equals(absentReason));

        // null -> returns false
        assertFalse(absentReason.equals(null));

        // different types -> returns false
        assertFalse(absentReason.equals(5.0f));

        // different values -> returns false
        assertFalse(absentReason.equals(new AbsentReason("Holiday")));
    }
}
