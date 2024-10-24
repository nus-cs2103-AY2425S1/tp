package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



public class SupplierSortByTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SupplierSortBy(null));
    }
    @Test
    public void constructor_invalidSortBy_throwsIllegalArgumentException() {
        String invalidSortBy = "100abc";
        assertThrows(IllegalArgumentException.class, () -> new SupplierSortBy(invalidSortBy));
    }
    @Test
    public void isValidSortBy() {
        // null sortBy
        assertThrows(NullPointerException.class, () -> SupplierSortBy.isValidSortBy(null));
        // blank sortBy
        assertFalse(SupplierSortBy.isValidSortBy("")); // empty string
        assertFalse(SupplierSortBy.isValidSortBy(" ")); // spaces only
        // invalid sortBy
        assertFalse(SupplierSortBy.isValidSortBy("a")); // not in SortBy enum
        assertFalse(SupplierSortBy.isValidSortBy("1")); // not in SortBy enum
        assertFalse(SupplierSortBy.isValidSortBy("cd")); // only one sortBy allowed
        // valid sortBy
        assertTrue(SupplierSortBy.isValidSortBy("n")); // valid name
    }
    @Test
    public void equals() {
        SupplierSortBy sortBy = new SupplierSortBy("n");
        // same values -> returns true
        assertTrue(sortBy.equals(new SupplierSortBy("n")));
        // same object -> returns true
        assertTrue(sortBy.equals(sortBy));
        // null -> returns false
        assertFalse(sortBy.equals(null));
    }
}
