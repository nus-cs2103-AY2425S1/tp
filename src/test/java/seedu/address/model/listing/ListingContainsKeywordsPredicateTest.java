package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ListingBuilder;

public class ListingContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ListingContainsKeywordsPredicate firstPredicate =
                new ListingContainsKeywordsPredicate(firstPredicateKeywordList);
        ListingContainsKeywordsPredicate secondPredicate =
                new ListingContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ListingContainsKeywordsPredicate firstPredicateCopy =
                new ListingContainsKeywordsPredicate(firstPredicateKeywordList);
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
        ListingContainsKeywordsPredicate predicate =
                new ListingContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ListingBuilder().withName("Alice House").build()));

        // Multiple keywords
        predicate = new ListingContainsKeywordsPredicate(Arrays.asList("Alice", "House"));
        assertTrue(predicate.test(new ListingBuilder().withName("Alice House").build()));

        // Only one matching keyword
        predicate = new ListingContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ListingBuilder().withName("Carol House").build()));

        // Mixed-case keywords
        predicate = new ListingContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ListingBuilder().withName("Alice House").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ListingContainsKeywordsPredicate predicate = new ListingContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ListingBuilder().withName("Alice House").build()));

        // Non-matching keyword
        predicate = new ListingContainsKeywordsPredicate(Arrays.asList("Carol", "House"));
        assertFalse(predicate.test(new ListingBuilder().withName("Alice Condo").build()));

        // Keywords match address, area, region and price, but does not match name
        predicate = new ListingContainsKeywordsPredicate(
                Arrays.asList("Sengkang", "NORTH", "500000", "St", "1500", "22"));
        assertFalse(predicate.test(new ListingBuilder().withName("Alice House")
                .withAddress("Sengkang St 22")
                .withArea(1500)
                .withRegion(Region.NORTH)
                .withPrice("500000", new BigDecimal(500000))
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ListingContainsKeywordsPredicate predicate = new ListingContainsKeywordsPredicate(keywords);

        String expected = ListingContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
