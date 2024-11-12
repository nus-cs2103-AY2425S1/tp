package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class PaymentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Payment(null));
    }

    @Test
    public void constructor_invalidInsurancePayment_throwsIllegalArgumentException() {
        String invalidPayment = "";
        assertThrows(IllegalArgumentException.class, () -> new Payment(invalidPayment));
    }

    @Test
    public void isValidInsurancePayment() {
        // null insurance payment
        assertThrows(NullPointerException.class, () -> Payment.isValidInsurancePayment(null));

        // invalid insurance payments
        assertFalse(Payment.isValidInsurancePayment("")); // empty string
        assertFalse(Payment.isValidInsurancePayment(" ")); // spaces only
        assertFalse(Payment.isValidInsurancePayment("2023-11-01")); // missing amount
        assertFalse(Payment.isValidInsurancePayment("300.00")); // missing date
        assertFalse(Payment.isValidInsurancePayment("invalid-date 300.00")); // invalid date
        assertFalse(Payment.isValidInsurancePayment("2023-11-01 invalid-amount")); // invalid amount
        assertFalse(Payment.isValidInsurancePayment("2023-11-01 -100.00")); // negative amount

        // valid insurance payments
        assertTrue(Payment.isValidInsurancePayment("2023-11-01 300.00"));
        assertTrue(Payment.isValidInsurancePayment("2023-11-01 1000")); // integer amount
    }

    @Test
    public void updatePaymentDueDate_policyExpiringSoon_setsDueDateToMaxDate() {
        // Arrange
        Policy mockPolicy = mock(Policy.class);
        when(mockPolicy.isExpiringSoon()).thenReturn(true);

        Payment payment = new Payment("2023-11-01 300.00");

        // Act
        payment.updatePaymentDueDate(mockPolicy);

        // Assert
        assertEquals(LocalDate.MAX, payment.getPaymentDueDate());
    }

    @Test
    public void updatePaymentDueDate_policyNotExpiringSoon_incrementsDueDateByOneYear() {
        // Arrange
        Policy mockPolicy = mock(Policy.class);
        when(mockPolicy.isExpiringSoon()).thenReturn(false);

        Payment payment = new Payment("2023-11-01 300.00");

        // Act
        payment.updatePaymentDueDate(mockPolicy);

        // Assert
        assertEquals(LocalDate.of(2024, 11, 1), payment.getPaymentDueDate());
    }

    @Test
    public void equals() {
        Payment payment = new Payment("2023-11-01 300.00");

        // same values -> returns true
        assertTrue(payment.equals(new Payment("2023-11-01 300.00")));

        // same object -> returns true
        assertTrue(payment.equals(payment));

        // null -> returns false
        assertFalse(payment.equals(null));

        // different types -> returns false
        assertFalse(payment.equals(5));

        // different values -> returns false
        assertFalse(payment.equals(new Payment("2023-11-01 400.00"))); // different amount
        assertFalse(payment.equals(new Payment("2023-12-01 300.00"))); // different date
    }
}
