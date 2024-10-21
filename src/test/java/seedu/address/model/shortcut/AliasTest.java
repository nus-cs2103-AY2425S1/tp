package seedu.address.model.shortcut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AliasTest {

    @Test
    public void constructor_nullAlias_throwsNullPointerException() {
        // Check that a NullPointerException is thrown if the alias is null
        assertThrows(NullPointerException.class, () -> new Alias(null));
    }

    @Test
    public void constructor_invalidAlias_throwsIllegalArgumentException() {
        // Check that an IllegalArgumentException is thrown for an invalid alias (non-alphanumeric)
        String invalidAlias = "!@#";
        assertThrows(IllegalArgumentException.class, () -> new Alias(invalidAlias));
    }

    @Test
    public void constructor_validAlias_success() {
        // Check that a valid alias is successfully created
        String validAlias = "v";
        Alias alias = new Alias(validAlias);
        assertEquals(validAlias, alias.toString());
    }

    @Test
    public void isValidAlias() {
        // Test for valid aliases
        assertTrue(Alias.isValidAlias("v")); // Single letter alias
        assertTrue(Alias.isValidAlias("vg")); // Two-letter alias
        assertTrue(Alias.isValidAlias("Vegan123")); // Alphanumeric alias
        assertTrue(Alias.isValidAlias("veg an")); // Alias with spaces

        // Test for invalid aliases
        assertThrows(IllegalArgumentException.class, () -> new Alias("!@#")); // Special characters
        assertThrows(IllegalArgumentException.class, () -> new Alias(" ")); // Only space
        assertThrows(IllegalArgumentException.class, () -> new Alias(" veg")); // Starts with a space
    }

    @Test
    public void equals_sameAlias_returnsTrue() {
        // Check that two aliases with the same name are considered equal
        Alias alias1 = new Alias("v");
        Alias alias2 = new Alias("v");
        assertEquals(alias1, alias2);
    }

    @Test
    public void equals_differentAlias_returnsFalse() {
        // Check that two aliases with different names are not considered equal
        Alias alias1 = new Alias("v");
        Alias alias2 = new Alias("vg");
        assertNotEquals(alias1, alias2);
    }

    @Test
    public void hashCode_sameAlias_sameHashCode() {
        // Check that two aliases with the same name have the same hash code
        Alias alias1 = new Alias("v");
        Alias alias2 = new Alias("v");
        assertEquals(alias1.hashCode(), alias2.hashCode());
    }

    @Test
    public void hashCode_differentAlias_differentHashCode() {
        // Check that two aliases with different names have different hash codes
        Alias alias1 = new Alias("v");
        Alias alias2 = new Alias("vg");
        assertNotEquals(alias1.hashCode(), alias2.hashCode());
    }

    @Test
    public void toString_returnsAlias() {
        // Check that the toString method returns the alias string
        Alias alias = new Alias("v");
        assertEquals("v", alias.toString());
    }
}
