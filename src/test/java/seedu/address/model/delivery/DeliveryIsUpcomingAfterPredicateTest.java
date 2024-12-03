package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.prepareAfterPredicate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryIsUpcomingAfterPredicateTest {
    @Test
    public void equals() {
        DateTime firstPredicateDateTime = new DateTime("19-12-2022 16:00");
        Status firstPredicateStatus = Status.PENDING;

        DeliveryIsUpcomingAfterPredicate firstPredicate = prepareAfterPredicate("19-12-2022 16:00", Status.PENDING);
        DeliveryIsUpcomingAfterPredicate secondPredicate = prepareAfterPredicate("18-06-2023 17:00", Status.DELIVERED);
        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeliveryIsUpcomingAfterPredicate firstPredicateCopy = new
                DeliveryIsUpcomingAfterPredicate(firstPredicateDateTime, firstPredicateStatus);

        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("abc"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_deliveryIsUpcomingAfterPredicate_returnsTrue() {
        // pending and after specified date
        DeliveryIsUpcomingAfterPredicate predicate = new DeliveryIsUpcomingAfterPredicate(
                new DateTime("05-05-2024 16:15"), Status.PENDING);
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("05-06-2024 16:15")
                .withStatus(Status.PENDING).build()));
    }

    @Test
    public void test_deliveryIsUpcomingAfterPredicate_returnsFalse() {
        // pending and before specified date
        DeliveryIsUpcomingAfterPredicate predicate = new DeliveryIsUpcomingAfterPredicate(
                new DateTime("05-08-2024 16:15"), Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-07-2024 16:15")
                .withStatus(Status.PENDING).build()));

        // pending and same date
        predicate = new DeliveryIsUpcomingAfterPredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-05-2024 16:15")
                .withStatus(Status.PENDING).build()));

        // cancelled and after specified date
        predicate = new DeliveryIsUpcomingAfterPredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-10-2024 16:15")
                .withStatus(Status.CANCELLED).build()));

        // delivered and after specified date
        predicate = new DeliveryIsUpcomingAfterPredicate(new DateTime("05-05-2024 16:15"),
                Status.PENDING);
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("05-12-2024 16:15")
                .withStatus(Status.DELIVERED).build()));
    }

    @Test
    public void toStringMethod() {
        DateTime predicateDateTime = new DateTime("19-12-2022 16:00");
        Status predicateStatus = Status.PENDING;
        DeliveryIsUpcomingAfterPredicate predicate = new DeliveryIsUpcomingAfterPredicate(predicateDateTime,
                predicateStatus);

        String expected = DeliveryIsUpcomingAfterPredicate.class.getCanonicalName() + "{completionDateTime="
                + predicateDateTime + ", deliveryStatus=" + predicateStatus + "}";
        assertEquals(expected, predicate.toString());
    }
}
