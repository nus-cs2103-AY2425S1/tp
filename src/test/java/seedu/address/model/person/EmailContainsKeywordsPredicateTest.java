package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        List<String> secondPredicateKeywordList = Arrays.asList("alice@example.com", "bob@example.com");

        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com", "bob@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob@example.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("charlie@example.com", "bob@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob@example.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("ALICE@EXAMPLE.COM", "bOb@eXaMpLe.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("charlie@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Keywords match name, phone, and address, but do not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice", "12345678", "Main Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@example.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("alice@example.com", "bob@example.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
