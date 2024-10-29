package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AreaTest {

    @Test
    public void isValidArea() {
        // null area
        assertThrows(NullPointerException.class, () -> Area.isValidArea(null));

        // invalid area
        assertFalse(Area.isValidArea("")); // empty string
        assertFalse(Area.isValidArea(" ")); // spaces only
        assertFalse(Area.isValidArea("9")); // less than 2 digits
        assertFalse(Area.isValidArea("area")); // non-numeric
        assertFalse(Area.isValidArea("9011p041")); // alphabets within digits
        assertFalse(Area.isValidArea("9312 1534")); // spaces within digits

        // valid area
        assertTrue(Area.isValidArea("911")); // exactly 2 digits
        assertTrue(Area.isValidArea("5000000"));
        assertTrue(Area.isValidArea("124293842033123")); // large area
    }

    @Test
    public void equals() {
        Area area = new Area(3);

        // same values -> returns true
        assertTrue(area.equals(new Area(3)));

        // same object -> returns true
        assertTrue(area.equals(area));

        // null -> returns false
        assertFalse(area.equals(null));

        // different types -> returns false
        assertFalse(area.equals(5.0f));

        // different values -> returns false
        assertFalse(area.equals(new Area(1)));
    }
}
