package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class PhoneContainsSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        PhoneContainsSubstringPredicate firstPredicate =
                new PhoneContainsSubstringPredicate(firstPredicateSubstring);
        PhoneContainsSubstringPredicate secondPredicate =
                new PhoneContainsSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsSubstringPredicate firstPredicateCopy =
                new PhoneContainsSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsSubstring_returnsTrue() {
        // Exact match
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate("91112222");
        assertTrue(predicate.test(new ClientBuilder().withPhone("91112222").build()));

        // Partial matches cannot be tested due to parsing requirements of phone number, which disallows
        // phone numbers that aren't 8 digits.
    }

    @Test
    public void test_emptySubstring_throwsException() {
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> predicate.test(
                new ClientBuilder().withPhone("91112222").build()));
    }

    @Test
    public void test_phoneDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate("91112222");
        assertFalse(predicate.test(new ClientBuilder().withPhone("81112222").build()));

        // Substring matches name but does not match phone
        predicate = new PhoneContainsSubstringPredicate("91112222");
        assertFalse(predicate.test(new ClientBuilder().withName("91112222").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Substring matches address but does not match phone
        predicate = new PhoneContainsSubstringPredicate("91112222");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("91112222").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate(substring);

        String expected = PhoneContainsSubstringPredicate.class.getCanonicalName()
                + "{substring=" + substring.toUpperCase() + "}";
        assertEquals(expected, predicate.toString());
    }
}
