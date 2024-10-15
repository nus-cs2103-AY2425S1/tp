package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PaymentTest {

    // Test the constructor with the Boolean parameter (true and false cases)
    @Test
    public void constructor_booleanParam_success() {
        Payment paid = new Payment(true);
        Payment notPaid = new Payment(false);

        assertTrue(paid.hasPaid);
        assertFalse(notPaid.hasPaid);
    }

    // Test the default constructor (should default to false)
    @Test
    public void constructor_defaultConstructor_success() {
        Payment defaultPayment = new Payment();
        assertFalse(defaultPayment.hasPaid); // Default should be false
    }

    // Test the isValidPayment method with valid inputs
    @Test
    public void isValidPayment_validValues_returnsTrue() {
        assertTrue(Payment.isValidPayment("true")); // Valid payment status
        assertTrue(Payment.isValidPayment("false")); // Valid payment status
    }

    // Test the isValidPayment method with invalid inputs
    @Test
    public void isValidPayment_invalidValues_returnsFalse() {
        assertFalse(Payment.isValidPayment("paid")); // Invalid format
        assertFalse(Payment.isValidPayment("unpaid")); // Invalid format
        assertFalse(Payment.isValidPayment("123")); // Invalid characters
        assertFalse(Payment.isValidPayment("")); // Empty string
        assertFalse(Payment.isValidPayment("null")); // Random string
    }

    // Test the toString method for both true and false cases
    @Test
    public void toString_correctRepresentation() {
        Payment paid = new Payment(true);
        Payment notPaid = new Payment(false);

        assertEquals("Paid", paid.toString());
        assertEquals("Not Paid", notPaid.toString());
    }

    // Test the equals method (equality between objects)
    @Test
    public void equals_sameObjects_returnsTrue() {
        Payment paid1 = new Payment(true);
        Payment paid2 = new Payment(true);
        Payment notPaid1 = new Payment(false);
        Payment notPaid2 = new Payment(false);

        // Same values should return true
        assertTrue(paid1.equals(paid2));
        assertTrue(notPaid1.equals(notPaid2));

        // Objects should equal themselves
        assertTrue(paid1.equals(paid1));
    }

    @Test
    public void equals_differentObjects_returnsFalse() {
        Payment paid = new Payment(true);
        Payment notPaid = new Payment(false);

        // Different values should return false
        assertFalse(paid.equals(notPaid));

        // Different types should return false
        assertFalse(paid.equals(null));
        assertFalse(paid.equals("Paid"));
    }

    // Test the hashCode method for consistency with equals
    @Test
    public void hashCode_consistencyWithEquals() {
        Payment paid1 = new Payment(true);
        Payment paid2 = new Payment(true);
        Payment notPaid = new Payment(false);

        // Objects with the same value should have the same hashcode
        assertEquals(paid1.hashCode(), paid2.hashCode());

        // Objects with different values should have different hashcodes
        assertFalse(paid1.hashCode() == notPaid.hashCode());
    }
}

