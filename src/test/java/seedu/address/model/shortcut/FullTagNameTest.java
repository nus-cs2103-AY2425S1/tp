package seedu.address.model.shortcut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FullTagNameTest {

    @Test
    public void constructor_nullFullTagName_throwsNullPointerException() {
        // Check that a NullPointerException is thrown if the fullTagName is null
        assertThrows(NullPointerException.class, () -> new FullTagName(null));
    }

    @Test
    public void constructor_invalidFullTagName_throwsIllegalArgumentException() {
        // Check that an IllegalArgumentException is thrown for an invalid fullTagName (non-alphanumeric)
        String invalidFullTagName = "!@#";
        String invalidFullTagName2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertThrows(IllegalArgumentException.class, () -> new FullTagName(invalidFullTagName));
        assertThrows(IllegalArgumentException.class, () -> new FullTagName(invalidFullTagName2));
    }

    @Test
    public void constructor_validFullTagName_success() {
        // Check that a valid fullTagName is successfully created
        String validFullTagName = "Vegetarian";
        FullTagName fullTagName = new FullTagName(validFullTagName);
        assertEquals(validFullTagName, fullTagName.toString());
    }

    @Test
    public void isValidTagName() {
        // Test for valid tag names
        assertTrue(FullTagName.isValidTagName("Vegetarian")); // Valid alphanumeric name
        assertTrue(FullTagName.isValidTagName("Vegan123")); // Alphanumeric tag name
        assertTrue(FullTagName.isValidTagName("veg an")); // Tag name with spaces

        // Test for invalid tag names
        assertThrows(IllegalArgumentException.class, () -> new FullTagName("!@#")); // Special characters
        assertThrows(IllegalArgumentException.class, () -> new FullTagName(" ")); // Only space
        assertThrows(IllegalArgumentException.class, () -> new FullTagName(" veg")); // Starts with a space
    }

    @Test
    public void equals_sameFullTagName_returnsTrue() {
        FullTagName fullTagName1 = new FullTagName("Vegan");
        FullTagName fullTagName2 = new FullTagName("Vegan");
        FullTagName fullTagName3 = new FullTagName("VeGan");
        assertEquals(fullTagName1, fullTagName2);
        assertEquals(fullTagName1, fullTagName3);
    }

    @Test
    public void equals_differentFullTagName_returnsFalse() {
        FullTagName fullTagName1 = new FullTagName("Vegan");
        FullTagName fullTagName2 = new FullTagName("Vegetarian");
        Alias alias = new Alias("V");
        assertNotEquals(fullTagName1, fullTagName2);
        assertNotEquals(fullTagName1, alias);
    }

    @Test
    public void hashCode_sameFullTagName_sameHashCode() {
        FullTagName fullTagName1 = new FullTagName("Vegan");
        FullTagName fullTagName2 = new FullTagName("Vegan");
        assertEquals(fullTagName1.hashCode(), fullTagName2.hashCode());
    }

    @Test
    public void hashCode_differentFullTagName_differentHashCode() {
        // Check that two FullTagName objects with different names have different hash codes
        FullTagName fullTagName1 = new FullTagName("Vegan");
        FullTagName fullTagName2 = new FullTagName("Vegetarian");
        assertNotEquals(fullTagName1.hashCode(), fullTagName2.hashCode());
    }

    @Test
    public void toString_returnsFullTagName() {
        // Check that the toString method returns the full tag name string
        FullTagName fullTagName = new FullTagName("Vegan");
        assertEquals("Vegan", fullTagName.toString());
    }
}
