package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalDeliveries;

public class DeliveryStatusPredicateTest {

    @Test
    public void test_statusMatches_returnsTrue() {
        Delivery delivery = TypicalDeliveries.BREAD; // Status is PENDING
        DeliveryStatusPredicate predicate = new DeliveryStatusPredicate(Status.PENDING);

        assertTrue(predicate.test(delivery));
    }

    @Test
    public void test_statusDoesNotMatch_returnsFalse() {
        Delivery delivery = TypicalDeliveries.DURIAN; // Status is DELIVERED
        DeliveryStatusPredicate predicate = new DeliveryStatusPredicate(Status.PENDING);

        assertFalse(predicate.test(delivery));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DeliveryStatusPredicate predicate = new DeliveryStatusPredicate(Status.DELIVERED);

        assertTrue(predicate.equals(predicate));
        assertTrue(predicate == predicate);
    }

    @Test
    public void equals_differentObjectSameStatus_returnsTrue() {
        DeliveryStatusPredicate predicate1 = new DeliveryStatusPredicate(Status.PENDING);
        DeliveryStatusPredicate predicate2 = new DeliveryStatusPredicate(Status.PENDING);

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_null_returnsFalse() {
        DeliveryStatusPredicate predicate = new DeliveryStatusPredicate(Status.PENDING);

        assertFalse(predicate.equals(null));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        DeliveryStatusPredicate predicate = new DeliveryStatusPredicate(Status.PENDING);
        Object otherObject = new Object();

        assertFalse(predicate.equals(otherObject));
    }

    @Test
    public void equals_differentStatus_returnsFalse() {
        DeliveryStatusPredicate predicate1 = new DeliveryStatusPredicate(Status.DELIVERED);
        DeliveryStatusPredicate predicate2 = new DeliveryStatusPredicate(Status.CANCELLED);

        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_toString() {
        DeliveryStatusPredicate predicate = new DeliveryStatusPredicate(Status.DELIVERED);

        String expectedString = "seedu.address.model.delivery.DeliveryStatusPredicate{targetStatus=DELIVERED}";
        assertEquals(expectedString, predicate.toString());
    }
}

