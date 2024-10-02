package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
        assertTrue(Rsvp.isValidRsvp("Accepted"));
        assertTrue(Rsvp.isValidRsvp("Pending"));
        assertTrue(Rsvp.isValidRsvp("Declined"));
        assertTrue(Rsvp.isValidRsvp("accePted"));
        assertTrue(Rsvp.isValidRsvp("pending"));
        assertTrue(Rsvp.isValidRsvp("declineD"));

    }

    @Test
    public void equals() {
        Rsvp rsvp = new Rsvp("Accepted");

        // same values -> returns true
        assertTrue(rsvp.equals(new Rsvp("Accepted")));

        // same object -> returns true
        assertTrue(rsvp.equals(rsvp));

        // null -> returns false
        assertFalse(rsvp.equals(null));

        // different types -> returns false
        assertFalse(rsvp.equals(5.0f));

        // different values -> returns false
        assertFalse(rsvp.equals(new Rsvp("Pending")));
    }

}
