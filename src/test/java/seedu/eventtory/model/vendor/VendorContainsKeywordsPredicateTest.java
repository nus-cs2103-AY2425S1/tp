package seedu.eventtory.model.vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eventtory.testutil.VendorBuilder;

public class VendorContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        VendorContainsKeywordsPredicate firstPredicate =
                new VendorContainsKeywordsPredicate(firstPredicateKeywordList);
        VendorContainsKeywordsPredicate secondPredicate =
                new VendorContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        VendorContainsKeywordsPredicate firstPredicateCopy =
                new VendorContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different vendor -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        VendorContainsKeywordsPredicate predicate =
                new VendorContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new VendorBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new VendorBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new VendorBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new VendorBuilder().withName("Alice Bob").build()));

        // partial keywords
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("Ali", "Bo"));
        assertTrue(predicate.test(new VendorBuilder().withName("Alice Bob").build()));

        // partial keywords with mixed-case
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("aLi", "bO"));
        assertTrue(predicate.test(new VendorBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        VendorContainsKeywordsPredicate predicate =
                new VendorContainsKeywordsPredicate(Collections.singletonList("Street"));
        assertTrue(predicate.test(new VendorBuilder().withDescription("Main Street").build()));

        // Multiple keywords
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("Main", "Street"));
        assertTrue(predicate.test(new VendorBuilder().withDescription("Main Street").build()));

        // Only one matching keyword
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("Street", "Block"));
        assertTrue(predicate.test(new VendorBuilder().withDescription("Main Street").build()));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        VendorContainsKeywordsPredicate predicate =
                new VendorContainsKeywordsPredicate(Collections.singletonList("Cake"));
        assertTrue(predicate.test(new VendorBuilder().withTags("Cake").build()));

        // Multiple keywords
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("Cake", "Venue"));
        assertTrue(predicate.test(new VendorBuilder().withTags("Cake", "Venue").build()));

        // Only one matching keyword
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("Cake", "Decorations"));
        assertTrue(predicate.test(new VendorBuilder().withTags("Cake", "Venue").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        // Zero keywords
        VendorContainsKeywordsPredicate predicate = new VendorContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new VendorBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new VendorContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new VendorBuilder().withName("Alice Bob").build()));
    }

    @Test void test_multipleKeywords_returnsTrue() {
        // Keywords match phone, email and description, but does not match name
        VendorContainsKeywordsPredicate predicate =
                new VendorContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertTrue(predicate.test(new VendorBuilder().withName("Alice").withPhone("12345")
                .withDescription("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        VendorContainsKeywordsPredicate predicate = new VendorContainsKeywordsPredicate(keywords);

        String expected = VendorContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
