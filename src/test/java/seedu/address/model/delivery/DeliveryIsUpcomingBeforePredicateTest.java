package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.prepareBeforePredicate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryIsUpcomingBeforePredicateTest {
    @Test
    public void equals() {
        DateTime firstPredicateDateTime = new DateTime("19-12-2022 16:00");
        Status firstPredicateStatus = Status.PENDING;
        DeliveryIsUpcomingBeforePredicate firstPredicate = prepareBeforePredicate("19-12-2022 16:00",
                Status.PENDING);
        DeliveryIsUpcomingBeforePredicate secondPredicate = prepareBeforePredicate("18-06-2023 17:00",
                Status.DELIVERED);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeliveryIsUpcomingBeforePredicate firstPredicateCopy = new
                DeliveryIsUpcomingBeforePredicate(firstPredicateDateTime, firstPredicateStatus);

        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("abc"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_deliveryIsUpcomingBeforePredicate_returnsTrue() {
        // pending and before specified date
        DeliveryIsUpcomingBeforePredicate predicate = new DeliveryIsUpcomingBeforePredicate(
                new DateTime("05-05-2024 16:15"), Status.PENDING);
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("05-04-2024 16:15")
                .withStatus(Status.PENDING).build()));
    }

    @Test
    public void test_deliveryIsUpcomingBeforePredicate_returnsFalse() {
        // pending and after specified date
        DeliveryIsUpcomingBeforePredicate predicate = new DeliveryIsUpcomingBeforePredicate(
                new DateTime("05-05-2024 16:15"), Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-07-2024 16:15")
                .withStatus(Status.PENDING).build()));

        // pending and same date
        predicate = new DeliveryIsUpcomingBeforePredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-05-2024 16:15")
                .withStatus(Status.PENDING).build()));

        // cancelled and before specified date
        predicate = new DeliveryIsUpcomingBeforePredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-02-2024 16:15")
                .withStatus(Status.CANCELLED).build()));

        // delivered and before specified date
        predicate = new DeliveryIsUpcomingBeforePredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-02-2024 16:15")
                .withStatus(Status.DELIVERED).build()));
    }

    @Test
    public void toStringMethod() {
        DateTime predicateDateTime = new DateTime("19-12-2022 16:00");
        Status predicateStatus = Status.PENDING;
        DeliveryIsUpcomingBeforePredicate predicate = new DeliveryIsUpcomingBeforePredicate(predicateDateTime,
                predicateStatus);

        String expected = DeliveryIsUpcomingBeforePredicate.class.getCanonicalName() + "{completionDateTime="
                + predicateDateTime + ", deliveryStatus=" + predicateStatus + "}";
        assertEquals(expected, predicate.toString());
    }
}
