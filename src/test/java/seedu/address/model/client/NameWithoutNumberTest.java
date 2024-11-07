package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameWithoutNumberTest {
    @Test
    void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NameWithoutNumber(null));
    }

    @Test
    void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "John123"; // Contains numbers
        assertThrows(IllegalArgumentException.class, () -> new NameWithoutNumber(invalidName));
    }

    @Test
    void isValidNameWithoutNumber_validNames_returnsTrue() {
        // Valid names
        assertTrue(NameWithoutNumber.isValidNameWithoutNumber("John Doe"));
        assertTrue(NameWithoutNumber.isValidNameWithoutNumber("Alice"));
        assertTrue(NameWithoutNumber.isValidNameWithoutNumber("Mary Jane"));
    }

    @Test
    void isValidNameWithoutNumber_invalidNames_returnsFalse() {
        // Invalid names
        assertFalse(NameWithoutNumber.isValidNameWithoutNumber("John  Doe")); // Multiple spaces
        assertFalse(NameWithoutNumber.isValidNameWithoutNumber(" John")); // Leading space
        assertFalse(NameWithoutNumber.isValidNameWithoutNumber("John ")); // Trailing space
        assertFalse(NameWithoutNumber.isValidNameWithoutNumber("John123")); // Contains numbers
        assertFalse(NameWithoutNumber.isValidNameWithoutNumber("John@Doe")); // Contains special characters
        assertFalse(NameWithoutNumber.isValidNameWithoutNumber("")); // Empty string
    }

    @Test
    void isValidNameWithoutNumber_exceedsMaxLength_returnsFalse() {
        // 748 characters - exceeds max length of 747
        String longName = "A".repeat(748);
        assertFalse(NameWithoutNumber.isValidNameWithoutNumber(longName));

        // Exactly 747 characters - valid
        String validLongName = "A".repeat(747);
        assertTrue(NameWithoutNumber.isValidNameWithoutNumber(validLongName));
    }
}
