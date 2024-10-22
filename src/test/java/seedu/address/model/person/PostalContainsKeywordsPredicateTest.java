package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PostalContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("123456");
        List<String> secondPredicateKeywordList = Arrays.asList("123456", "654321");

        PostalContainsKeywordsPredicate firstPredicate =
                new PostalContainsKeywordsPredicate(firstPredicateKeywordList);
        PostalContainsKeywordsPredicate secondPredicate =
                new PostalContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PostalContainsKeywordsPredicate firstPredicateCopy =
                new PostalContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_postalContainsKeywords_returnsTrue() {
        // One matching 6-digit postal code
        PostalContainsKeywordsPredicate predicate =
                new PostalContainsKeywordsPredicate(Collections.singletonList("123456"));
        assertTrue(predicate.test(new PersonBuilder().withPostalCode("123456").build()));

        // Multiple matching keywords
        predicate = new PostalContainsKeywordsPredicate(Arrays.asList("123456", "654321"));
        assertTrue(predicate.test(new PersonBuilder().withPostalCode("123456").build()));

        // Only one matching keyword
        predicate = new PostalContainsKeywordsPredicate(Arrays.asList("654321", "123456"));
        assertTrue(predicate.test(new PersonBuilder().withPostalCode("654321").build()));

        // Mixed-case keywords (though postal codes are numeric)
        predicate = new PostalContainsKeywordsPredicate(Arrays.asList("123456"));
        assertTrue(predicate.test(new PersonBuilder().withPostalCode("123456").build()));
    }

    @Test
    public void test_postalDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PostalContainsKeywordsPredicate predicate = new PostalContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPostalCode("123456").build()));

        // Non-matching 6-digit postal code
        predicate = new PostalContainsKeywordsPredicate(Arrays.asList("789012"));
        assertFalse(predicate.test(new PersonBuilder().withPostalCode("123456").build()));

        // Non-matching 6-digit keyword
        predicate = new PostalContainsKeywordsPredicate(Arrays.asList("000000"));
        assertFalse(predicate.test(new PersonBuilder().withPostalCode("123456").build()));

        // Keywords match other attributes but do not match postal code
        predicate = new PostalContainsKeywordsPredicate(Arrays.asList("Alice", "98765432", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withPostalCode("123456")
                .withName("Alice").withPhone("98765432").withEmail("alice@email.com").build()));
    }

}
