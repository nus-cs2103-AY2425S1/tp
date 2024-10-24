package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class LevelContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        LevelContainsKeywordsPredicate firstPredicate = new LevelContainsKeywordsPredicate(firstPredicateKeywordList);
        LevelContainsKeywordsPredicate secondPredicate = new LevelContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LevelContainsKeywordsPredicate firstPredicateCopy = new LevelContainsKeywordsPredicate(
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
    public void test_levelContainsKeywords_returnsTrue() {
        // One keyword
        LevelContainsKeywordsPredicate predicate = new LevelContainsKeywordsPredicate(
                Collections.singletonList("S1 EXPRESS"));
        assertTrue(predicate.test(new PersonBuilder().withLevel("S1 EXPRESS").build()));

        // Multiple keywords
        predicate = new LevelContainsKeywordsPredicate(Arrays.asList("S1 EXPRESS", "S2 EXPRESS"));
        assertTrue(predicate.test(new PersonBuilder().withLevel("S1 EXPRESS").build()));
    }

    @Test
    public void test_levelDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        LevelContainsKeywordsPredicate predicate = new LevelContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withLevel("S1 EXPRESS").build()));

        // Non-matching keyword
        predicate = new LevelContainsKeywordsPredicate(Arrays.asList("S2 NA"));
        assertFalse(predicate.test(new PersonBuilder().withLevel("S1 NA").build()));

        // Keywords match phone and address, but does not match level
        predicate = new LevelContainsKeywordsPredicate(Arrays.asList("12345", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withLevel("S1 NA").withPhone("12345")
                .withAddress("Main Street").build()));
    }
}
