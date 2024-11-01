package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PaymentTest {
    @Test
    public void constructor_validIntegerString_success() {
        Payment positivePayment = new Payment("123");
        Payment negativePayment = new Payment("-123");
        Payment zeroPayment = new Payment("0");

        assertEquals("123", positivePayment.overdueAmount);
        assertEquals("-123", negativePayment.overdueAmount);
        assertEquals("0", zeroPayment.overdueAmount);
    }
    @Test
    public void constructor_null_throwsNullPointerException() {
        // Ensure that passing null throws an exception
        try {
            new Payment(null);
        } catch (NullPointerException e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    @Test
    public void isValidPayment_validValues_returnsTrue() {
        assertTrue(Payment.isValidPayment("123")); // Positive integer
        assertTrue(Payment.isValidPayment("-123")); // Negative integer
        assertTrue(Payment.isValidPayment("0")); // Zero
    }

    @Test
    public void isValidPayment_invalidValues_returnsFalse() {
        assertFalse(Payment.isValidPayment("abc")); // Non-numeric
        assertFalse(Payment.isValidPayment("12.34")); // Floating point number
        assertFalse(Payment.isValidPayment("")); // Empty string// Leading zeros
        assertTrue(Payment.isValidPayment("-0")); // Negative zero
        assertFalse(Payment.isValidPayment("$123")); // Contains non-numeric character
    }

    @Test
    public void toString_correctRepresentation() {
        Payment positivePayment = new Payment("123");
        Payment zeroPayment = new Payment("0");

        assertEquals("Current payment overdue: $123", positivePayment.toString());
        assertEquals("Current payment overdue: $0", zeroPayment.toString());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Payment payment1 = new Payment("123");
        Payment payment2 = new Payment("123");

        assertTrue(payment1.equals(payment2));
        assertTrue(payment1.equals(payment1));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Payment payment1 = new Payment("123");
        Payment payment2 = new Payment("456");
        Payment payment3 = new Payment("-123");

        assertFalse(payment1.equals(payment2));
        assertFalse(payment1.equals(payment3));
        assertFalse(payment1.equals(null));
        assertFalse(payment1.equals("123"));
    }

    @Test
    public void hashCode_consistencyWithEquals() {
        Payment payment1 = new Payment("123");
        Payment payment2 = new Payment("123");

        assertEquals(payment1.hashCode(), payment2.hashCode());

        Payment payment3 = new Payment("-123");
        assertFalse(payment1.hashCode() == payment3.hashCode());
    }
}
