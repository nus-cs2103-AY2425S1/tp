package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.VendorBuilder;

public class RelationContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("H");
        List<String> secondPredicateKeywordList = Arrays.asList("H", "W");

        RelationContainsKeywordsPredicate firstPredicate =
                new RelationContainsKeywordsPredicate(firstPredicateKeywordList);
        RelationContainsKeywordsPredicate secondPredicate =
                new RelationContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RelationContainsKeywordsPredicate firstPredicateCopy =
                new RelationContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different relation -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_relationContainsKeywords_returnsTrue() {
        // One keyword
        RelationContainsKeywordsPredicate predicate =
                new RelationContainsKeywordsPredicate(Collections.singletonList("H"));
        assertTrue(predicate.test(new GuestBuilder().withRelation("H").build()));

        // Only one matching keyword
        predicate = new RelationContainsKeywordsPredicate(Arrays.asList("H", "W"));
        assertTrue(predicate.test(new GuestBuilder().withRelation("H").build()));
    }

    @Test
    public void test_relationDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RelationContainsKeywordsPredicate predicate = new RelationContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withRelation("H").build()));

        // Non-matching keyword
        predicate = new RelationContainsKeywordsPredicate(Arrays.asList("H"));
        assertFalse(predicate.test(new GuestBuilder().withRelation("W").build()));

        // Keywords match name, phone, email and address, but does not match relation
        predicate = new RelationContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withRelation("H").build()));

        // Non-guest
        predicate = new RelationContainsKeywordsPredicate(Arrays.asList("H"));
        assertFalse(predicate.test(new VendorBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("H");
        RelationContainsKeywordsPredicate predicate = new RelationContainsKeywordsPredicate(keywords);

        String expected = RelationContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
