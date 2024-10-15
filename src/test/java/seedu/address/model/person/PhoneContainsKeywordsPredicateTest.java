package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

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

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        EmailContainsKeywordsPredicate predicate;
        //One keyword
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("alice", "example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("bob", "example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("ALicE", "exAMplE"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        EmailContainsKeywordsPredicate predicate;
        // Zero keywords
        predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("bob"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Keywords match phone, and address, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@example.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
