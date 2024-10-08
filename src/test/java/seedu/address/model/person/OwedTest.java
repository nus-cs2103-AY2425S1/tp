package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OwedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Owed(null));
    }

    @Test
    public void constructor_invalidOwed_throwsIllegalArgumentException() {
        String invalidOwed = "";
        assertThrows(IllegalArgumentException.class, () -> new Owed(invalidOwed));
    }

    @Test
    public void isValidOwed() {
        // null owed
        assertThrows(NullPointerException.class, () -> Owed.isValidOwed(null));

        // invalid oweds
        assertFalse(Owed.isValidOwed("")); // empty string
        assertFalse(Owed.isValidOwed(" ")); // spaces only
        assertFalse(Owed.isValidOwed("1.234")); // more than 2 decimal places
        assertFalse(Owed.isValidOwed("1.2.3")); // more than 1 decimal point
        assertFalse(Owed.isValidOwed("-1.23")); // negative number

        // valid oweds
        assertTrue(Owed.isValidOwed("1")); // 0 decimal places
        assertTrue(Owed.isValidOwed("1.2")); // 1 decimal place
        assertTrue(Owed.isValidOwed("123.23")); // 2 decimal places
        assertTrue(Owed.isValidOwed("0")); // 3 digits
    }

    @Test
    public void toStringTest() {
        Owed owed = new Owed("1.23");
        assertEquals("1.23", owed.toString());

        Owed owed2 = new Owed("1.0");
        assertEquals("1.00", owed2.toString());

        Owed owed3 = new Owed("0");
        assertEquals("0.00", owed3.toString());
    }

    @Test
    public void equals() {
        Owed owed = new Owed("1.23");
        Owed sameOwed = new Owed("1.23");
        Owed differentOwed = new Owed("1.24");

        // same values -> returns true
        assertTrue(owed.equals(sameOwed));

        // same object -> returns true
        assertTrue(owed.equals(owed));

        // null -> returns false
        assertFalse(owed.equals(null));

        // different types -> returns false
        assertFalse(owed.equals(5.0f));

        // different values -> returns false
        assertFalse(owed.equals(differentOwed));
    }

    @Test
    public void hashCodeTest() {
        Owed owed = new Owed("1.23");
        Owed sameOwed = new Owed("1.23");
        Owed differentOwed = new Owed("1.24");

        assertEquals(owed.hashCode(), sameOwed.hashCode());
        assertNotEquals(owed.hashCode(), differentOwed.hashCode());

    }
}
