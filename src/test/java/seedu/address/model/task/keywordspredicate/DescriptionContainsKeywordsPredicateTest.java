package seedu.address.model.task.keywordspredicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.Task;
import seedu.address.testutil.TypicalTasks;

public class DescriptionContainsKeywordsPredicateTest {

    private static final Task TASK_BUY_GROCERIES = TypicalTasks.TODO_TASK;
    private static final Task TASK_SUBMIT_ASSIGNMENT = TypicalTasks.SUBMIT_ASSIGNMENT_TASK;

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("groceries");
        List<String> secondPredicateKeywordList = Arrays.asList("groceries", "assignment");

        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionContainsKeywordsPredicate firstPredicateCopy =
                new DescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("groceries"));
        assertTrue(predicate.test(TASK_BUY_GROCERIES));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("groceries", "assignment"));
        assertTrue(predicate.test(TASK_BUY_GROCERIES));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("assignment", "groceries"));
        assertTrue(predicate.test(TASK_SUBMIT_ASSIGNMENT));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("GrOCeRiEs", "ASSIGNMENT"));
        assertTrue(predicate.test(TASK_BUY_GROCERIES));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(TASK_BUY_GROCERIES));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("assignment"));
        assertFalse(predicate.test(TASK_BUY_GROCERIES));

        // Keywords match part of the description but not fully
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("buy", "grocer"));
        assertFalse(predicate.test(TASK_SUBMIT_ASSIGNMENT));
    }
}
