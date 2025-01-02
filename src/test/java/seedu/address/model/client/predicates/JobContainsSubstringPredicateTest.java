package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class JobContainsSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        JobContainsSubstringPredicate firstPredicate =
                new JobContainsSubstringPredicate(firstPredicateSubstring);
        JobContainsSubstringPredicate secondPredicate =
                new JobContainsSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobContainsSubstringPredicate firstPredicateCopy =
                new JobContainsSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobContainsSubstring_returnsTrue() {
        // Partial job match
        JobContainsSubstringPredicate predicate = new JobContainsSubstringPredicate("Engineer");
        assertTrue(predicate.test(new ClientBuilder().withJob("Software Engineer").build()));

        // Full job match
        predicate = new JobContainsSubstringPredicate("Software Engineer");
        assertTrue(predicate.test(new ClientBuilder().withJob("Software Engineer").build()));

        // Mixed-case substring
        predicate = new JobContainsSubstringPredicate("sOfTwArE eNginEeR");
        assertTrue(predicate.test(new ClientBuilder().withJob("Software Engineer").build()));
    }

    @Test
    public void test_emptySubstring_throwsException() {
        JobContainsSubstringPredicate predicate = new JobContainsSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> predicate.test(
                new ClientBuilder().withJob("Software Engineer").build()));
    }

    @Test
    public void test_jobDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        JobContainsSubstringPredicate predicate = new JobContainsSubstringPredicate("Doctor");
        assertFalse(predicate.test(new ClientBuilder().withJob("Engineer").build()));

        // Substring has a single matching word but substring does not match job
        predicate = new JobContainsSubstringPredicate("Software Engineer");
        assertFalse(predicate.test(new ClientBuilder().withJob("Electrical Engineer").build()));

        // Substring matches name but does not match job
        predicate = new JobContainsSubstringPredicate("Alice");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches phone but does not match job
        predicate = new JobContainsSubstringPredicate("91234567");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches email but does not match job
        predicate = new JobContainsSubstringPredicate("alice@email.com");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches address but does not match job
        predicate = new JobContainsSubstringPredicate("Main Street");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches remark but does not match job
        predicate = new JobContainsSubstringPredicate("Genius");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        JobContainsSubstringPredicate predicate = new JobContainsSubstringPredicate(substring);

        String expected = JobContainsSubstringPredicate.class.getCanonicalName()
                + "{substring=" + substring.toUpperCase() + "}";
        assertEquals(expected, predicate.toString());
    }
}
