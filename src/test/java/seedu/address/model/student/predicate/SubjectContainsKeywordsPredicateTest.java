package seedu.address.model.student.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;

public class SubjectContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SubjectContainsKeywordsPredicate firstPredicate = new SubjectContainsKeywordsPredicate(
                firstPredicateKeywordList);
        SubjectContainsKeywordsPredicate secondPredicate = new SubjectContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SubjectContainsKeywordsPredicate firstPredicateCopy = new SubjectContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different student -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_levelContainsKeywords_returnsTrue() {
        // One keyword
        SubjectContainsKeywordsPredicate predicate = new SubjectContainsKeywordsPredicate(
                Collections.singletonList("MATH"));
        assertTrue(predicate.test(new StudentBuilder().withSubjects("MATH", "PHYSICS").build()));

        // Multiple keywords
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("MATH", "PHYSICS"));
        assertTrue(predicate.test(new StudentBuilder().withSubjects("MATH", "PHYSICS").build()));

        // Only one matching keyword
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("ENGLISH", "PHYSICS"));
        assertTrue(predicate.test(new StudentBuilder().withSubjects("MATH", "ENGLISH").build()));
    }

    @Test
    public void test_levelDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SubjectContainsKeywordsPredicate predicate = new SubjectContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudentBuilder().withSubjects("MATH").build()));

        // Non-matching keyword
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("ENGLISH"));
        assertFalse(predicate.test(new StudentBuilder().withSubjects("MATH", "PHYSICS").build()));

        // Keywords match phone and address, but does not match level
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("12345", "Main", "Street"));
        assertFalse(predicate.test(new StudentBuilder().withSubjects("MATH").withPhone("12345")
                .withAddress("Main Street").build()));
    }
}
