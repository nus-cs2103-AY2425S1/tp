package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        // invalid locations
        assertThrows(IllegalArgumentException.class, () -> new Location("")); // empty string
        assertThrows(IllegalArgumentException.class, () -> new Location(" ")); // spaces only
        assertThrows(IllegalArgumentException.class, () -> new Location("a ".repeat(101))); // > 100 char
    }

    @Test
    public void isValidLocation_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));
    }

    @Test
    public void isValidLocation_invalidLocations_returnsFalse() {
        // invalid locations
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("a ".repeat(101))); // 101 characters after space inclusion
        assertFalse(Location.isValidLocation("Location-1")); // contains non-alphanumeric character (dash)
        assertFalse(Location.isValidLocation("Location.Name")); // contains punctuation (dot)
    }

    @Test
    public void isValidLocation_validLocations_returnsTrue() {
        // valid locations
        assertTrue(Location.isValidLocation("123 Main Street")); // alphanumeric with spaces
        assertTrue(Location.isValidLocation("Downtown")); // single word
        assertTrue(Location.isValidLocation("Main Street 123")); // alphanumeric with spaces
        assertTrue(Location.isValidLocation("Building Name Block 12")); // multiple words, spaces allowed
        assertTrue(Location.isValidLocation("a".repeat(100))); // exactly 100 characters
    }

    @Test
    public void equals() {
        Location location1 = new Location("Location 1");
        Location location2 = new Location("Location 1");
        Location location3 = new Location("Different Location");

        // same values -> returns true
        assertTrue(location1.equals(location2));

        // same object -> returns true
        assertTrue(location1.equals(location1));

        // null -> returns false
        assertFalse(location1.equals(null));

        // different types -> returns false
        assertFalse(location1.equals(5.0f));

        // different values -> returns false
        assertFalse(location1.equals(location3));
    }
}
