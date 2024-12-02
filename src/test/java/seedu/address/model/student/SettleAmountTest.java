package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SettleAmountTest {
    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SettleAmount(null));
    }

    @Test
    void constructor_invalidSettleAmount_throwsIllegalArgumentException() {
        String invalidSettleAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new SettleAmount(invalidSettleAmount));
    }

    @Test
    void isValidSettleAmount() {
        // null settleAmount
        assertThrows(NullPointerException.class, () -> SettleAmount.isValidSettleAmount(null));

        // invalid settleAmounts
        assertFalse(SettleAmount.isValidSettleAmount("")); // empty string
        assertFalse(SettleAmount.isValidSettleAmount(" ")); // spaces only
        assertFalse(SettleAmount.isValidSettleAmount("1.234")); // more than 2 decimal places
        assertFalse(SettleAmount.isValidSettleAmount("1.2.3")); // more than 1 decimal point
        assertFalse(SettleAmount.isValidSettleAmount("-1.23")); // negative number
        assertFalse(SettleAmount.isValidSettleAmount("10000000.00")); // more than max value
        assertFalse(SettleAmount.isValidSettleAmount("0"));

        // valid paidAmounts
        assertTrue(SettleAmount.isValidSettleAmount("1")); // 0 decimal places
        assertTrue(SettleAmount.isValidSettleAmount("1.2")); // 1 decimal place
        assertTrue(SettleAmount.isValidSettleAmount("123.23")); // 2 decimal places
        assertTrue(SettleAmount.isValidSettleAmount("9999999.99")); // boundary value
    }

    @Test
    void equals() {
        SettleAmount settleAmount = new SettleAmount("1.23");
        SettleAmount sameSettleAmount = new SettleAmount("1.23");
        SettleAmount differentSettleAmount = new SettleAmount("1.24");

        // same values -> returns true
        assertTrue(settleAmount.equals(sameSettleAmount));

        // same object -> returns true
        assertTrue(settleAmount.equals(settleAmount));

        // null -> returns false
        assertFalse(settleAmount.equals(null));

        // different types -> returns false
        assertFalse(settleAmount.equals(5.0f));

        // different values -> returns false
        assertFalse(settleAmount.equals(differentSettleAmount));
    }
}
