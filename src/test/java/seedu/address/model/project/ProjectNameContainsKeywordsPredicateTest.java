package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ProjectBuilder;

public class ProjectNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ProjectNameContainsKeywordsPredicate firstPredicate =
                new ProjectNameContainsKeywordsPredicate(firstPredicateKeywordList);
        ProjectNameContainsKeywordsPredicate secondPredicate =
                new ProjectNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ProjectNameContainsKeywordsPredicate firstPredicateCopy =
                new ProjectNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different employee -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ProjectNameContainsKeywordsPredicate predicate =
                new ProjectNameContainsKeywordsPredicate(Collections.singletonList("Alpha"));
        assertTrue(predicate.test(new ProjectBuilder().withName("Project Alpha").build()));

        // Multiple keywords
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Alpha", "Project"));
        assertTrue(predicate.test(new ProjectBuilder().withName("Project Alpha").build()));

        // Only one matching keyword
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Alpha", "Carol"));
        assertTrue(predicate.test(new ProjectBuilder().withName("Project Alpha").build()));

        // Mixed-case keywords
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("aLPha", "pROjeCt"));
        assertTrue(predicate.test(new ProjectBuilder().withName("Project Alpha").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ProjectNameContainsKeywordsPredicate predicate =
                new ProjectNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ProjectBuilder().withName("Alpha").build()));

        // Non-matching keyword
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ProjectBuilder().withName("Project Alpha").build()));

        // Keywords match id, but does not match name
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("A0276123J"));
        assertFalse(predicate.test(new ProjectBuilder().withName("Alpha").withId("A0276123J").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ProjectNameContainsKeywordsPredicate predicate = new ProjectNameContainsKeywordsPredicate(keywords);

        String expected = ProjectNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
