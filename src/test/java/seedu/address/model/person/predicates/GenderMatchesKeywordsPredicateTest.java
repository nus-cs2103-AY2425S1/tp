package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class GenderMatchesKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("m");
        List<String> secondPredicateKeywordList = Arrays.asList("M", "F");

        GenderMatchesKeywordsPredicate firstPredicate = new GenderMatchesKeywordsPredicate(firstPredicateKeywordList);
        GenderMatchesKeywordsPredicate secondPredicate = new GenderMatchesKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GenderMatchesKeywordsPredicate firstPredicateCopy = new GenderMatchesKeywordsPredicate(
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
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        GenderMatchesKeywordsPredicate predicate = new GenderMatchesKeywordsPredicate(
                Collections.singletonList("m"));
        assertTrue(predicate.test(new PersonBuilder().withGender("M").build()));

        // Multiple keywords
        predicate = new GenderMatchesKeywordsPredicate(Arrays.asList("m", "M"));
        assertTrue(predicate.test(new PersonBuilder().withGender("M").build()));

        // Only one matching keyword
        predicate = new GenderMatchesKeywordsPredicate(Arrays.asList("m", "F"));
        assertTrue(predicate.test(new PersonBuilder().withGender("M").build()));
    }

    @Test
    public void test_genderDoesNotMatchKeywords_returnsFalse() {
        // Zero keywords
        GenderMatchesKeywordsPredicate predicate = new GenderMatchesKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withGender("M").build()));

        // Non-matching keyword
        predicate = new GenderMatchesKeywordsPredicate(Arrays.asList("f"));
        assertFalse(predicate.test(new PersonBuilder().withGender("M").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("M", "F");
        GenderMatchesKeywordsPredicate predicate = new GenderMatchesKeywordsPredicate(keywords);

        String expected = GenderMatchesKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
