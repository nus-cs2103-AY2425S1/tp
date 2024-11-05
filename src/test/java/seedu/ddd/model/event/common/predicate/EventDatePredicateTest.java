package seedu.ddd.model.event.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.event.common.Date;
import seedu.ddd.testutil.event.EventBuilder;

public class EventDatePredicateTest {
    private String validDate1 = "2000-01-03";
    private String validDate2 = "3 Jan 2000";
    private String validDate3 = "01/03/2000";
    private String differentValidDate = "2000-03-01";
    @Test
    public void equals() {
        Date firstDate = new Date(validDate1);
        Date secondDate = new Date(validDate2);
        Date thirdDate = new Date(validDate3);
        Date differentDate = new Date(differentValidDate);

        EventDatePredicate firstPredicate = new EventDatePredicate(firstDate);
        EventDatePredicate secondPredicate = new EventDatePredicate(secondDate);
        EventDatePredicate thirdPredicate = new EventDatePredicate(thirdDate);
        EventDatePredicate fourthPredicate = new EventDatePredicate(differentDate);

        // same object -> return true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same date in different formats -> returns true
        EventDatePredicate firstPredicateCopy = new EventDatePredicate(firstDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));
        assertTrue(firstPredicate.equals(secondPredicate));
        assertTrue(firstPredicate.equals(thirdPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different date -> returns false
        assertFalse(firstPredicate.equals(fourthPredicate));
    }
    @Test
    public void test_eventContainsDate_returnsTrue() {
        // Test the first date format (yyyy-MM-dd)
        EventDatePredicate predicate = new EventDatePredicate(new Date(validDate1));
        assertTrue(predicate.test(new EventBuilder().withDate(validDate1).build()));

        // Test the second date format (d MMM YYYY)
        predicate = new EventDatePredicate(new Date(validDate2));
        assertTrue(predicate.test(new EventBuilder().withDate(validDate1).build()));

        // Test the third date format (MM/dd/yyyy)
        predicate = new EventDatePredicate(new Date(validDate3));
        assertTrue(predicate.test(new EventBuilder().withDate(validDate1).build()));
    }
    @Test
    public void test_eventDoesNotContainsDate_returnsFalse() {
        EventDatePredicate predicate = new EventDatePredicate(new Date(validDate1));
        assertFalse(predicate.test(new EventBuilder().withDate(differentValidDate).build()));

        predicate = new EventDatePredicate(new Date(differentValidDate));
        assertFalse(predicate.test(new EventBuilder().withDate(validDate1).build()));
    }
    @Test
    public void toStringMethod() {
        Date date = new Date(validDate1);
        EventDatePredicate predicate = new EventDatePredicate(date);

        String expected = EventDatePredicate.class.getCanonicalName() + "{Date=" + date + "}";
        assertEquals(expected, predicate.toString());
    }
}
