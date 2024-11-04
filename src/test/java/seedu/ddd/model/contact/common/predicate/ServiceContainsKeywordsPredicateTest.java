package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ddd.testutil.contact.VendorBuilder;

public class ServiceContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ServiceContainsKeywordsPredicate firstPredicate =
                new ServiceContainsKeywordsPredicate(firstPredicateKeywordList);
        ServiceContainsKeywordsPredicate secondPredicate =
                new ServiceContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ServiceContainsKeywordsPredicate firstPredicateCopy =
                new ServiceContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different name list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_serviceContainsKeywords_returnsTrue() {
        // One keyword
        ServiceContainsKeywordsPredicate predicate =
                new ServiceContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new VendorBuilder().withService("Alice Bob").build()));

        // Multiple keywords
        predicate = new ServiceContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new VendorBuilder().withService("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ServiceContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new VendorBuilder().withService("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ServiceContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new VendorBuilder().withService("Alice Bob").build()));
    }

    @Test
    public void test_serviceDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ServiceContainsKeywordsPredicate predicate = new ServiceContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new VendorBuilder().withService("Alice").build()));

        // Non-matching keyword
        predicate = new ServiceContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new VendorBuilder().withService("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match service
        predicate = new ServiceContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new VendorBuilder().withService("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ServiceContainsKeywordsPredicate predicate = new ServiceContainsKeywordsPredicate(keywords);

        String expected = ServiceContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
