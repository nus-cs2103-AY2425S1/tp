package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LastSeenTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastSeen(null));
    }

    @Test
    public void constructor_invalidLastSeen_throwsIllegalArgumentException() {
        String invalidFormat = "2024-01-01";
        assertThrows(IllegalArgumentException.class, () -> new LastSeen(invalidFormat));
    }

    @Test
    public void isValidLastSeen() {
        // null as date
        assertThrows(NullPointerException.class, () -> LastSeen.isValidDate(null));
        // invalid dates
        assertFalse(LastSeen.isValidDate("")); // empty string
        assertFalse(LastSeen.isValidDate(" ")); // spaces only
        assertFalse(LastSeen.isValidDate("30-02-2024"));
        // valid dates
        assertTrue(LastSeen.isValidDate("01-02-2024"));
    }

    @Test
    public void equals() {
        LastSeen lastSeen = new LastSeen("01-02-2024");
        // same values -> returns true
        assertTrue(lastSeen.equals(new LastSeen("01-02-2024")));
        // same object -> returns true
        assertTrue(lastSeen.equals(lastSeen));
        // null -> returns false
        assertFalse(lastSeen.equals(null));
        // different types -> returns false
        assertFalse(lastSeen.equals(5.0f));
        // different values -> returns false
        assertFalse(lastSeen.equals(new LastSeen("29-03-2021")));
    }
}
