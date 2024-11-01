package seedu.address.model.types.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.types.common.DateTimeUtil.DATE_TIME_FORMATTER;
import static seedu.address.model.types.common.DateTimeUtil.getCurrentDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventInSchedulePredicateTest {

    @Test
    public void equals() {
        EventInSchedulePredicate firstPredicate = new EventInSchedulePredicate(1);
        EventInSchedulePredicate secondPredicate = new EventInSchedulePredicate(2);
        EventInSchedulePredicate thirdPredicate = new EventInSchedulePredicate(new DateTime("2024-10-15 14:30"));
        EventInSchedulePredicate fourthPredicate = new EventInSchedulePredicate(new DateTime("2024-10-16 15:30"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventInSchedulePredicate firstPredicateCopy =
                new EventInSchedulePredicate(1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        EventInSchedulePredicate thirdPredicateCopy =
                new EventInSchedulePredicate(new DateTime("2024-10-15 14:30"));
        assertTrue(thirdPredicate.equals(thirdPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventInSchedule_returnsTrue() {
        // Plus one day
        EventInSchedulePredicate predicate = new EventInSchedulePredicate(1);
        assertTrue(predicate.test(new EventBuilder().withStartTime(currentTimeAdder(1, 0, 0)).build()));

        // Minus one day
        EventInSchedulePredicate predicate2 = new EventInSchedulePredicate(-1);
        assertTrue(predicate2.test(new EventBuilder().withStartTime(currentTimeAdder(-1, 0, 0)).build()));

        // Event at start of day
        EventInSchedulePredicate predicate3 = new EventInSchedulePredicate(new DateTime("2024-10-15 00:00"));
        assertTrue(predicate3.test(new EventBuilder().withStartTime("2024-10-15 00:00").build()));

        // Event at end of day
        EventInSchedulePredicate predicate4 = new EventInSchedulePredicate(new DateTime("2024-10-15 00:00"));
        assertTrue(predicate4.test(new EventBuilder().withStartTime("2024-10-15 23:59").build()));
    }

    @Test
    public void test_eventInSchedule_returnsFalse() {
        // Plus one day
        EventInSchedulePredicate predicate = new EventInSchedulePredicate(1);
        assertFalse(predicate.test(new EventBuilder().withStartTime(currentTimeAdder(0, 0, -1)).build()));

        // Minus one day
        EventInSchedulePredicate predicate2 = new EventInSchedulePredicate(-1);
        assertFalse(predicate2.test(new EventBuilder().withStartTime(currentTimeAdder(0, 0, 1)).build()));

        // Event at start of day
        EventInSchedulePredicate predicate3 = new EventInSchedulePredicate(new DateTime("2024-10-14 23:59"));
        assertFalse(predicate3.test(new EventBuilder().withStartTime("2024-10-15 00:00").build()));

        // Event at end of day
        EventInSchedulePredicate predicate4 = new EventInSchedulePredicate(new DateTime("2024-10-16 00:00"));
        assertFalse(predicate4.test(new EventBuilder().withStartTime("2024-10-15 23:59").build()));
    }

    private String currentTimeAdder(int days, int hours, int minutes) {
        return getCurrentDateTime().plusDays(days).plusHours(hours).plusMinutes(minutes).format(DATE_TIME_FORMATTER);
    }
}
