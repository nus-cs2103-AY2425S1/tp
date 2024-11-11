package hallpointer.address.model.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hallpointer.address.testutil.SessionBuilder;

public class SessionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SessionContainsKeywordsPredicate firstPredicate =
                new SessionContainsKeywordsPredicate(firstPredicateKeywordList);
        SessionContainsKeywordsPredicate secondPredicate =
                new SessionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SessionContainsKeywordsPredicate firstPredicateCopy =
                new SessionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        SessionContainsKeywordsPredicate predicate =
                new SessionContainsKeywordsPredicate(Collections.singletonList("Meeting"));
        assertTrue(predicate.test(new SessionBuilder().withSessionName("Meeting W1").build()));

        // Multiple keywords
        predicate = new SessionContainsKeywordsPredicate(Arrays.asList("Meeting", "W1"));
        assertTrue(predicate.test(new SessionBuilder().withSessionName("Meeting W1").build()));

        // Only one matching keyword
        predicate = new SessionContainsKeywordsPredicate(Arrays.asList("Rehearsal", "W1"));
        assertTrue(predicate.test(new SessionBuilder().withSessionName("Meeting W1").build()));

        // Two matching keywords, out of order
        predicate = new SessionContainsKeywordsPredicate(Arrays.asList("W2", "W1", "Meeting"));
        assertTrue(predicate.test(new SessionBuilder().withSessionName("Meeting W1").build()));

        // Keyword same up to case
        predicate = new SessionContainsKeywordsPredicate(Collections.singletonList("mEETING"));
        assertTrue(predicate.test(new SessionBuilder().withSessionName("Meeting W1").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SessionContainsKeywordsPredicate predicate = new SessionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new SessionBuilder().withSessionName("Rehearsal").build()));

        // Non-matching keyword
        predicate = new SessionContainsKeywordsPredicate(Arrays.asList("Rehearsal"));
        assertFalse(predicate.test(new SessionBuilder().withSessionName("Meeting").build()));

        // Keywords match date and points, but does not match session name
        predicate = new SessionContainsKeywordsPredicate(Arrays.asList("rehearsal", "24", "Aug", "2024", "9"));
        assertFalse(predicate.test(new SessionBuilder().withSessionName("Meeting")
                .withDate("24 Aug 2024").withPoints("9").build()));

        // No partial match
        predicate = new SessionContainsKeywordsPredicate(Arrays.asList("1"));
        assertFalse(predicate.test(new SessionBuilder().withSessionName("Meeting W1").build()));
    }
}
