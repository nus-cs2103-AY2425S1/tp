package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class EmailContainsSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        EmailContainsSubstringPredicate firstPredicate =
                new EmailContainsSubstringPredicate(firstPredicateSubstring);
        EmailContainsSubstringPredicate secondPredicate =
                new EmailContainsSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsSubstringPredicate firstPredicateCopy =
                new EmailContainsSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsSubstring_returnsTrue() {
        // Partial email match
        EmailContainsSubstringPredicate predicate = new EmailContainsSubstringPredicate("alice@gmail");
        assertTrue(predicate.test(new ClientBuilder().withEmail("alice@gmail.com").build()));

        // Full email match
        predicate = new EmailContainsSubstringPredicate("alice@gmail.com");
        assertTrue(predicate.test(new ClientBuilder().withEmail("alice@gmail.com").build()));

        // Mixed-case substring
        predicate = new EmailContainsSubstringPredicate("aLiCe@gMaiL.cOm");
        assertTrue(predicate.test(new ClientBuilder().withEmail("alice@gmail.com").build()));
    }

    @Test
    public void test_emptySubstring_throwsException() {
        EmailContainsSubstringPredicate predicate = new EmailContainsSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> predicate.test(
                new ClientBuilder().withEmail("Alice").build()));
    }

    @Test
    public void test_emailDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        EmailContainsSubstringPredicate predicate = new EmailContainsSubstringPredicate("bob@gmail.com");
        assertFalse(predicate.test(new ClientBuilder().withEmail("alice@yahoo.com").build()));

        // Substring has some matching parts but substring does not match email
        predicate = new EmailContainsSubstringPredicate("bob@gmail");
        assertFalse(predicate.test(new ClientBuilder().withEmail("bob@yahoo.com").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        EmailContainsSubstringPredicate predicate = new EmailContainsSubstringPredicate(substring);

        String expected = EmailContainsSubstringPredicate.class.getCanonicalName()
                + "{substring=" + substring.toUpperCase() + "}";
        assertEquals(expected, predicate.toString());
    }
}
