package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneNumberContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("12345");
        List<String> secondPredicateKeywordList = Arrays.asList("12345", "678910");

        PhoneNumberContainsKeywordPredicate firstPredicate =
                new PhoneNumberContainsKeywordPredicate(firstPredicateKeywordList);
        PhoneNumberContainsKeywordPredicate secondPredicate =
                new PhoneNumberContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneNumberContainsKeywordPredicate firstPredicateCopy =
                new PhoneNumberContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneNumberContainsKeywords_returnsTrue() {
        PhoneNumberContainsKeywordPredicate predicate =
                new PhoneNumberContainsKeywordPredicate(Collections.singletonList("86796685"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("86796685").build()));

    }

    @Test
    public void test_phoneNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneNumberContainsKeywordPredicate predicate =
                new PhoneNumberContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("1234567").build()));

        // Non-matching keyword
        predicate = new PhoneNumberContainsKeywordPredicate(Arrays.asList("123456"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("45678").build()));

        // Keywords match name, email and address, but does not match phone
        predicate =
                new PhoneNumberContainsKeywordPredicate(Arrays.asList(
                        "Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("34567")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);

        String expected = PhoneNumberContainsKeywordPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
