package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void constructor_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLocation = " ";
        assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void isValidLocation() {
        // invalid locations
        assertFalse(Location.isValidLocation(" ")); // empty or blank location
        assertFalse(Location.isValidLocation("@ConferenceRoom!")); // special characters

        // valid locations
        assertTrue(Location.isValidLocation("Conference Room"));
        assertTrue(Location.isValidLocation("Room 101"));
        assertTrue(Location.isValidLocation("Main Hall"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Location location = new Location("Conference Room");
        assertTrue(location.equals(location));
    }

    @Test
    public void equals_differentObjectsSameValue_returnsTrue() {
        Location location1 = new Location("Conference Room");
        Location location2 = new Location("Conference Room");
        assertTrue(location1.equals(location2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Location location1 = new Location("Conference Room");
        Location location2 = new Location("Main Hall");
        assertFalse(location1.equals(location2));
    }
}

