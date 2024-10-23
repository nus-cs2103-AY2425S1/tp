package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryIsUpcomingPredicateTest {
    @Test
    public void equals() {
        DateTime firstPredicateDateTime = new DateTime("19-12-2022 16:00");
        DateTime secondPredicateDateTime = new DateTime("18-06-2023 17:00");
        Status firstPredicateStatus = Status.PENDING;
        Status secondPredicateStatus = Status.DELIVERED;

        DeliveryIsUpcomingPredicate firstPredicate = new DeliveryIsUpcomingPredicate(firstPredicateDateTime,
                firstPredicateStatus);
        DeliveryIsUpcomingPredicate secondPredicate = new DeliveryIsUpcomingPredicate(secondPredicateDateTime,
                secondPredicateStatus);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeliveryIsUpcomingPredicate firstPredicateCopy = new DeliveryIsUpcomingPredicate(firstPredicateDateTime,
                firstPredicateStatus);

        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("abc"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_deliveryIsUpcomingPredicate_returnsTrue() {
        // pending and before specified date
        DeliveryIsUpcomingPredicate predicate = new DeliveryIsUpcomingPredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("05-04-2024 16:15")
                .withStatus(Status.PENDING).build()));
    }

    @Test
    public void test_deliveryIsUpcomingPredicate_returnsFalse() {
        // pending and after specified date
        DeliveryIsUpcomingPredicate predicate = new DeliveryIsUpcomingPredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-07-2024 16:15")
                .withStatus(Status.PENDING).build()));

        // pending and same date
        predicate = new DeliveryIsUpcomingPredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-05-2024 16:15")
                .withStatus(Status.PENDING).build()));

        // cancelled and before specified date
        predicate = new DeliveryIsUpcomingPredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-02-2024 16:15")
                .withStatus(Status.CANCELLED).build()));

        // delivered and before specified date
        predicate = new DeliveryIsUpcomingPredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-02-2024 16:15")
                .withStatus(Status.DELIVERED).build()));
    }

    @Test
    public void toStringMethod() {
        DateTime predicateDateTime = new DateTime("19-12-2022 16:00");
        Status predicateStatus = Status.PENDING;
        DeliveryIsUpcomingPredicate predicate = new DeliveryIsUpcomingPredicate(predicateDateTime,
                predicateStatus);

        String expected = DeliveryIsUpcomingPredicate.class.getCanonicalName() + "{completionDateTime="
                + predicateDateTime + ", deliveryStatus=" + predicateStatus + "}";
        assertEquals(expected, predicate.toString());
    }
}
