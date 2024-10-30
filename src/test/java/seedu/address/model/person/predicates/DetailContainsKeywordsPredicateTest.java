package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class DetailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<String> firstPredicateKeywordList = Set.of("first");
        Set<String> secondPredicateKeywordList = Set.of("first", "second");

        DetailContainsKeywordsPredicate firstPredicate = new DetailContainsKeywordsPredicate(
                firstPredicateKeywordList);
        DetailContainsKeywordsPredicate secondPredicate = new DetailContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DetailContainsKeywordsPredicate firstPredicateCopy = new DetailContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_detailsContainsKeywords_returnsTrue() {
        // One keyword
        DetailContainsKeywordsPredicate predicate = new DetailContainsKeywordsPredicate(
                Set.of("details"));
        assertTrue(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));

        // Multiple keywords
        predicate = new DetailContainsKeywordsPredicate(Set.of("this", "details"));
        assertTrue(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));

        // Only one matching keyword
        predicate = new DetailContainsKeywordsPredicate(Set.of("this", "nonsense"));
        assertTrue(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));

        // Mixed-case keywords
        predicate = new DetailContainsKeywordsPredicate(Set.of("tHiS", "DEtaIlS"));
        assertTrue(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));
    }

    @Test
    public void test_detailsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DetailContainsKeywordsPredicate predicate = new DetailContainsKeywordsPredicate(Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));

        // Non-matching keyword
        predicate = new DetailContainsKeywordsPredicate(Set.of("detail"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void toStringMethod() {
        Set<String> keywords = Set.of("keyword1", "keyword2");
        DetailContainsKeywordsPredicate predicate = new DetailContainsKeywordsPredicate(keywords);

        String expected = DetailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
