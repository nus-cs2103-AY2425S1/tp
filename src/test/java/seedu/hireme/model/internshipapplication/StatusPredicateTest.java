package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.TypicalInternshipApplications.APPLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.BYTEDANCE;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOOGLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOVTECH;

import org.junit.jupiter.api.Test;

public class StatusPredicateTest {

    @Test
    public void equals() {
        Status firstStatus = Status.PENDING;
        Status secondStatus = Status.ACCEPTED;

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
        StatusPredicate predicate = new StatusPredicate(Status.PENDING);
        assertTrue(predicate.test(GOOGLE));

        // accepted
        predicate = new StatusPredicate(Status.ACCEPTED);
        assertTrue(predicate.test(GOVTECH));

        // rejected
        predicate = new StatusPredicate(Status.REJECTED);
        assertTrue(predicate.test(BYTEDANCE));

    }

    @Test
    public void test_nonMatchingStatus_returnsFalse() {
        StatusPredicate predicate = new StatusPredicate(Status.ACCEPTED);
        assertFalse(predicate.test(GOOGLE));

        predicate = new StatusPredicate(Status.REJECTED);
        assertFalse(predicate.test(APPLE));

        predicate = new StatusPredicate(Status.PENDING);
        assertFalse(predicate.test(GOVTECH));
    }

    @Test
    public void toStringMethod() {
        String pending = "PENDING";
        StatusPredicate pendingPredicate = new StatusPredicate(Status.PENDING);
        String expected = StatusPredicate.class.getCanonicalName() + "{statusToFilterBy=" + pending + "}";
        assertEquals(expected, pendingPredicate.toString());

        String accepted = "ACCEPTED";
        StatusPredicate acceptedPredicate = new StatusPredicate(Status.ACCEPTED);
        expected = StatusPredicate.class.getCanonicalName() + "{statusToFilterBy=" + accepted + "}";
        assertEquals(expected, acceptedPredicate.toString());

        String rejected = "REJECTED";
        StatusPredicate rejectedPredicate = new StatusPredicate(Status.REJECTED);
        expected = StatusPredicate.class.getCanonicalName() + "{statusToFilterBy=" + rejected + "}";
        assertEquals(expected, rejectedPredicate.toString());
    }
}
