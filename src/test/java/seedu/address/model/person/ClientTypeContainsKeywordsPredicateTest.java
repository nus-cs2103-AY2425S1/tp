package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for {@code ClientTypeContainsKeywordsPredicate}.
 */
public class ClientTypeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        // Initialising the Predicate Keyword list
        String firstPredicateKeyword = "Investment";
        String secondPredicateKeyword = "Healthcare";
        List<String> firstPredicateKeywordList = new ArrayList<>();
        List<String> secondPredicateKeywordList = new ArrayList<>();
        firstPredicateKeywordList.add(firstPredicateKeyword);
        secondPredicateKeywordList.add(secondPredicateKeyword);

        ClientTypeContainsKeywordsPredicate firstPredicate =
                new ClientTypeContainsKeywordsPredicate(firstPredicateKeywordList);
        ClientTypeContainsKeywordsPredicate secondPredicate =
                new ClientTypeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClientTypeContainsKeywordsPredicate firstPredicateCopy =
                new ClientTypeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_clientTypeBeginsWithKeyword_returnsTrue() {
        // One letter
        ClientTypeContainsKeywordsPredicate predicate = new ClientTypeContainsKeywordsPredicate(List.of("I"));
        assertTrue(predicate.test(new PersonBuilder().withClientTypes("Investment").build()));

        // Multiple letters
        predicate = new ClientTypeContainsKeywordsPredicate(List.of("Invest"));
        assertTrue(predicate.test(new PersonBuilder().withClientTypes("Investment").build()));

        // Only one matching keyword
        predicate = new ClientTypeContainsKeywordsPredicate(List.of("Investment"));
        assertTrue(predicate.test(new PersonBuilder().withClientTypes("Investment").build()));
    }

    @Test
    public void test_clientTypeDoesNotBeginWithKeyword_returnsFalse() {
        // Zero keyword
        ClientTypeContainsKeywordsPredicate predicate = new ClientTypeContainsKeywordsPredicate(new ArrayList<>());
        assertFalse(predicate.test(new PersonBuilder().withClientTypes("Investment").build()));

        // Non-matching keyword
        predicate = new ClientTypeContainsKeywordsPredicate(List.of("Invistment"));
        assertFalse(predicate.test(new PersonBuilder().withClientTypes("Investment").build()));

        // Keywords match phone, email and address, but does not match ClientType
        predicate = new ClientTypeContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street")
        );
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").withClientTypes("Investment").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "Investment";
        ClientTypeContainsKeywordsPredicate predicate = new ClientTypeContainsKeywordsPredicate(List.of(keyword));

        String expected = ClientTypeContainsKeywordsPredicate
                .class
                .getCanonicalName() + "{keywords=" + List.of(keyword) + "}";
        assertEquals(expected, predicate.toString());
    }
}
