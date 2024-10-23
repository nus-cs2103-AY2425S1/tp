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
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidSortBy = "100abc";
        assertThrows(IllegalArgumentException.class, () -> new DeliverySortBy(invalidSortBy));
    }

    @Test
    public void isValidSortBy() {
        // null quantity
        assertThrows(NullPointerException.class, () -> DeliverySortBy.isValidSortBy(null));

        // blank cost
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only

        // invalid sortBy
        assertFalse(DeliverySortBy.isValidSortBy("a")); // not in SortBy enum
        assertFalse(DeliverySortBy.isValidSortBy("1")); // not in SortBy enum
        assertFalse(DeliverySortBy.isValidSortBy("cd")); // only one sortBy allowed

        assertFalse(Quantity.isValidQuantity("100")); // missing unit
        assertFalse(Quantity.isValidQuantity("abc kg")); // non-numeric quantity
        assertFalse(Quantity.isValidQuantity("100abc")); // no space between number and unit
        assertFalse(Quantity.isValidQuantity("100 grams")); // invalid unit (grams instead of g)
        assertFalse(Quantity.isValidQuantity("100g")); // no space between number and unit
        assertFalse(Quantity.isValidQuantity("1 liters   ")); // space at the end
        assertFalse(Quantity.isValidQuantity("  1 liters   ")); // space at the start

        // valid sortBy
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
