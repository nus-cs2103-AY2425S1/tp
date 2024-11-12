package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class ClientContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ClientContainsKeywordsPredicate firstPredicate = new ClientContainsKeywordsPredicate(firstPredicateKeywordList);
        ClientContainsKeywordsPredicate secondPredicate =
                new ClientContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClientContainsKeywordsPredicate firstPredicateCopy =
                new ClientContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ClientContainsKeywordsPredicate predicate =
                new ClientContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ClientContainsKeywordsPredicate predicate = new ClientContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_companyContainsKeywords_returnsTrue() {
        // One keyword
        ClientContainsKeywordsPredicate predicate =
                new ClientContainsKeywordsPredicate(Collections.singletonList("Apple"));
        assertTrue(predicate.test(new ClientBuilder().withCompany("Apple Co.").build()));

        // Multiple keywords
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("Apple", "Orange"));
        assertTrue(predicate.test(new ClientBuilder().withCompany("Apple Orange Inc.").build()));

        // Only one matching keyword
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("Apple", "Happle"));
        assertTrue(predicate.test(new ClientBuilder().withCompany("Happle Co.").build()));

        // Mixed-case keywords
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("aPpLe", "oRaNge"));
        assertTrue(predicate.test(new ClientBuilder().withCompany("Apple Orange Inc.").build()));
    }

    @Test
    public void test_companyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ClientContainsKeywordsPredicate predicate = new ClientContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ClientBuilder().withCompany("Apple Inc.").build()));

        // Non-matching keyword
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("ABC"));
        assertFalse(predicate.test(new ClientBuilder().withCompany("Apple Orange").build()));

        // Keywords match phone, email and address, but does not match company
        predicate = new ClientContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ClientBuilder().withCompany("Apple Inc.").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ClientContainsKeywordsPredicate predicate = new ClientContainsKeywordsPredicate(keywords);

        String expected = ClientContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
