package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstKeywordList = Collections.singletonList("Meeting");
        List<String> secondKeywordList = Arrays.asList("Meeting", "Review");

        EventNameContainsKeywordsPredicate firstPredicate = new EventNameContainsKeywordsPredicate(firstKeywordList);
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(secondKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventNameContainsKeywordsPredicate firstPredicateCopy =
                new EventNameContainsKeywordsPredicate(firstKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventNameContainsKeywords_returnsTrue() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Meeting"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Meeting with Client").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Meeting", "Client"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Meeting with Client").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Review", "Meeting"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Team Meeting").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("mEeTiNg", "cLiEnT"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Meeting with Client").build()));
    }

    @Test
    public void test_eventNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withEventName("Meeting with Client").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Review"));
        assertFalse(predicate.test(new EventBuilder().withEventName("Meeting with Client").build()));

        // Keywords match location and date, but not name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Office", "2024-12-01"));
        assertFalse(predicate.test(new EventBuilder().withEventName("Client Review")
                .withLocation("Office").withDate("2024-12-01").build()));
    }
}
