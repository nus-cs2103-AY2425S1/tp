package seedu.address.model.company;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BillingDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BillingDate(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new BillingDate(invalidName));
    }

    @Test
    public void isValidBillingDate() {
        // null billing date
        assertThrows(NullPointerException.class, () -> BillingDate.isValidBillingDate(null));

        // invalid billing date
        assertFalse(BillingDate.isValidBillingDate("")); // empty string
        assertFalse(BillingDate.isValidBillingDate(" ")); // space
        assertFalse(BillingDate.isValidBillingDate("abc")); // not number
        assertFalse(BillingDate.isValidBillingDate("0")); // below range
        assertFalse(BillingDate.isValidBillingDate("29")); // above range

        // valid billing date
        assertTrue(BillingDate.isValidBillingDate("1")); // first in range
        assertTrue(BillingDate.isValidBillingDate("12")); // middle of range
        assertTrue(BillingDate.isValidBillingDate("28")); // last in range
    }
}
