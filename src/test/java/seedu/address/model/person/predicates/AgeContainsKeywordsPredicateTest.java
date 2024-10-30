package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AgeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<String> firstPredicateKeywordList = Set.of("11");
        Set<String> secondPredicateKeywordList = Set.of("11", "12");

        AgeContainsKeywordsPredicate firstPredicate = new AgeContainsKeywordsPredicate(firstPredicateKeywordList);
        AgeContainsKeywordsPredicate secondPredicate = new AgeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AgeContainsKeywordsPredicate firstPredicateCopy = new AgeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ageContainsKeywords_returnsTrue() {
        // One keyword
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(
                Set.of("11"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));

        // Only one matching keyword
        predicate = new AgeContainsKeywordsPredicate(Set.of("11", "12"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));
    }

    @Test
    public void test_ageDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(
                Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withAge("11").build()));

        // Non-matching keyword
        predicate = new AgeContainsKeywordsPredicate(Set.of("12", "13"));
        assertFalse(predicate.test(new PersonBuilder().withAge("11").build()));
    }

    @Test
    public void toStringMethod() {
        Set<String> keywords = Set.of("1", "2-3");
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(keywords);

        String expected = AgeContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
