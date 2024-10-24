package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOOGLE;

import org.junit.jupiter.api.Test;

public class StatusPredicateTest {

    @Test
    public void equals() {
        String firstStatus = "pending";
        String secondStatus = "rejected";

        StatusPredicate firstPredicate = new StatusPredicate(firstStatus);
        StatusPredicate secondPredicate = new StatusPredicate(secondStatus);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StatusPredicate firstPredicateCopy = new StatusPredicate(firstStatus);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchingStatus_returnsTrue() {
        // pending
        StatusPredicate predicate = new StatusPredicate("pending");
        assertTrue(predicate.test(GOOGLE));

    }

    @Test
    public void test_notMatchingStatus_returnsFalse() {
        // Non-matching status
        StatusPredicate predicate = new StatusPredicate("accepted");
        assertFalse(predicate.test(GOOGLE));

    }

    @Test
    public void toStringMethod() {
        String pending = "PENDING";
        StatusPredicate predicate = new StatusPredicate("PENDING");

        String expected = StatusPredicate.class.getCanonicalName() + "{statusToFilterBy=" + pending + "}";
        assertEquals(expected, predicate.toString());
    }
}
