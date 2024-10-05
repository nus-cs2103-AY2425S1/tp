package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NricMatchesPredicateTest {

    @Test
    public void equals() {
        Nric nric = new Nric("T1234567A");
        Nric nric2 = new Nric("T1234567B");
        NricMatchesPredicate firstPredicate = new NricMatchesPredicate(nric);
        NricMatchesPredicate secondPredicate = new NricMatchesPredicate(nric);
        NricMatchesPredicate thirdPredicate = new NricMatchesPredicate(nric2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different nric -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_nricMatchesPredicate_returnsTrue() {
        //Same Nric
        Nric nric = new Nric("T1234567A");
        NricMatchesPredicate nricPredicate = new NricMatchesPredicate(nric);
        assertTrue(nricPredicate.test(new PersonBuilder().withNric("T1234567A").build()));
    }

    @Test
    public void test_nricDoesNotMatchPredicate_returnsFalse() {
        //Different Nric
        Nric nric2 = new Nric("T1234567B");
        NricMatchesPredicate nricPredicate2 = new NricMatchesPredicate(nric2);
        assertFalse(nricPredicate2.test(new PersonBuilder().withNric("T1234567A").build()));
    }

    @Test
    public void toStringMethod() {

        Nric nric = new Nric("T1234567A");
        NricMatchesPredicate predicate = new NricMatchesPredicate(nric);
        String expected = NricMatchesPredicate.class.getCanonicalName() + "{nric=" + nric + "}";

        assertEquals(expected, predicate.toString());
    }
}

