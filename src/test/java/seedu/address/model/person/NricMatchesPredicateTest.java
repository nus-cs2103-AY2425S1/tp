package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.NricMatchesPredicate;
import seedu.address.testutil.PersonBuilder;

public class NricMatchesPredicateTest {

    @Test
    public void equals() {
        String firstNric = "S1234567A";
        String secondNric = "S7654321Z";

        NricMatchesPredicate firstPredicate = new NricMatchesPredicate(firstNric);
        NricMatchesPredicate secondPredicate = new NricMatchesPredicate(secondNric);

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same NRIC -> returns true
        NricMatchesPredicate firstPredicateCopy = new NricMatchesPredicate(firstNric);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Different NRIC -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nricMatches_returnsTrue() {
        NricMatchesPredicate predicate = new NricMatchesPredicate("S1234567A");
        Person person = new PersonBuilder().withNric("S1234567A").build();

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_caseInsensitive_returnsTrue() {
        NricMatchesPredicate predicate = new NricMatchesPredicate("s1234567a");
        Person person = new PersonBuilder().withNric("S1234567A").build();

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_nricDoesNotMatch_returnsFalse() {
        NricMatchesPredicate predicate = new NricMatchesPredicate("S0000000X");
        Person person = new PersonBuilder().withNric("S1234567A").build();

        assertFalse(predicate.test(person));
    }
}
