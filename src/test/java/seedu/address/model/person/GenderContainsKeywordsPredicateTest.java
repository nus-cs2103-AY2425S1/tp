package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class GenderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Male");
        List<String> secondPredicateKeywordList = Arrays.asList("Male", "Female");

        GenderContainsKeywordsPredicate firstPredicate =
                new GenderContainsKeywordsPredicate(firstPredicateKeywordList);
        GenderContainsKeywordsPredicate secondPredicate =
                new GenderContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GenderContainsKeywordsPredicate firstPredicateCopy =
                new GenderContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_genderContainsKeywords_returnsTrue() {
        // One keyword
        GenderContainsKeywordsPredicate predicate =
                new GenderContainsKeywordsPredicate(Collections.singletonList("Male"));
        assertTrue(predicate.test(new PersonBuilder().withGender("male").build()));

        // Multiple matching keywords
        predicate = new GenderContainsKeywordsPredicate(Arrays.asList("Male", "Female"));
        assertTrue(predicate.test(new PersonBuilder().withGender("female").build()));

        // Mixed-case keywords
        predicate = new GenderContainsKeywordsPredicate(Arrays.asList("male", "FEMALE"));
        assertTrue(predicate.test(new PersonBuilder().withGender("female").build()));
    }

    @Test
    public void test_genderDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GenderContainsKeywordsPredicate predicate = new GenderContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withGender("male").build()));

        // Non-matching keyword
        predicate = new GenderContainsKeywordsPredicate(Arrays.asList("NonBinary"));
        assertFalse(predicate.test(new PersonBuilder().withGender("female").build()));

        // Keywords match name, but not gender
        predicate = new GenderContainsKeywordsPredicate(Arrays.asList("Male", "Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withGender("female").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Male", "Female");
        GenderContainsKeywordsPredicate predicate = new GenderContainsKeywordsPredicate(keywords);

        String expected = GenderContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
