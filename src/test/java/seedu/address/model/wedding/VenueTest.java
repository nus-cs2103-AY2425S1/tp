package seedu.address.model.wedding;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_invalidVenue_throwsIllegalArgumentException() {
        String invalidVenue = "";
        assertThrows(IllegalArgumentException.class, () -> new Venue(invalidVenue));
    }

    @Test
    public void isValidVenue() {
        // null venue
        assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));

        // invalid venues
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // spaces only

        // valid venues
        assertTrue(Venue.isValidVenue("Orchard Hotel")); // standard venue
        assertTrue(Venue.isValidVenue("Pasir Ris Beach Club")); // with spaces
        assertTrue(Venue.isValidVenue("123 Venue Street")); // alphanumeric
    }

    @Test
    public void equals() {
        Venue venue = new Venue("Orchard Hotel");

        // same values -> returns true
        assertTrue(venue.equals(new Venue("Orchard Hotel")));

        // same object -> returns true
        assertTrue(venue.equals(venue));

        // null -> returns false
        assertFalse(venue.equals(null));

        // different types -> returns false
        assertFalse(venue.equals(5.0f));

        // different values -> returns false
        assertFalse(venue.equals(new Venue("Different Hotel")));
    }
}
