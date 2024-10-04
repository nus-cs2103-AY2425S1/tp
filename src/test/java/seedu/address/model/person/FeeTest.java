package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
public class FeeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> generateFee(null));
    }

    @Test
    public void constructor_invalidFee_throwsIllegalArgumentException() {
        String invalidFee = "";
        assertThrows(IllegalArgumentException.class, () -> generateFee(invalidFee));
    }
    @Test
    public void isValidFee() {
        // null fee
        assertThrows(NullPointerException.class, () -> Fee.isValidFee(null));

        // invalid fees
        assertFalse(Fee.isValidFee("")); // empty string
        assertFalse(Fee.isValidFee(" ")); // spaces only
        assertFalse(Fee.isValidFee("1.234")); // more than 2 decimal places
        assertFalse(Fee.isValidFee("1.2.3")); // more than 1 decimal point
        assertFalse(Fee.isValidFee("-1.23")); // negative number

        // valid fees
        assertTrue(Fee.isValidFee("1")); // 0 decimal places
        assertTrue(Fee.isValidFee("1.2")); // 1 decimal place
        assertTrue(Fee.isValidFee("123.23")); // 2 decimal places
        assertTrue(Fee.isValidFee("0")); // 3 digits
    }

    @Test
    public void toStringTest() {
        Fee fee = generateFee("1.23");
        assertEquals("1.23", fee.toString());

        Fee fee2 = generateFee("1.0");
        assertEquals("1.00", fee2.toString());

        Fee fee3 = generateFee("0");
        assertEquals("0.00", fee3.toString());
    }

    @Test
    public void equals() {
        Fee fee = generateFee("1.23");
        Fee sameFee = generateFee("1.23");
        Fee differentFee = generateFee("2.34");

        // same values -> returns true
        assertTrue(fee.equals(sameFee));

        // same object -> returns true
        assertTrue(fee.equals(fee));

        // null -> returns false
        assertFalse(fee.equals(null));

        // different types -> returns false
        assertFalse(fee.equals(5.0f));

        // different values -> returns false
        assertFalse(fee.equals(differentFee));
    }

    @Test
    public void hashCodeTest() {
        Fee fee = generateFee("1.23");
        Fee sameFee = generateFee("1.23");
        Fee differentFee = generateFee("2.34");

        // same values -> returns true
        assertEquals(fee.hashCode(), sameFee.hashCode());
        assertNotEquals(fee.hashCode(), differentFee.hashCode());
    }

    public Fee generateFee(String fee) {
        return new Fee(fee) {

        };
    }
}
