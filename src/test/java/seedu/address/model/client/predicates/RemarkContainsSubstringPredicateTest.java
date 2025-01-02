package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class RemarkContainsSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        RemarkContainsSubstringPredicate firstPredicate =
                new RemarkContainsSubstringPredicate(firstPredicateSubstring);
        RemarkContainsSubstringPredicate secondPredicate =
                new RemarkContainsSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RemarkContainsSubstringPredicate firstPredicateCopy =
                new RemarkContainsSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_remarkContainsSubstring_returnsTrue() {
        // Partial remark match
        RemarkContainsSubstringPredicate predicate = new RemarkContainsSubstringPredicate("Engineer");
        assertTrue(predicate.test(new ClientBuilder().withRemark("Software Engineer").build()));

        // Full remark match
        predicate = new RemarkContainsSubstringPredicate("Software Engineer");
        assertTrue(predicate.test(new ClientBuilder().withRemark("Software Engineer").build()));

        // Mixed-case substring
        predicate = new RemarkContainsSubstringPredicate("sOfTwArE eNginEeR");
        assertTrue(predicate.test(new ClientBuilder().withRemark("Software Engineer").build()));
    }

    @Test
    public void test_emptySubstring_throwsException() {
        RemarkContainsSubstringPredicate predicate = new RemarkContainsSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> predicate.test(
                new ClientBuilder().withRemark("Software Engineer").build()));
    }

    @Test
    public void test_remarkDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        RemarkContainsSubstringPredicate predicate = new RemarkContainsSubstringPredicate("Doctor");
        assertFalse(predicate.test(new ClientBuilder().withRemark("Engineer").build()));

        // Substring has a single matching word but substring does not match remark
        predicate = new RemarkContainsSubstringPredicate("Software Engineer");
        assertFalse(predicate.test(new ClientBuilder().withRemark("Electrical Engineer").build()));

        // Substring matches name but does not match remark
        predicate = new RemarkContainsSubstringPredicate("Alice");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches phone but does not match remark
        predicate = new RemarkContainsSubstringPredicate("91234567");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches email but does not match remark
        predicate = new RemarkContainsSubstringPredicate("alice@email.com");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches address but does not match remark
        predicate = new RemarkContainsSubstringPredicate("Main Street");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches remark but does not match job
        predicate = new RemarkContainsSubstringPredicate("Doctor");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        RemarkContainsSubstringPredicate predicate = new RemarkContainsSubstringPredicate(substring);

        String expected = RemarkContainsSubstringPredicate.class.getCanonicalName()
                + "{substring=" + substring.toUpperCase() + "}";
        assertEquals(expected, predicate.toString());
    }
}
