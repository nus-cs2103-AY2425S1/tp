package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeliverySortByTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliverySortBy(null));
    }

    @Test
    public void constructor_invalidSortBy_throwsIllegalArgumentException() {
        String invalidSortBy = "100abc";
        assertThrows(IllegalArgumentException.class, () -> new DeliverySortBy(invalidSortBy));
    }

    @Test
    public void isValidSortBy() {
        // null sort by
        assertThrows(NullPointerException.class, () -> DeliverySortBy.isValidSortBy(null));

        // blank sort by
        assertFalse(DeliverySortBy.isValidSortBy("")); // empty string
        assertFalse(DeliverySortBy.isValidSortBy(" ")); // spaces only

        // invalid sort by
        assertFalse(DeliverySortBy.isValidSortBy("a")); // not in SortBy enum
        assertFalse(DeliverySortBy.isValidSortBy("1")); // not in SortBy enum
        assertFalse(DeliverySortBy.isValidSortBy("cd")); // only one sortBy allowed

        // valid sort by
        assertTrue(DeliverySortBy.isValidSortBy("c")); // valid cost
        assertTrue(DeliverySortBy.isValidSortBy("d")); // valid date time
        assertTrue(DeliverySortBy.isValidSortBy("s")); // valid status
    }

    @Test
    public void equals() {
        DeliverySortBy sortBy = new DeliverySortBy("c");

        // same values -> returns true
        assertTrue(sortBy.equals(new DeliverySortBy("c")));

        // same object -> returns true
        assertTrue(sortBy.equals(sortBy));

        // null -> returns false
        assertFalse(sortBy.equals(null));

        // different types -> returns false
        assertFalse(sortBy.equals(5));

        // different values -> returns false
        assertFalse(sortBy.equals(new DeliverySortBy("d")));
    }
}
