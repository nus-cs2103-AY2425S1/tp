package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class DatePredicateTest {

    @Test
    public void equals() {
        DatePredicate firstDatePredicate = new DatePredicate(
                new Date(LocalDateTime.of(2024, 11, 6, 12, 0)));
        DatePredicate secondDatePredicate = new DatePredicate(
                new Date(LocalDateTime.of(2024, 11, 7, 5, 0)));

        // same object -> returns true
        assertTrue(firstDatePredicate.equals(firstDatePredicate));

        // same values -> returns true
        DatePredicate firstDatePredicateCopy = new DatePredicate(
                new Date(LocalDateTime.of(2024, 11, 6, 12, 0)));
        assertTrue(firstDatePredicate.equals(firstDatePredicateCopy));

        // different types -> returns false
        assertFalse(firstDatePredicate.equals(1));

        // null -> returns false
        assertFalse(firstDatePredicate.equals(null));

        // different date -> returns false
        assertFalse(firstDatePredicate.equals(secondDatePredicate));
    }

    @Test
    public void test_datePresent_returnsTrue() {
        DatePredicate datePredicate = new DatePredicate(
                new Date(LocalDateTime.of(2024, 7, 7, 7, 0)));
        assertTrue(datePredicate.test(new PersonBuilder().withDate(
                LocalDateTime.of(2024, 7, 7, 7, 0)
        ).build()));
    }

    @Test
    public void test_dateAbsent_returnsFalse() {
        // different date and time
        DatePredicate datePredicate = new DatePredicate(
                new Date(LocalDateTime.of(2024, 7, 7, 7, 0)));
        assertFalse(datePredicate.test(new PersonBuilder().withDate(
                LocalDateTime.of(2023, 6, 6, 8, 0)
        ).build()));

        // no date and time
        assertFalse(datePredicate.test(new PersonBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        DatePredicate datePredicate = new DatePredicate(
                new Date(LocalDateTime.of(2024, 1, 2, 3, 0)));
        String expectedString = DatePredicate.class.getCanonicalName() + "{date=2/1/2024 0300}";
        assertEquals(expectedString, datePredicate.toString());
    }
}
