package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryStatusMatchInputPredicateTest {
    @Test
    public void equals() {
        Status firstPredicateStatus = Status.DELIVERED;
        Status secondPredicateStatus = Status.PENDING;
        DeliveryStatusMatchInputPredicate firstPredicate =
                new DeliveryStatusMatchInputPredicate(firstPredicateStatus);
        DeliveryStatusMatchInputPredicate secondPredicate =
                new DeliveryStatusMatchInputPredicate(secondPredicateStatus);
        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeliveryStatusMatchInputPredicate firstPredicateCopy =
                new DeliveryStatusMatchInputPredicate(firstPredicateStatus);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }

    @Test
    public void test_deliveryStatusMatchInput_returnsTrue() {
        // PENDING status
        DeliveryStatusMatchInputPredicate predicate = new DeliveryStatusMatchInputPredicate(Status.PENDING);
        assertTrue(predicate.test(new DeliveryBuilder().withStatus(Status.PENDING).build()));

        // CANCELLED STATUS
        predicate = new DeliveryStatusMatchInputPredicate(Status.CANCELLED);
        assertTrue(predicate.test(new DeliveryBuilder().withStatus(Status.CANCELLED).build()));

        // DELIVERED STATUS
        predicate = new DeliveryStatusMatchInputPredicate(Status.DELIVERED);
        assertTrue(predicate.test(new DeliveryBuilder().withStatus(Status.DELIVERED).build()));
    }

    @Test
    public void test_deliveryStatusMatchInput_returnsFalse() {
        // PENDING status mismatch
        DeliveryStatusMatchInputPredicate predicate = new DeliveryStatusMatchInputPredicate(Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withStatus(Status.CANCELLED).build()));

        // CANCELLED STATUS mismatch
        predicate = new DeliveryStatusMatchInputPredicate(Status.CANCELLED);
        assertFalse(predicate.test(new DeliveryBuilder().withStatus(Status.PENDING).build()));

        // DELIVERED STATUS mismatch
        predicate = new DeliveryStatusMatchInputPredicate(Status.DELIVERED);
        assertFalse(predicate.test(new DeliveryBuilder().withStatus(Status.PENDING).build()));
    }

    @Test
    public void toStringMethod() {
        Status deliveryStatus = Status.DELIVERED;
        DeliveryStatusMatchInputPredicate predicate = new DeliveryStatusMatchInputPredicate(deliveryStatus);

        String expected = DeliveryStatusMatchInputPredicate.class.getCanonicalName()
                + "{deliveryStatus=" + deliveryStatus + "}";
        assertEquals(expected, predicate.toString());
    }
}
