package seedu.address.model.person.keywordspredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.Task;

public class TaskContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Book venue");
        List<String> secondPredicateKeywordList = Arrays.asList("Do hair and makeup");

        TaskContainsKeywordsPredicate firstPredicate =
                new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        TaskContainsKeywordsPredicate secondPredicate =
                new TaskContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskContainsKeywordsPredicate firstPredicateCopy =
                new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_taskContainsKeywords_returnsTrue() {
        // One full-matching keyword
        TaskContainsKeywordsPredicate predicate =
                new TaskContainsKeywordsPredicate(Arrays.asList("Make bouquet"));
        assertTrue(predicate.test(new Task("Make bouquet")));

        // One full-matching keyword
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("Make bouquet", "Cater food"));
        assertTrue(predicate.test(new Task("Make bouquet")));

        // One partial-matching keyword
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("bouq"));
        assertTrue(predicate.test(new Task("Make bouquet")));

        // One partial-matching keyword, case-insensitive
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("maKe Bouquet"));
        assertTrue(predicate.test(new Task("Make bouquet")));
    }

    @Test
    public void test_taskDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new Task("Make bouquet")));

        // Non-matching keyword
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("Food"));
        assertFalse(predicate.test(new Task("Make bouquet")));

        // Multiple non-matching keywords
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("Food", "Makeup"));
        assertFalse(predicate.test(new Task("Make bouquet")));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Upload photographs", "Finalise catering menu ");
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(keywords);

        String expected = TaskContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }


}
