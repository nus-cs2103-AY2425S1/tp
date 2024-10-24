package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.NameContainsPredicate;
import seedu.address.testutil.PersonBuilder;

public class NameContainsPredicateTest {
    @Test
    public void equals() {

        NameContainsPredicate firstPredicate = new NameContainsPredicate("linkes");
        NameContainsPredicate secondPredicate = new NameContainsPredicate("varun");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsPredicate firstPredicateCopy = new NameContainsPredicate("linkes");
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
        // One keyword (case insensitive)
        NameContainsPredicate predicate = new NameContainsPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial matching keyword
        predicate = new NameContainsPredicate("B");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new NameContainsPredicate("aLIce");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Bad keyword
        NameContainsPredicate predicate = new NameContainsPredicate("$$$$$$$$$$$$");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsPredicate("Xenon");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

    }

    @Test
    public void toStringMethod() {
        NameContainsPredicate predicate = new NameContainsPredicate("testing 1 2 3");

        String expected = NameContainsPredicate.class.getCanonicalName() + "{keyword=testing 1 2 3}";
        assertEquals(expected, predicate.toString());
    }
}
