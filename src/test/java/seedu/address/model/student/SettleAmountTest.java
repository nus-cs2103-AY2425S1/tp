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
        assertFalse(PaidAmount.isValidPaidAmount("")); // empty string
        assertFalse(PaidAmount.isValidPaidAmount(" ")); // spaces only
        assertFalse(PaidAmount.isValidPaidAmount("1.234")); // more than 2 decimal places
        assertFalse(PaidAmount.isValidPaidAmount("1.2.3")); // more than 1 decimal point
        assertFalse(PaidAmount.isValidPaidAmount("-1.23")); // negative number
        assertFalse(PaidAmount.isValidPaidAmount("10000000.00")); // more than max value

        // valid paidAmounts
        assertTrue(PaidAmount.isValidPaidAmount("1")); // 0 decimal places
        assertTrue(PaidAmount.isValidPaidAmount("1.2")); // 1 decimal place
        assertTrue(PaidAmount.isValidPaidAmount("123.23")); // 2 decimal places
        assertTrue(PaidAmount.isValidPaidAmount("9999999.99")); // boundary value
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
