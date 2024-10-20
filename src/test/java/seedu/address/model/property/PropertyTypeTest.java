package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PropertyTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PropertyType(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidType = "";
        assertThrows(IllegalArgumentException.class, () -> new PropertyType(invalidType));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> PropertyType.isValidType(null));

        // invalid addresses
        assertFalse(PropertyType.isValidType("")); // empty string
        assertFalse(PropertyType.isValidType(" ")); // spaces only

        // valid addresses
        assertTrue(PropertyType.isValidType("Hougang"));
        assertTrue(PropertyType.isValidType("-")); // one character
        assertTrue(PropertyType.isValidType("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        PropertyType address = new PropertyType("Valid type");

        // same values -> returns true
        assertTrue(address.equals(new PropertyType("Valid type")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new PropertyType("Other valid type")));
    }
}
