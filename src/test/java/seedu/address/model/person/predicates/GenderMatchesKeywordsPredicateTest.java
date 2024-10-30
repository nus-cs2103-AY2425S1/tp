package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class GenderMatchesKeywordsPredicateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GenderMatchesKeywordsPredicate(null));
    }

    @Test
    public void isValidInput() {
        // null gender
        assertThrows(NullPointerException.class, () -> GenderMatchesKeywordsPredicate.isValidInput(null));

        // invalid gender
        assertFalse(GenderMatchesKeywordsPredicate.isValidInput("")); // empty string
        assertFalse(GenderMatchesKeywordsPredicate.isValidInput(" ")); // spaces only
        assertFalse(GenderMatchesKeywordsPredicate.isValidInput("G")); // invalid character
        assertFalse(GenderMatchesKeywordsPredicate.isValidInput("Female")); // invalid characters

        // valid gender
        assertTrue(GenderMatchesKeywordsPredicate.isValidInput("F"));
        assertTrue(GenderMatchesKeywordsPredicate.isValidInput("M"));
        assertTrue(GenderMatchesKeywordsPredicate.isValidInput("f"));
        assertTrue(GenderMatchesKeywordsPredicate.isValidInput("m"));
    }

    @Test
    public void equals() {
        Set<String> firstPredicateKeywordList = Set.of("m");
        Set<String> secondPredicateKeywordList = Set.of("M", "F");

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
                Set.of("m"));
        assertTrue(predicate.test(new PersonBuilder().withGender("M").build()));

        // Multiple keywords
        predicate = new GenderMatchesKeywordsPredicate(Set.of("m", "M"));
        assertTrue(predicate.test(new PersonBuilder().withGender("M").build()));

        // Only one matching keyword
        predicate = new GenderMatchesKeywordsPredicate(Set.of("m", "F"));
        assertTrue(predicate.test(new PersonBuilder().withGender("M").build()));
    }

    @Test
    public void test_genderDoesNotMatchKeywords_returnsFalse() {
        // Zero keywords
        GenderMatchesKeywordsPredicate predicate = new GenderMatchesKeywordsPredicate(Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withGender("M").build()));

        // Non-matching keyword
        predicate = new GenderMatchesKeywordsPredicate(Set.of("f"));
        assertFalse(predicate.test(new PersonBuilder().withGender("M").build()));
    }

    @Test
    public void toStringMethod() {
        Set<String> keywords = Set.of("M", "F");
        GenderMatchesKeywordsPredicate predicate = new GenderMatchesKeywordsPredicate(keywords);

        String expected = GenderMatchesKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
