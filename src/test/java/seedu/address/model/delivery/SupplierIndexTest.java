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
    public void isValidSupplierIndex() {

        // blank SupplierIndex
        assertFalse(SupplierIndex.isValidIndex("")); // empty string
        assertFalse(SupplierIndex.isValidIndex(" ")); // spaces only

        // invalid SupplierIndex
        assertFalse(SupplierIndex.isValidIndex("aaa")); // non-numeric

        // valid cost
        assertTrue(SupplierIndex.isValidIndex("1"));
        assertTrue(SupplierIndex.isValidIndex("100"));
        assertTrue(SupplierIndex.isValidIndex("2"));
    }
}
