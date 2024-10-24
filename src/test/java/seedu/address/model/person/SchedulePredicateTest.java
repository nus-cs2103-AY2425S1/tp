package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class SchedulePredicateTest {
    @Test
    public void equals() {
        SchedulePredicate firstPredicate = new SchedulePredicate(new Date("16 Feb 2024"));
        SchedulePredicate secondPredicate = new SchedulePredicate(new Date("16 Feb 2023"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SchedulePredicate firstPredicateCopy = new SchedulePredicate(new Date("16 Feb 2024"));
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
        SchedulePredicate predicate = new SchedulePredicate(new Date("16 January 2006"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withDate("16 January 2006").build()));
    }

    @Test
    public void test_nameContainsKeywords_returnsFalse() {
        // different date
        SchedulePredicate predicate = new SchedulePredicate(new Date("16 January 2006"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withDate("17 January 2006").build()));

        // no date
        predicate = new SchedulePredicate(new Date("16 January 2006"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void toStringMethod() {
        SchedulePredicate predicate = new SchedulePredicate(new Date("16 Feb 2024"));

        String expected = SchedulePredicate.class.getCanonicalName() + "{date=" + "16 Feb 2024" + "}";
        assertEquals(expected, predicate.toString());
    }

}
