package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
public class PaidAmountTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaidAmount(null));
    }

    @Test
    void constructor_invalidPaidAmount_throwsIllegalArgumentException() {
        String invalidPaidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new PaidAmount(invalidPaidAmount));
    }

    @Test
    void isValidPaidAmount() {
        // null paidAmount
        assertThrows(NullPointerException.class, () -> PaidAmount.isValidPaidAmount(null));

        // invalid paidAmounts
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
        assertTrue(PaidAmount.isValidPaidAmount("0")); // 3 digits
        assertTrue(PaidAmount.isValidPaidAmount("9999999.99")); // boundary value
    }

    @Test
    public void updateValue() {
        PaidAmount paidAmount = new PaidAmount("10.00");

        PaidAmount updatedPaidAmount = paidAmount.updateValue(5.00);
        assertEquals("15.00", updatedPaidAmount.toString());

        PaidAmount updatedPaidAmount2 = paidAmount.updateValue(0.01);
        assertEquals("10.01", updatedPaidAmount2.toString());
    }


    @Test
    void toStringTest() {
        PaidAmount paidAmount = new PaidAmount("1.23");
        assertEquals("1.23", paidAmount.toString());

        PaidAmount paidAmount2 = new PaidAmount("1.0");
        assertEquals("1.00", paidAmount2.toString());

        PaidAmount paidAmount3 = new PaidAmount("0");
        assertEquals("0.00", paidAmount3.toString());
    }

    @Test
    void equals() {
        PaidAmount paidAmount = new PaidAmount("1.23");
        PaidAmount samePaidAmount = new PaidAmount("1.23");
        PaidAmount differentPaidAmount = new PaidAmount("1.24");

        // same values -> returns true
        assertTrue(paidAmount.equals(samePaidAmount));

        // same object -> returns true
        assertTrue(paidAmount.equals(paidAmount));

        // null -> returns false
        assertFalse(paidAmount.equals(null));

        // different types -> returns false
        assertFalse(paidAmount.equals(5.0f));

        // different values -> returns false
        assertFalse(paidAmount.equals(differentPaidAmount));
    }

    @Test
    void hashCodeTest() {
        PaidAmount paidAmount = new PaidAmount("1.23");
        PaidAmount samePaidAmount = new PaidAmount("1.23");
        PaidAmount differentPaidAmount = new PaidAmount("1.24");

        // same values -> returns true
        assertTrue(paidAmount.hashCode() == samePaidAmount.hashCode());

        // different values -> returns false
        assertFalse(paidAmount.hashCode() == differentPaidAmount.hashCode());

    }
}
