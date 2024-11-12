package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;


public class SchedulePredicateTest {
    @Test
    public void equals() {
        SchedulePredicate firstPredicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 16, 0, 0))
        );
        SchedulePredicate secondPredicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 18, 0, 0))
        );

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SchedulePredicate firstPredicateCopy = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 16, 0, 0))
        );
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        //same date
        SchedulePredicate predicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 16, 18, 45))
        );
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withDate(
                LocalDateTime.of(2023, 2, 16, 18, 45)
        ).build()));
    }

    @Test
    public void test_nameContainsKeywords_returnsFalse() {
        // different date
        SchedulePredicate predicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 16, 0, 0))
        );
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withDate(
                LocalDateTime.of(2023, 5, 30, 0, 0)).build()));

        // no date
        predicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 16, 0, 0)));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void toStringMethod() {
        SchedulePredicate predicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 16, 0, 0)));

        String expected = SchedulePredicate.class.getCanonicalName() + "{date=" + "2023-02-16" + "}";
        assertEquals(expected, predicate.toString());
    }

}
