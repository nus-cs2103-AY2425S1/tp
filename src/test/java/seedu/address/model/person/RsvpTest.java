package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RsvpTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rsvp(null));
    }

    @Test
    public void constructor_invalidRsvp_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Rsvp("invalidRsvp"));
    }

    @Test
    public void isValidRsvp() {
        // null Rsvp
        assertThrows(NullPointerException.class, () -> Rsvp.isValidRsvp(null));

        // invalid Rsvp
        assertFalse(Rsvp.isValidRsvp("")); // empty string
        assertFalse(Rsvp.isValidRsvp(" ")); // spaces only
        assertFalse(Rsvp.isValidRsvp("^")); // only non-alphanumeric characters
        assertFalse(Rsvp.isValidRsvp("peter*")); // contains non-alphanumeric characters

        // valid Rsvp
        assertTrue(Rsvp.isValidRsvp("P"));
        assertTrue(Rsvp.isValidRsvp("A"));
        assertTrue(Rsvp.isValidRsvp("D"));
        assertTrue(Rsvp.isValidRsvp("p"));
        assertTrue(Rsvp.isValidRsvp("a"));
        assertTrue(Rsvp.isValidRsvp("d"));
    }

    @Test
    public void equals() {
        Rsvp rsvp = new Rsvp("A");

        // same values -> returns true
        assertTrue(rsvp.equals(new Rsvp("A")));

        // same object -> returns true
        assertTrue(rsvp.equals(rsvp));

        // null -> returns false
        assertFalse(rsvp.equals(null));

        // different types -> returns false
        assertFalse(rsvp.equals(5.0f));

        // different values -> returns false
        assertFalse(rsvp.equals(new Rsvp("P")));
    }

}
