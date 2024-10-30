package seedu.address.model.person.keywordspredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class WeddingContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        // Test same object -> returns true
        WeddingContainsKeywordsPredicate firstPredicate =
                new WeddingContainsKeywordsPredicate(Collections.singletonList("Carl's wedding"));
        assertEquals(firstPredicate, firstPredicate);

        // Test null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Test different types -> returns false
        assertNotEquals(5, firstPredicate);

        // Test different predicates -> returns false
        WeddingContainsKeywordsPredicate secondPredicate =
                new WeddingContainsKeywordsPredicate(Collections.singletonList("Wedding Apr 2025"));
        assertNotEquals(firstPredicate, secondPredicate);

        // Test same predicate with same keyword -> returns true
        WeddingContainsKeywordsPredicate firstPredicateCopy =
                new WeddingContainsKeywordsPredicate(Collections.singletonList("Carl's wedding"));
        assertEquals(firstPredicate, firstPredicateCopy);
    }

    @Test
    public void test_weddingContainsKeywords_returnsTrue() {
        // One full-matching keyword
        WeddingContainsKeywordsPredicate predicate =
                new WeddingContainsKeywordsPredicate(Arrays.asList("Bryan's Wedding"));
        assertTrue(predicate.test(new Wedding(new WeddingName("Bryan's Wedding"))));

        // One full-matching keyword
        predicate = new WeddingContainsKeywordsPredicate(Arrays.asList("Bryan's Wedding", "Wedding 1"));
        assertTrue(predicate.test(new Wedding(new WeddingName("Bryan's Wedding"))));

        // One partial-matching keyword
        predicate = new WeddingContainsKeywordsPredicate(Arrays.asList("Bry"));
        assertTrue(predicate.test(new Wedding(new WeddingName("Bryan's Wedding"))));

        // One partial-matching, case-insensitive keyword
        predicate = new WeddingContainsKeywordsPredicate(Arrays.asList("bry"));
        assertTrue(predicate.test(new Wedding(new WeddingName("Bryan's Wedding"))));
    }

    @Test
    public void test_weddingDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        WeddingContainsKeywordsPredicate predicate = new WeddingContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new Wedding(new WeddingName("Zack's Wedding"))));

        // Non-matching keyword
        predicate = new WeddingContainsKeywordsPredicate(Arrays.asList("Zayn"));
        assertFalse(predicate.test(new Wedding(new WeddingName("Zack's Wedding"))));

        // Multiple non-matching keywords
        predicate = new WeddingContainsKeywordsPredicate(Arrays.asList("Zara", "Zofia"));
        assertFalse(predicate.test(new Wedding(new WeddingName("Zack's Wedding"))));

    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Wedding1", "Wedding2");
        WeddingContainsKeywordsPredicate predicate = new WeddingContainsKeywordsPredicate(keywords);

        String expected = WeddingContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

}
