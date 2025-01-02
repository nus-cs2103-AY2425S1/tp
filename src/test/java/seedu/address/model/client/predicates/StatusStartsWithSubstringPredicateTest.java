package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class StatusStartsWithSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        StatusStartsWithSubstringPredicate firstPredicate =
                new StatusStartsWithSubstringPredicate(firstPredicateSubstring);
        StatusStartsWithSubstringPredicate secondPredicate =
                new StatusStartsWithSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StatusStartsWithSubstringPredicate firstPredicateCopy =
                new StatusStartsWithSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_statusStartsWithSubstring_returnsTrue() {
        // Full status match
        StatusStartsWithSubstringPredicate predicate = new StatusStartsWithSubstringPredicate("urgent");
        assertTrue(predicate.test(new ClientBuilder().withStatus("urgent").build()));

        // Partial status match
        predicate = new StatusStartsWithSubstringPredicate("ur");
        assertTrue(predicate.test(new ClientBuilder().withStatus("urgent").build()));

        // Mixed-case substring
        predicate = new StatusStartsWithSubstringPredicate("uRgEnt");
        assertTrue(predicate.test(new ClientBuilder().withStatus("URGENT").build()));
    }

    @Test
    public void test_emptySubstring_throwsException() {
        StatusStartsWithSubstringPredicate predicate = new StatusStartsWithSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> predicate.test(
                new ClientBuilder().withStatus("URGENT").build()));
    }

    @Test
    public void test_statusDoesNotStartWithSubstring_returnsFalse() {
        // Non-matching substring
        StatusStartsWithSubstringPredicate predicate = new StatusStartsWithSubstringPredicate("URGENT");
        assertFalse(predicate.test(new ClientBuilder().withStatus("Non_urgent").build()));

        // Substring matches name but does not match status
        predicate = new StatusStartsWithSubstringPredicate("Alice");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").withStatus("URGENT").build()));

        // Substring matches phone but does not match status
        predicate = new StatusStartsWithSubstringPredicate("91234567");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").withStatus("URGENT").build()));

        // Substring matches email but does not match status
        predicate = new StatusStartsWithSubstringPredicate("alice@email.com");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").withStatus("URGENT").build()));

        // Substring matches address but does not match status
        predicate = new StatusStartsWithSubstringPredicate("Main Street");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").withStatus("URGENT").build()));

        // Substring matches remark but does not match status
        predicate = new StatusStartsWithSubstringPredicate("Doctor");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").withStatus("URGENT").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        StatusStartsWithSubstringPredicate predicate = new StatusStartsWithSubstringPredicate(substring);

        String expected = StatusStartsWithSubstringPredicate.class.getCanonicalName()
                + "{substring=" + substring.toUpperCase() + "}";
        assertEquals(expected, predicate.toString());
    }
}
