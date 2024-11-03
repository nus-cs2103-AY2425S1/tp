package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyword = Collections.singletonList("91234567");
        List<String> secondPredicateKeyword = Arrays.asList("91234567", "87654321");

        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeyword);
        PhoneContainsKeywordsPredicate secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy = new PhoneContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different phone -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // Exact match
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Multiple exact matches
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("87654321", "91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Case insensitive
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("87654321"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Partial match (should not match since we're using containsWordIgnoreCase)
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("9123"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Keywords match email, name and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("alice", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").withEmail("alice@email.com")
                .withName("Alice").withAddress("Main Street").build()));
    }
}
