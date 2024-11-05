package seedu.address.model.supplier.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.supplier.predicates.NameContainsKeywordPredicate;
import seedu.address.testutil.SupplierBuilder;

public class NameContainsKeywordPredicateTest {
    @Test
    public void equals() {

        NameContainsKeywordPredicate firstPredicate = new NameContainsKeywordPredicate("linkes");
        NameContainsKeywordPredicate secondPredicate = new NameContainsKeywordPredicate("varun");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordPredicate firstPredicateCopy = new NameContainsKeywordPredicate("linkes");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different supplier -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword (case insensitive)
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Alice");
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Partial matching keyword
        predicate = new NameContainsKeywordPredicate("B");
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordPredicate("aLIce");
        assertTrue(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Bad keyword
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("$$$$$$$$$$$$");
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordPredicate("Xenon");
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice Bob").build()));

    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("testing 1 2 3");

        String expected = NameContainsKeywordPredicate.class.getCanonicalName() + "{keyword=testing 1 2 3}";
        assertEquals(expected, predicate.toString());
    }
}
