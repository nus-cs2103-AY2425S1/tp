package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class NameContainsSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        NameContainsSubstringPredicate firstPredicate =
                new NameContainsSubstringPredicate(firstPredicateSubstring);
        NameContainsSubstringPredicate secondPredicate =
                new NameContainsSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsSubstringPredicate firstPredicateCopy =
                new NameContainsSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsSubstring_returnsTrue() {
        // Partial name match
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("Alice");
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Full name match
        predicate = new NameContainsSubstringPredicate("Alice Bob");
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Mixed-case substring
        predicate = new NameContainsSubstringPredicate("aLIce bOB");
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_emptySubstring_throwsException() {
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> predicate.test(
                new ClientBuilder().withName("Alice").build()));
    }

    @Test
    public void test_nameDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("Carol");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Substring has a single matching word but substring does not match name
        predicate = new NameContainsSubstringPredicate("Bob Carol");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice Carol").build()));

        // Substring matches phone but does not match name
        predicate = new NameContainsSubstringPredicate("91234567");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches email but does not match name
        predicate = new NameContainsSubstringPredicate("alice@email.com");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches address but does not match name
        predicate = new NameContainsSubstringPredicate("Main Street");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches remark but does not match remark
        predicate = new NameContainsSubstringPredicate("Genius");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches remark but does not match job
        predicate = new NameContainsSubstringPredicate("Doctor");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate(substring);

        String expected = NameContainsSubstringPredicate.class.getCanonicalName()
                + "{substring=" + substring.toUpperCase() + "}";
        assertEquals(expected, predicate.toString());
    }
}
