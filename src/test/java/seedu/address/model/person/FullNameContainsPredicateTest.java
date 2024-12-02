package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FullNameContainsPredicateTest {

    @Test
    public void test_fullNameContainsKeyword_returnsTrue() {
        FullNameContainsPredicate predicate = new FullNameContainsPredicate("Alice");

        // Full name contains keyword
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Different casing
        assertTrue(predicate.test(new PersonBuilder().withName("alice bob").build()));

        // Keyword as part of full name
        assertTrue(predicate.test(new PersonBuilder().withName("Malice Johnson").build()));
    }

    @Test
    public void test_fullNameDoesNotContainKeyword_returnsFalse() {
        FullNameContainsPredicate predicate = new FullNameContainsPredicate("Alice");

        // Full name does not contain keyword
        assertFalse(predicate.test(new PersonBuilder().withName("Bob Charlie").build()));

        // Partial match but not contained as a whole
        assertFalse(predicate.test(new PersonBuilder().withName("Alicia").build()));
    }

    @Test
    public void equals() {
        FullNameContainsPredicate firstPredicate = new FullNameContainsPredicate("Alice");
        FullNameContainsPredicate secondPredicate = new FullNameContainsPredicate("Bob");

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same value -> returns true
        FullNameContainsPredicate firstPredicateCopy = new FullNameContainsPredicate("Alice");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // Different types -> returns false
        assertFalse(firstPredicate.equals("Alice"));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));
    }
}
