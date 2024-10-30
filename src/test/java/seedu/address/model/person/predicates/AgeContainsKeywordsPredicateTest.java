package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AgeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("11");
        List<String> secondPredicateKeywordList = Arrays.asList("11", "12");

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
                Collections.singletonList("11"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));

        // Only one matching keyword
        predicate = new AgeContainsKeywordsPredicate(Arrays.asList("11", "12"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));
    }

    @Test
    public void test_ageDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAge("11").build()));

        // Non-matching keyword
        predicate = new AgeContainsKeywordsPredicate(Arrays.asList("12", "13"));
        assertFalse(predicate.test(new PersonBuilder().withAge("11").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("1", "2-3");
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(keywords);

        String expected = AgeContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
