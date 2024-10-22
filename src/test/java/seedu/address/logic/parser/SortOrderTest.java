package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SortOrderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortOrder(null));
    }

    @Test
    public void constructor_invalidCost_throwsIllegalArgumentException() {
        String invalidSortOrder = "abc";
        assertThrows(IllegalArgumentException.class, () -> new SortOrder(invalidSortOrder));
    }

    @Test
    public void isValidCost() {
        // null cost
        assertThrows(NullPointerException.class, () -> SortOrder.isValidSortOrder(null));

        // blank sort order
        assertFalse(SortOrder.isValidSortOrder("")); // empty string
        assertFalse(SortOrder.isValidSortOrder(" ")); // spaces only

        // invalid sort order
        assertFalse(SortOrder.isValidSortOrder("c")); // not a or d
        assertFalse(SortOrder.isValidSortOrder("123")); // not a or d
        assertFalse(SortOrder.isValidSortOrder("ad")); // cannot be both a and d

        // valid sort order
        assertTrue(SortOrder.isValidSortOrder("a"));
        assertTrue(SortOrder.isValidSortOrder("d"));
    }

    @Test
    public void equals() {
        SortOrder sortOrder = new SortOrder("a");

        // same values -> returns true
        assertTrue(sortOrder.equals(new SortOrder("a")));

        // same object -> returns true
        assertTrue(sortOrder.equals(sortOrder));

        // null -> returns false
        assertFalse(sortOrder.equals(null));

        // different types -> returns false
        assertFalse(sortOrder.equals(1));

        // different values -> returns false
        assertFalse(sortOrder.equals(new SortOrder("d")));
    }
}
