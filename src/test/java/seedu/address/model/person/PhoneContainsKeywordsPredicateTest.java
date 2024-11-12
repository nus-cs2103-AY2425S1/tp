package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyword = Collections.singletonList("61234567");
        List<String> secondPredicateKeyword = Arrays.asList("61234567", "87654321");

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
        // Exact match starting with 6
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("61234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("61234567").build()));

        // Exact match starting with 8
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("81234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("81234567").build()));

        // Exact match starting with 9
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Multiple exact matches, including number starting with 8
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("87654321", "81234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("81234567").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("87654321"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Partial match (using containsWordIgnoreCase)
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("9123"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Keywords match email, name, and address, but do not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("alice", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").withEmail("alice@email.com")
                .withName("Alice").withAddress("Main Street").build()));
    }


    @Test
    public void test_invalidPhoneFormats_throwsIllegalArgumentException() {
        // Incorrect length (less than 8 digits)
        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("6123456").build());

        // Incorrect length (more than 8 digits)
        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("612345678").build());

        // Invalid starting digits
        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("71234567").build());

        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("51234567").build());

        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("31234567").build());

        // Phone number with special characters
        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("6123-4567").build());

        // Phone number with spaces
        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("6123 4567").build());

        // Non-numeric characters
        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("6123abcd").build());

        // Valid starting digits but incorrect length or format
        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("9123").build());

        assertThrows(IllegalArgumentException.class, () ->
                new PersonBuilder().withPhone("8123456789").build());
    }
}
