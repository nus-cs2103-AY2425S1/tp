package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskNameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Collections.singletonList("second");

        TaskNameContainsKeywordsPredicate firstPredicate =
            new TaskNameContainsKeywordsPredicate(firstPredicateKeywordList);
        TaskNameContainsKeywordsPredicate secondPredicate =
            new TaskNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object --> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values --> true
        TaskNameContainsKeywordsPredicate firstPredicateCopy =
            new TaskNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different type --> false
        assertFalse(firstPredicate.equals(1));

        // null --> false
        assertFalse(firstPredicate.equals(null));

        // different obj
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_taskNameContainsKeywords_returnsTrue() {
        Task testTask = new Task(new TaskName("CA2 Test"), new Deadline(LocalDateTime.now()));
        // one keyword
        TaskNameContainsKeywordsPredicate predicate =
            new TaskNameContainsKeywordsPredicate(Collections.singletonList("Test"));
        assertTrue(predicate.test(testTask));

        // multiple keywords
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("Test", "CA"));
        assertTrue(predicate.test(testTask));

        // only one matching keyword
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("CA", "Submission"));
        assertTrue(predicate.test(testTask));

        // only lower-case keywords
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("ca", "test"));
        assertTrue(predicate.test(testTask));
    }

    @Test
    public void test_taskNameDoesNotContainKeywords_returnsFalse() {
        Task testTask = new Task(new TaskName("CA2 Test"), new Deadline(LocalDateTime.now()));

        // zero keywords
        TaskNameContainsKeywordsPredicate predicate = new TaskNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(testTask));

        // non-matching keyword
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("Submission"));
        assertFalse(predicate.test(testTask));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TaskNameContainsKeywordsPredicate predicate = new TaskNameContainsKeywordsPredicate(keywords);

        String expected = TaskNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
