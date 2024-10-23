package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TelContainsNumberPredicateTest {
    @Test
    public void equals() {
        String firstPredicateNumber = "12345678";
        String secondPredicateNumber = "87654321";

        TelContainsNumberPredicate firstPredicate = new TelContainsNumberPredicate(firstPredicateNumber);
        TelContainsNumberPredicate secondPredicate = new TelContainsNumberPredicate(secondPredicateNumber);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TelContainsNumberPredicate firstPredicateCopy = new TelContainsNumberPredicate(firstPredicateNumber);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // Full number
        TelContainsNumberPredicate predicate = new TelContainsNumberPredicate("45362859");
        assertTrue(predicate.test(new PersonBuilder().withPhone("45362859").build()));

        // Partial number
        predicate = new TelContainsNumberPredicate("4536");
        assertTrue(predicate.test(new PersonBuilder().withPhone("45362859").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        TelContainsNumberPredicate predicate = new TelContainsNumberPredicate("88888888");
        assertFalse(predicate.test(new PersonBuilder().withPhone("12341234").build()));
    }

    @Test
    public void toStringMethod() {
        String searchNumber = "12345678";
        TelContainsNumberPredicate predicate = new TelContainsNumberPredicate(searchNumber);

        String expected = TelContainsNumberPredicate.class.getCanonicalName() + "{search telephone number="
                + searchNumber + "}";
        assertEquals(expected, predicate.toString());
    }
}
