package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyword = Collections.singletonList("Main Street");
        List<String> secondPredicateKeyword = Arrays.asList("Clementi", "Street");

        AddressContainsKeywordsPredicate firstPredicate = new AddressContainsKeywordsPredicate(firstPredicateKeyword);
        AddressContainsKeywordsPredicate secondPredicate = new AddressContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword exact match
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("mAiN", "sTrEeT"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        // Partial match
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Main St"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        // Full address match
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("123 Main Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Avenue"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        // Keywords match phone, email and name, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 Main Street").withPhone("98122345")
                .withEmail("alice@email.com").withName("Alice").build()));
    }
}
