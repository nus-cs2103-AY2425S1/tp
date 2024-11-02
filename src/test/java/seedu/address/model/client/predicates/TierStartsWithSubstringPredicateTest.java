package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class TierStartsWithSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        TierStartsWithSubstringPredicate firstPredicate =
                new TierStartsWithSubstringPredicate(firstPredicateSubstring);
        TierStartsWithSubstringPredicate secondPredicate =
                new TierStartsWithSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TierStartsWithSubstringPredicate firstPredicateCopy =
                new TierStartsWithSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tierStartsWithSubstring_returnsTrue() {
        // Full tier match
        TierStartsWithSubstringPredicate predicate = new TierStartsWithSubstringPredicate("gold");
        assertTrue(predicate.test(new ClientBuilder().withTier("gold").build()));

        // Partial tier match
        predicate = new TierStartsWithSubstringPredicate("go");
        assertTrue(predicate.test(new ClientBuilder().withTier("gold").build()));

        // Mixed-case substring
        predicate = new TierStartsWithSubstringPredicate("gOlD");
        assertTrue(predicate.test(new ClientBuilder().withTier("GOLD").build()));
    }

    @Test
    public void test_emptySubstring_throwsException() {
        TierStartsWithSubstringPredicate predicate = new TierStartsWithSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> predicate.test(
                new ClientBuilder().withTier("GOLD").build()));
    }

    @Test
    public void test_tierDoesNotStartWithSubstring_returnsFalse() {
        // Non-matching substring
        TierStartsWithSubstringPredicate predicate = new TierStartsWithSubstringPredicate("gold");
        assertFalse(predicate.test(new ClientBuilder().withTier("silver").build()));

        // Substring matches name but does not match tier
        predicate = new TierStartsWithSubstringPredicate("Alice");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").build()));

        // Substring matches phone but does not match tier
        predicate = new TierStartsWithSubstringPredicate("91234567");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches email but does not match tier
        predicate = new TierStartsWithSubstringPredicate("alice@email.com");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").build()));

        // Substring matches address but does not match tier
        predicate = new TierStartsWithSubstringPredicate("Main Street");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").build()));

        // Substring matches remark but does not match tier
        predicate = new TierStartsWithSubstringPredicate("Doctor");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").withTier("GOLD").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        TierStartsWithSubstringPredicate predicate = new TierStartsWithSubstringPredicate(substring);

        String expected = TierStartsWithSubstringPredicate.class.getCanonicalName()
                + "{substring=" + substring.toUpperCase() + "}";
        assertEquals(expected, predicate.toString());
    }
}
