package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("alice@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("bob@example.com", "charlie@example.com");

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy = new
                EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different email -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // Exact match
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Mixed case
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("aLiCe@eXaMpLe.CoM"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Partial match
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("alice@"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Multiple keywords, matches at least one
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("bob@example.com", "alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("bob@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Keywords match phone, name and address, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("12345", "alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alic@example.com").withPhone("97122345")
                .withName("Alice").withAddress("Main Street").build()));
    }
}
