package seedu.eventtory.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eventtory.testutil.EventBuilder;

public class EventContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventContainsKeywordsPredicate firstPredicate = new EventContainsKeywordsPredicate(
                firstPredicateKeywordList);
        EventContainsKeywordsPredicate secondPredicate = new EventContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventContainsKeywordsPredicate firstPredicateCopy = new EventContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // partial keywords
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Ali", "Bo"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // partial keywords with mixed-case
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("aLi", "bO"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_dateContainsKeywords_returnsTrue() {
        // One keyword
        EventContainsKeywordsPredicate predicate =
                new EventContainsKeywordsPredicate(Collections.singletonList("12-10-2024"));
        assertTrue(predicate.test(new EventBuilder().withDate("2024-10-12").build()));

        // Partial matching keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("2024"));
        assertTrue(predicate.test(new EventBuilder().withDate("2024-10-10").build()));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        EventContainsKeywordsPredicate predicate =
                new EventContainsKeywordsPredicate(Collections.singletonList("Cake"));
        assertTrue(predicate.test(new EventBuilder().withTags("Cake").build()));

        // Multiple keywords
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Cake", "Venue"));
        assertTrue(predicate.test(new EventBuilder().withTags("Cake", "Venue").build()));

        // Only one matching keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Cake", "Decorations"));
        assertTrue(predicate.test(new EventBuilder().withTags("Cake", "Venue").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new EventBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(keywords);

        String expected = EventContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
