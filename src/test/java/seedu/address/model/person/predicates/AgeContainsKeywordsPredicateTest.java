package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AgeContainsKeywordsPredicateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AgeContainsKeywordsPredicate(null));
    }

    @Test
    public void constructor_invalidAgeKeyword_throwsIllegalArgumentException() {
        Set<String> invalidAgeKeywordSet = Set.of("1", "invalid");
        assertThrows(IllegalArgumentException.class, () -> new AgeContainsKeywordsPredicate(invalidAgeKeywordSet));
    }

    @Test
    public void isValidInput() {
        // null age
        assertThrows(NullPointerException.class, () -> AgeContainsKeywordsPredicate.isValidInput(null));

        // invalid age
        assertFalse(AgeContainsKeywordsPredicate.isValidInput("")); // empty string
        assertFalse(AgeContainsKeywordsPredicate.isValidInput(" ")); // spaces only
        assertFalse(AgeContainsKeywordsPredicate.isValidInput("one")); // non-numeric
        assertFalse(AgeContainsKeywordsPredicate.isValidInput("0.00")); // non-integer
        assertFalse(AgeContainsKeywordsPredicate.isValidInput("1 7")); // space within digits
        assertFalse(AgeContainsKeywordsPredicate.isValidInput("-100")); // negative
        assertFalse(AgeContainsKeywordsPredicate.isValidInput("1 - 7")); // space within range
        assertFalse(AgeContainsKeywordsPredicate.isValidInput("1 -7")); // space within range
        assertFalse(AgeContainsKeywordsPredicate.isValidInput("1- 7")); // space within range

        // valid age
        assertTrue(AgeContainsKeywordsPredicate.isValidInput("27")); // between lower and upper limit
        assertTrue(AgeContainsKeywordsPredicate.isValidInput("1-4")); // a range
        assertTrue(AgeContainsKeywordsPredicate.isValidInput("4-1")); // reverse order range
    }

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
    public void test_ageContainsSingleKeywords_returnsTrue() {
        // One keyword
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(
                Set.of("11"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));

        // Only one matching keyword
        predicate = new AgeContainsKeywordsPredicate(Set.of("11", "12"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));
    }

    @Test
    public void test_ageDoesNotContainSingleKeywords_returnsFalse() {
        // Zero keywords
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(
                Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withAge("11").build()));

        // Non-matching keyword
        predicate = new AgeContainsKeywordsPredicate(Set.of("12", "13"));
        assertFalse(predicate.test(new PersonBuilder().withAge("11").build()));
    }

    @Test
    public void test_ageContainsKeywordRange_returnsTrue() {
        // age in range
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(
                Set.of("10-12"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));

        // age in range + matching single keyword
        predicate = new AgeContainsKeywordsPredicate(Set.of("11", "10-12"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));

        // age in range + non-matching single keyword
        predicate = new AgeContainsKeywordsPredicate(Set.of("14", "10-12"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));

        // age in multi overlapping keyword range
        predicate = new AgeContainsKeywordsPredicate(Set.of("9-13", "10-16"));
        assertTrue(predicate.test(new PersonBuilder().withAge("11").build()));
    }

    @Test
    public void test_ageDoesNotContainKeywordRange_returnsFalse() {
        // Non-matching single keyword range
        AgeContainsKeywordsPredicate predicate = new AgeContainsKeywordsPredicate(
                Set.of("15-20"));
        assertFalse(predicate.test(new PersonBuilder().withAge("11").build()));

        // Non-matching multi keyword range
        predicate = new AgeContainsKeywordsPredicate(
                Set.of("9-10", "12-14"));
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
