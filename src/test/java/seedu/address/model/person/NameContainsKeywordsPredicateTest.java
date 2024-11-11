package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
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
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Ali", "Car"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords in a different order
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Car", "Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_isExact() {
        // Exact match with single keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice").build()));

        // Exact match with multiple keywords concatenated
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // Exact match with mixed-case
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // No match due to extra keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "Carol"));
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // No match due to different keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Carol"));
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // Slash character ignored in matching
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob$"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // No match due to leading or trailing spaces
        predicate = new NameContainsKeywordsPredicate(Arrays.asList(" Alice ", " Bob "));
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // No match due to order of words
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Alice"));
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // Empty keyword list
        predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // Exact match with name containing multiple spaces
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("AliceBob"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice    Bob").build()));

        // Multiple keywords without space between words
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("AliceBob$"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // Different casing in keywords and person name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("ALICE", "bob"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("alice BOB").build()));
    }
}
