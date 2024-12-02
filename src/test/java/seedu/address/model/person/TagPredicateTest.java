package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagPredicateTest {

    @Test
    public void test_tagMatchesKeyword_returnsTrue() {
        TagPredicate predicate = new TagPredicate("N");

        // Exact match
        assertTrue(predicate.test(new PersonBuilder().withTag("N").build()));

        // Different casing, but still matches
        assertTrue(predicate.test(new PersonBuilder().withTag("n").build()));

        // Partial match but not the same (e.g., "B" does not match "BP")
        assertTrue(new TagPredicate("B").test(new PersonBuilder().withTag("BP").build()));

        // Test other valid tags
        assertTrue(new TagPredicate("BP").test(new PersonBuilder().withTag("BP").build()));
        assertTrue(new TagPredicate("BC").test(new PersonBuilder().withTag("BC").build()));
        assertTrue(new TagPredicate("TP").test(new PersonBuilder().withTag("TP").build()));
        assertTrue(new TagPredicate("TC").test(new PersonBuilder().withTag("TC").build()));
        assertTrue(new TagPredicate("A").test(new PersonBuilder().withTag("A").build()));
        assertTrue(new TagPredicate("R").test(new PersonBuilder().withTag("R").build()));
    }

    @Test
    public void test_tagDoesNotMatchKeyword_returnsFalse() {
        TagPredicate predicate = new TagPredicate("N");

        // Tag does not match the keyword
        assertFalse(predicate.test(new PersonBuilder().withTag("BP").build()));

    }

    @Test
    public void equals() {
        TagPredicate firstPredicate = new TagPredicate("N");
        TagPredicate secondPredicate = new TagPredicate("BP");

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same value -> returns true
        TagPredicate firstPredicateCopy = new TagPredicate("N");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // Different types -> returns false
        assertFalse(firstPredicate.equals("N"));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));
    }
}
