package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RestaurantBuilder;



public class PriceContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("$");
        List<String> secondPredicateKeywordList = Arrays.asList("$", "$$");

        PriceContainsKeywordsPredicate firstPredicate = new PriceContainsKeywordsPredicate(
                firstPredicateKeywordList);
        PriceContainsKeywordsPredicate secondPredicate = new PriceContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PriceContainsKeywordsPredicate firstPredicateCopy =
                new PriceContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_priceContainsKeywords_returnsTrue() {
        // One keyword
        PriceContainsKeywordsPredicate predicate =
                new PriceContainsKeywordsPredicate(Collections.singletonList("$"));
        assertTrue(predicate.test(new RestaurantBuilder().withPrice("$").build()));

        // Multiple keywords
        predicate = new PriceContainsKeywordsPredicate(Arrays.asList("$", "$$"));
        assertTrue(predicate.test(new RestaurantBuilder().withPrice("$").build()));
    }

    @Test
    public void test_priceNotContainsKeywords_returnsFalse() {
        // One keyword
        PriceContainsKeywordsPredicate predicate =
                new PriceContainsKeywordsPredicate(Collections.singletonList("$$$"));
        assertFalse(predicate.test(new RestaurantBuilder().withPrice("$").build()));

        // Multiple keywords
        predicate = new PriceContainsKeywordsPredicate(Arrays.asList("$$$", "$$$$"));
        assertFalse(predicate.test(new RestaurantBuilder().withPrice("$").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("$", "$$");
        PriceContainsKeywordsPredicate predicate = new PriceContainsKeywordsPredicate(keywords);

        String expected = PriceContainsKeywordsPredicate.class.getCanonicalName() + "{price labels=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
