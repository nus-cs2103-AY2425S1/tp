package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryDatePredicateTest {

    @Test
    public void equals() {
        DateTime firstPredicateDate = new DateTime("10-10-2024 16:00");
        DateTime secondPredicateDate = new DateTime("11-10-2024 10:00");

        DeliveryDatePredicate firstPredicate = new DeliveryDatePredicate(firstPredicateDate);
        DeliveryDatePredicate secondPredicate = new DeliveryDatePredicate(secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeliveryDatePredicate firstPredicateCopy = new DeliveryDatePredicate(firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_deliveryDateMatchesTargetDate_returnsTrue() {
        // Exact matching date and time
        DeliveryDatePredicate predicate = new DeliveryDatePredicate(new DateTime("10-10-2024 16:00"));
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("10-10-2024 16:00").build()));

        // Same date, different time (considered matching based on the logic)
        predicate = new DeliveryDatePredicate(new DateTime("11-10-2024 10:00"));
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("11-10-2024 10:00").build()));
    }

    @Test
    public void test_deliveryDateDoesNotMatchTargetDate_returnsFalse() {
        // Different date
        DeliveryDatePredicate predicate = new DeliveryDatePredicate(new DateTime("10-10-2024 16:00"));
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("11-10-2024 10:00").build()));
    }

    @Test
    public void test_toStringMethod() {
        DateTime targetDate = new DateTime("10-10-2024 16:00");
        DeliveryDatePredicate predicate = new DeliveryDatePredicate(targetDate);

        String expected = DeliveryDatePredicate.class.getCanonicalName() + "{targetDate=" + targetDate + "}";
        assertEquals(expected, predicate.toString());
    }
}
