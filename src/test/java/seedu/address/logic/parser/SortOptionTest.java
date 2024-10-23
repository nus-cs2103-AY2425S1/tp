package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        SortOption sortOption = new SortOption("name");
        assertEquals("name", sortOption.toString());
    }

    @Test
    public void constructor_invalidOption_throwsIllegalArgumentException() {
        String invalidOption = "NotSortOption";
        assertThrows(IllegalArgumentException.class, () -> new SortOption(invalidOption));
    }

    @Test
    public void isValidSortOption_null_throwsNullPointerException() {
        // Null input should throw NullPointerException
        assertThrows(NullPointerException.class, () -> SortOption.isValidSortOption(null));
    }

    @Test
    public void isValidSortOption_validOption_returnsTrue() {
        // Valid sort options
        assertTrue(SortOption.isValidSortOption("name"));
        assertTrue(SortOption.isValidSortOption("name")); // Case-insensitive
    }

    @Test
    public void isValidSortOption_invalidOption_returnsFalse() {
        // Invalid sort options
        assertFalse(SortOption.isValidSortOption("age")); // Not in VALID_SORT_OPTIONS
        assertFalse(SortOption.isValidSortOption(""));
        assertFalse(SortOption.isValidSortOption(" "));
    }

    @Test
    public void testHashCode() {
        SortOption sortOption1 = new SortOption("name");
        SortOption sortOption2 = new SortOption("name");

        // Ensure that hashCode returns the same value consistently
        int initialHashCode = sortOption1.hashCode();
        assertEquals(initialHashCode, sortOption1.hashCode());
        assertEquals(initialHashCode, sortOption1.hashCode());

        // Ensure their hash codes are the same
        assertEquals(sortOption1.hashCode(), sortOption2.hashCode());
    }

    @Test
    public void equals() {
        SortOption sortOption = new SortOption("name");

        // same values -> returns true
        assertTrue(sortOption.equals(new SortOption("name")));

        // same object -> returns true
        assertTrue(sortOption.equals(sortOption));

        // null -> returns false
        assertFalse(sortOption.equals(null));

        // different types -> returns false
        assertFalse(sortOption.equals(5));

        // Currently can only create SortOption with name.
        // Add more cases to check SortOption are not equal when option is different
    }
}
