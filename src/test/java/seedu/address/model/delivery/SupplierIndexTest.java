package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SupplierIndexTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SupplierIndex(null));
    }

    @Test
    public void constructor_invalidSupplierIndex_throwsIllegalArgumentException() {
        String invalidIndex = "aaaa";
        assertThrows(IllegalArgumentException.class, () -> new SupplierIndex(invalidIndex));
    }

    @Test
    public void isValidSupplierIndex_invalidSupplierIndex_returnsFalse() {
        // blank SupplierIndex
        assertFalse(SupplierIndex.isValidIndex("")); // empty string
        assertFalse(SupplierIndex.isValidIndex(" ")); // spaces only

        // Non integer SupplierIndex
        assertFalse(SupplierIndex.isValidIndex("aaa")); // non-numeric
        assertFalse(SupplierIndex.isValidIndex("22.00")); // non-integer
    }

    @Test
    public void isValidSupplierIndex_validSupplierIndex_returnsTrue() {
        // valid cost
        assertTrue(SupplierIndex.isValidIndex("1"));
        assertTrue(SupplierIndex.isValidIndex("100"));
        assertTrue(SupplierIndex.isValidIndex("222"));
    }

    @Test
    public void equals() {
        SupplierIndex supplierIndex = new SupplierIndex("1");

        // same values -> returns true
        assertTrue(supplierIndex.equals(new SupplierIndex("1")));

        // same object -> returns true
        assertTrue(supplierIndex.equals(supplierIndex));

        // null -> returns false
        assertFalse(supplierIndex.equals(null));

        // different types -> returns false
        assertFalse(supplierIndex.equals("abcdefgh"));

        // different values -> returns false
        assertFalse(supplierIndex.equals(new SupplierIndex("50")));
    }
}
