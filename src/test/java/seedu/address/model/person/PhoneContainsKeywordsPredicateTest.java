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
        List<String> firstPredicateKeywordList = Collections.singletonList("91234567");
        List<String> secondPredicateKeywordList = Arrays.asList("91230000", "81230000");

        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("91234567", "87654321"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("91234567", "87654321"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("91234567"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("81234567").build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("90000000"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("Alice", "98765432", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567")
                .withName("Alice").withPostalCode("123456").withEmail("alice@email.com").build()));
    }
}
