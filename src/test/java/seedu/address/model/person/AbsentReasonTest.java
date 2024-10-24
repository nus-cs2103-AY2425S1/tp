package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        String invalidReason = "+";
        assertThrows(IllegalArgumentException.class, () -> new AbsentReason(invalidReason));
    }

    @Test
    public void isValidReason() {
        // invalid reasons
        assertFalse(AbsentReason.isValidAbsentReason(null)); // null input
        assertFalse(AbsentReason.isValidAbsentReason("123")); // numeric characters not allowed
        assertFalse(AbsentReason.isValidAbsentReason("!@#")); // special characters not allowed


        // valid reasons
        assertTrue(AbsentReason.isValidAbsentReason("")); // empty string allowed
        assertTrue(AbsentReason.isValidAbsentReason(" ")); // single space not allowed
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

    @Test
    public void hashCode_sameReason_sameHashCode() {
        AbsentReason reason1 = new AbsentReason("Sick");
        AbsentReason reason2 = new AbsentReason("Sick");

        assertEquals(reason1.hashCode(), reason2.hashCode());
    }

    @Test
    public void hashCode_differentReasons_differentHashCode() {
        AbsentReason reason1 = new AbsentReason("Sick");
        AbsentReason reason2 = new AbsentReason("Holiday");

        assertNotEquals(reason1.hashCode(), reason2.hashCode());
    }

    @Test
    public void hashCode_sameObject_sameHashCode() {
        AbsentReason reason = new AbsentReason("Sick");

        assertEquals(reason.hashCode(), reason.hashCode());
    }
}
