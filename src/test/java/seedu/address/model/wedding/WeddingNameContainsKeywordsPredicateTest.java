package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.WeddingBuilder;

public class WeddingNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        WeddingNameContainsKeywordsPredicate firstPredicate =
                new WeddingNameContainsKeywordsPredicate(firstPredicateKeywordList);
        WeddingNameContainsKeywordsPredicate secondPredicate =
                new WeddingNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        WeddingNameContainsKeywordsPredicate firstPredicateCopy =
                new WeddingNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        WeddingNameContainsKeywordsPredicate predicate =
                new WeddingNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new WeddingBuilder().withWeddingName("Alice & Bob").build()));

        // Multiple keywords
        predicate = new WeddingNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new WeddingBuilder().withWeddingName("Alice & Bobb").build()));

        // Only one matching keyword
        predicate = new WeddingNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new WeddingBuilder().withWeddingName("Alice & Carol").build()));

        // Mixed-case keywords
        predicate = new WeddingNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new WeddingBuilder().withWeddingName("Alice & Bobb").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        WeddingNameContainsKeywordsPredicate predicate =
                new WeddingNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new WeddingBuilder().withWeddingName("Alice & Bob").build()));

        // Non-matching keyword
        predicate = new WeddingNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new WeddingBuilder().withWeddingName("Alice & Bobb").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new WeddingNameContainsKeywordsPredicate(Arrays.asList("woodlands", "22/01/2024"));
        assertFalse(predicate.test(new WeddingBuilder().withWeddingName("Alice & Bob")
                .withVenue("woodlands").withDate("22/01/2024").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        WeddingNameContainsKeywordsPredicate predicate = new WeddingNameContainsKeywordsPredicate(keywords);

        String expected = WeddingNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
