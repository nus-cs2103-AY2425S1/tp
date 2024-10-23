package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class DetailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

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
                Collections.singletonList("details"));
        assertTrue(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));

        // Multiple keywords
        predicate = new DetailContainsKeywordsPredicate(Arrays.asList("this", "details"));
        assertTrue(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));

        // Only one matching keyword
        predicate = new DetailContainsKeywordsPredicate(Arrays.asList("this", "nonsense"));
        assertTrue(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));

        // Mixed-case keywords
        predicate = new DetailContainsKeywordsPredicate(Arrays.asList("tHiS", "DEtaIlS"));
        assertTrue(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));
    }

    @Test
    public void test_detailsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DetailContainsKeywordsPredicate predicate = new DetailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withDetail("Sample details for this person").build()));

        // Non-matching keyword
        predicate = new DetailContainsKeywordsPredicate(Arrays.asList("detail"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        DetailContainsKeywordsPredicate predicate = new DetailContainsKeywordsPredicate(keywords);

        String expected = DetailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
