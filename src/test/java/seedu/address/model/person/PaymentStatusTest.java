package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Payment Status field in Person class.
 */
public class PaymentStatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaymentStatus(null));
    }

    @Test
    public void constructor_invalidPaymentStatus_throwsIllegalArgumentException() {
        String invalidStatus = "not paid";
        assertThrows(IllegalArgumentException.class, () -> new PaymentStatus(invalidStatus));
    }

    @Test
    public void isValidPaymentStatus() {
        // null payment status
        assertThrows(NullPointerException.class, () -> PaymentStatus.isValidPaymentStatus(null));

        // invalid payment status
        assertFalse(PaymentStatus.isValidPaymentStatus(" ")); // spaces
        assertFalse(PaymentStatus.isValidPaymentStatus("")); // empty string
        assertFalse(PaymentStatus.isValidPaymentStatus("partiL")); // typo: partial
        assertFalse(PaymentStatus.isValidPaymentStatus("unpaid")); // 'pending'


        // valid payment status
        assertTrue(PaymentStatus.isValidPaymentStatus("paid"));
        assertTrue(PaymentStatus.isValidPaymentStatus("pending"));
        assertTrue(PaymentStatus.isValidPaymentStatus("partial"));
        assertTrue(PaymentStatus.isValidPaymentStatus("late"));
        assertTrue(PaymentStatus.isValidPaymentStatus("PAID"));
        assertTrue(PaymentStatus.isValidPaymentStatus("PENDING"));
        assertTrue(PaymentStatus.isValidPaymentStatus("LATE"));
        assertTrue(PaymentStatus.isValidPaymentStatus("PARTIAL"));
    }

    @Test
    public void toString_validStatus_returnsCorrectString() {

        PaymentStatus pendingPaymentStatus = new PaymentStatus("pending");
        assertTrue(pendingPaymentStatus.toString().equals("pending"));

        PaymentStatus partialPaymentStatus = new PaymentStatus("partial");
        assertTrue(partialPaymentStatus.toString().equals("partial"));

        PaymentStatus latePaymentStatus = new PaymentStatus("late");
        assertTrue(latePaymentStatus.toString().equals("late"));

        PaymentStatus paidPaymentStatus = new PaymentStatus("paid");
        assertTrue(paidPaymentStatus.toString().equals("paid"));
    }

    @Test
    public void equals() {
        PaymentStatus partial = new PaymentStatus("partial");
        PaymentStatus pending = new PaymentStatus("pending");
        PaymentStatus late = new PaymentStatus("late");
        PaymentStatus paid = new PaymentStatus("paid");

        // same values -> returns true
        assertTrue(partial.equals(new PaymentStatus("partial")));
        assertTrue(pending.equals(new PaymentStatus("pending")));
        assertTrue(late.equals(new PaymentStatus("late")));
        assertTrue(paid.equals(new PaymentStatus("paid")));

        // same object -> returns true
        assertTrue(partial.equals(partial));

        // null -> returns false
        assertFalse(partial.equals(null));

        // different types -> returns false
        assertFalse(partial.equals(5));

        // different values -> returns false
        assertFalse(partial.equals(paid));
    }

    @Test
    public void hashCode_sameStatus_sameHashCode() {
        PaymentStatus paid1 = new PaymentStatus("paid");
        PaymentStatus paid2 = new PaymentStatus("PAID");
        assertTrue(paid1.hashCode() == paid2.hashCode());

        PaymentStatus partial1 = new PaymentStatus("partial");
        PaymentStatus partial2 = new PaymentStatus("PARTIAL");
        assertTrue(partial1.hashCode() == partial2.hashCode());

        PaymentStatus pending1 = new PaymentStatus("pending");
        PaymentStatus pending2 = new PaymentStatus("PENDING");
        assertTrue(pending1.hashCode() == pending2.hashCode());

        PaymentStatus late1 = new PaymentStatus("late");
        PaymentStatus late2 = new PaymentStatus("LATE");
        assertTrue(late1.hashCode() == late2.hashCode());
    }
}
