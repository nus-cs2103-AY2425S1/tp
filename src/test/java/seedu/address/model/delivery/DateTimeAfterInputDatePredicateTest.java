package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DateTimeAfterInputDatePredicateTest {
    @Test
    public void equals() {
        DateTime firstPredicateDateTime = new DateTime("11-02-2023 23:59");
        DateTime secondPredicateDateTime = new DateTime("12-02-2023 00:00");
        DateTimeAfterInputDatePredicate firstPredicate = new DateTimeAfterInputDatePredicate(firstPredicateDateTime);
        DateTimeAfterInputDatePredicate secondPredicate =
                new DateTimeAfterInputDatePredicate(secondPredicateDateTime);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateTimeAfterInputDatePredicate firstPredicateCopy =
                new DateTimeAfterInputDatePredicate(firstPredicateDateTime);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("123"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateTimeAfterInputDatePredicate_returnsTrue() {
        DateTimeAfterInputDatePredicate predicate =
                new DateTimeAfterInputDatePredicate(new DateTime("31-03-2024 17:16"));
        // later by minutes
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("31-03-2024 17:19").build()));

        // later by hours
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("12-03-2024 14:15"));
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("12-03-2024 17:15").build()));

        // later by days
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("10-11-2022 17:15"));
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("19-11-2022 17:15").build()));

        // later by months
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("19-10-2012 17:35"));
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("19-12-2012 17:35").build()));
        // later by years
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("01-10-2022 07:35"));
        assertTrue(predicate.test(new DeliveryBuilder().withDeliveryTime("01-10-2024 07:35").build()));
    }

    @Test
    public void test_dateTimeAfterInputDatePredicate_returnsFalse() {
        DateTimeAfterInputDatePredicate predicate = new DateTimeAfterInputDatePredicate(
                new DateTime("14-03-2024 17:16"));
        // earlier by minutes
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("14-03-2024 17:14").build()));

        // earlier by hours
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("16-05-2022 12:15"));
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("16-05-2022 05:15").build()));

        // earlier by days
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("11-10-2021 17:15"));
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("09-10-2021 17:15").build()));

        // earlier by months
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("21-12-2015 18:35"));
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("21-03-2015 18:35").build()));
        // earlier by years
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("01-10-2024 07:35"));
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("01-10-2022 07:35").build()));
        // same date and time
        predicate = new DateTimeAfterInputDatePredicate(new DateTime("21-06-2024 02:35"));
        assertFalse(predicate.test(new DeliveryBuilder().withDeliveryTime("21-06-2024 02:35").build()));
    }

    @Test
    public void toStringMethod() {
        DateTime dateTime = new DateTime("18-06-2023 17:00");
        DateTimeAfterInputDatePredicate predicate = new DateTimeAfterInputDatePredicate(dateTime);
        String expected = DateTimeAfterInputDatePredicate.class.getCanonicalName() + "{completionDateTime="
                + dateTime + "}";
        assertEquals(expected, predicate.toString());
    }
}
