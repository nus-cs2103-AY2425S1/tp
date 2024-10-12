package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for {@code SortOption}.
 */
public class SortOptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Null input should throw NullPointerException
        assertThrows(NullPointerException.class, () -> new SortOption(null));
    }

    @Test
    public void constructor_validOption_createsSortOption() {
        // Valid sort option
        SortOption sortOption = new SortOption("alphabet");
        assertTrue(sortOption.toString().equals("alphabet"));
    }

    @Test
    public void isValidSortOption_null_throwsNullPointerException() {
        // Null input should throw NullPointerException
        assertThrows(NullPointerException.class, () -> SortOption.isValidSortOption(null));
    }

    @Test
    public void isValidSortOption_validOption_returnsTrue() {
        // Valid sort options
        assertTrue(SortOption.isValidSortOption("alphabet"));
        assertTrue(SortOption.isValidSortOption("ALPHABET")); // Case-insensitive
    }

    @Test
    public void isValidSortOption_invalidOption_returnsFalse() {
        // Invalid sort options
        assertFalse(SortOption.isValidSortOption("age")); // Not in VALID_SORT_OPTIONS
        assertFalse(SortOption.isValidSortOption("name"));
        assertFalse(SortOption.isValidSortOption(""));
        assertFalse(SortOption.isValidSortOption(" "));
    }

    @Test
    public void equals() {
        SortOption sortOption = new SortOption("alphabet");

        // same values -> returns true
        assertTrue(sortOption.equals(new SortOption("alphabet")));

        // same object -> returns true
        assertTrue(sortOption.equals(sortOption));

        // null -> returns false
        assertFalse(sortOption.equals(null));

        // different types -> returns false
        assertFalse(sortOption.equals(5));

        // different values -> returns false
        assertFalse(sortOption.equals(new SortOption("age")));
    }
}
