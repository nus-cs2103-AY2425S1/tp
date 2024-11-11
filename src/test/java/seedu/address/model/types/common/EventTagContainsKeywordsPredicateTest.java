package seedu.address.model.types.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventTagContainsKeywordsPredicate firstPredicate =
                new EventTagContainsKeywordsPredicate(firstPredicateKeywordList);
        EventTagContainsKeywordsPredicate secondPredicate =
                new EventTagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventTagContainsKeywordsPredicate firstPredicateCopy =
                new EventTagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        EventTagContainsKeywordsPredicate predicate =
                new EventTagContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new EventBuilder().withTags("friends", "hobby").build()));

        // Multiple keywords
        predicate = new EventTagContainsKeywordsPredicate(Arrays.asList("friends", "hobby"));
        assertTrue(predicate.test(new EventBuilder().withTags("friends", "hobby").build()));

        // Only one matching keyword
        predicate = new EventTagContainsKeywordsPredicate(Arrays.asList("hobby", "music"));
        assertTrue(predicate.test(new EventBuilder().withTags("friends", "music").build()));

        // Mixed-case keywords
        predicate = new EventTagContainsKeywordsPredicate(Arrays.asList("fRIends", "hObBy"));
        assertTrue(predicate.test(new EventBuilder().withTags("friends", "hobby").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventTagContainsKeywordsPredicate predicate =
                new EventTagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new EventTagContainsKeywordsPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new EventBuilder().withTags("friends", "hobby").build()));

        // Keywords match name, address and start time, but does not match tag
        predicate = new EventTagContainsKeywordsPredicate(
                Arrays.asList("Anime", "Jurong", "West", "2024-01-20", "12:30"));
        assertFalse(predicate.test(new EventBuilder().withName("Anime").withAddress("Jurong West")
                .withStartTime("2024-01-20 12:30").withTags("hobby").build()));
    }
}
