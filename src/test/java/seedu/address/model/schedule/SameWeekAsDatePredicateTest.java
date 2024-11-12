package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MeetingBuilder;

public class SameWeekAsDatePredicateTest {

    private final SameWeekAsDatePredicate firstPredicate = new SameWeekAsDatePredicate(LocalDate
            .parse("2024-10-10"));
    private final MeetingBuilder meetingBuilder = new MeetingBuilder();

    @Test
    public void equals_true() {
        // same object -> returns true
        SameWeekAsDatePredicate secondPredicate = firstPredicate;
        assertTrue(firstPredicate.equals(secondPredicate));
        assertTrue(firstPredicate.equals(firstPredicate));
        // same values -> returns true
        SameWeekAsDatePredicate firstPredicateCopy = new SameWeekAsDatePredicate(LocalDate
                .parse("2024-10-10"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals_false() {
        // different types -> returns false
        assertFalse(firstPredicate.equals(1));
        // null -> returns false
        assertFalse(firstPredicate.equals(null));
        // different date -> returns false
        SameWeekAsDatePredicate differentPredicate = new SameWeekAsDatePredicate(LocalDate
                .parse("2024-10-11"));
        assertFalse(firstPredicate.equals(differentPredicate));
        // different year -> returns false
        SameWeekAsDatePredicate differentYearPredicate = new SameWeekAsDatePredicate(LocalDate
                .parse("2050-10-11"));
        assertFalse(firstPredicate.equals(differentYearPredicate));
    }

    @Test
    public void toString_equals() {
        SameWeekAsDatePredicate predicate = new SameWeekAsDatePredicate(LocalDate
                .parse("2024-10-10"));
        assertTrue(predicate.toString()
                .equals("seedu.address.model.schedule.SameWeekAsDatePredicate{date=2024-10-10}"));
    }

    @Test
    public void toString_notEquals() {
        SameWeekAsDatePredicate predicate = new SameWeekAsDatePredicate(LocalDate
                .parse("2024-10-10"));
        assertFalse(predicate.toString()
                .equals("seedu.address.model.schedule.SameWeekAsDatePredicate{date=2024-10-11}"));
    }

    @Test
    public void dateOfWeek() {
        // sunday to following saturday
        assertTrue(firstPredicate.getStartDateOfWeek().equals(LocalDate.parse("2024-10-06")));
        assertTrue(firstPredicate.getLastDateOfWeek().equals(LocalDate.parse("2024-10-12")));
    }

    @Test
    public void test_true() {
        Meeting meetingSameWeek = meetingBuilder.withDate(LocalDate.parse("2024-10-10")).build();
        assertTrue(firstPredicate.test(meetingSameWeek));
        Meeting meetingSameWeek2 = meetingBuilder.withDate(LocalDate.parse("2024-10-06")).build();
        assertTrue(firstPredicate.test(meetingSameWeek2));
        Meeting meetingSameWeek3 = meetingBuilder.withDate(LocalDate.parse("2024-10-12")).build();
        assertTrue(firstPredicate.test(meetingSameWeek3));
    }

    @Test
    public void test_false() {
        Meeting meetingDifferentWeek = meetingBuilder.withDate(LocalDate
                .parse("2024-10-13")).build();
        assertFalse(firstPredicate.test(meetingDifferentWeek));
        Meeting meetingDifferentWeek2 = meetingBuilder.withDate(LocalDate
                .parse("2024-10-05")).build();
        assertFalse(firstPredicate.test(meetingDifferentWeek2));
    }
}
