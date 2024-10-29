package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Expect a NullPointerException when passing a null balance
        assertThrows(NullPointerException.class, () -> new Fees(null));
    }

    @Test
    public void constructor_validFees_success() {
        // Valid fees strings
        String validFees1 = "100";
        String validFees2 = "0";

        // Check construction does not throw any exceptions
        Fees fees1 = new Fees(validFees1);
        Fees fees2 = new Fees(validFees2);

        // Verify values
        assertEquals("100", fees1.value);
        assertEquals("0", fees2.value);
    }

    @Test
    public void isValidFees_validValues_returnsTrue() {
        // Valid positive integer values
        assertTrue(Fees.isValidFees("100"));
        assertTrue(Fees.isValidFees("1"));

        // Valid zero value
        assertTrue(Fees.isValidFees("0"));
    }

    @Test
    public void isValidFees_invalidValues_returnsFalse() {
        // Non-numeric strings
        assertFalse(Fees.isValidFees("abc"));

        // Negative numbers
        assertFalse(Fees.isValidFees("-1"));

        // Decimal values
        assertFalse(Fees.isValidFees("10.5"));

        // Empty string
        assertFalse(Fees.isValidFees(""));

        // Spaces
        assertFalse(Fees.isValidFees("   "));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Fees fees1 = new Fees("100");
        Fees fees2 = new Fees("100");

        // Check equality based on value
        assertEquals(fees1, fees2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Fees fees1 = new Fees("100");
        Fees fees2 = new Fees("200");

        // Check inequality based on value
        assertNotEquals(fees1, fees2);
    }

    @Test
    public void toString_correctFormat_returnsExpectedString() {
        Fees fees = new Fees("100");

        // Check that toString formats as expected
        assertEquals("Balance: 100", fees.toString());
    }

    @Test
    public void hashCode_sameValues_returnsSameHash() {
        Fees fees1 = new Fees("100");
        Fees fees2 = new Fees("100");

        // Check that hash codes are equal for objects with same value
        assertEquals(fees1.hashCode(), fees2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHash() {
        Fees fees1 = new Fees("100");
        Fees fees2 = new Fees("200");

        // Check that hash codes are different for objects with different values
        assertNotEquals(fees1.hashCode(), fees2.hashCode());
    }
}
