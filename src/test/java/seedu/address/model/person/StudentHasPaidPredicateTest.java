package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.predicate.StudentHasPaidPredicate;
import seedu.address.testutil.PersonBuilder;

public class StudentHasPaidPredicateTest {
    @Test
    public void equals() {
        StudentHasPaidPredicate predicateHasPaid = new StudentHasPaidPredicate(true);
        StudentHasPaidPredicate predicateHasNotPaid = new StudentHasPaidPredicate(false);

        // same object -> returns true
        assertTrue(predicateHasPaid.equals(predicateHasPaid));
        assertTrue(predicateHasNotPaid.equals(predicateHasNotPaid));

        // same value -> returns true
        StudentHasPaidPredicate predicateHasPaidCopy = new StudentHasPaidPredicate(true);
        StudentHasPaidPredicate predicateHasNotPaidCopy = new StudentHasPaidPredicate(false);
        assertTrue(predicateHasPaid.equals(predicateHasPaidCopy));
        assertTrue(predicateHasNotPaid.equals(predicateHasNotPaidCopy));

        // different type -> returns false
        assertFalse(predicateHasPaid.equals(1));
        assertFalse(predicateHasNotPaid.equals(1));

        // null -> returns false
        assertFalse(predicateHasPaid.equals(null));
        assertFalse(predicateHasNotPaid.equals(null));

        // different value -> returns false
        assertFalse(predicateHasPaid.equals(predicateHasNotPaid));
    }

    @Test
    public void test_studentHasPaid_returnsTrue() {
        StudentHasPaidPredicate predicate = new StudentHasPaidPredicate(true);
        assertTrue(predicate.test(new PersonBuilder().withPayment("0").build()));
    }

    @Test
    public void test_studentHasPaid_returnsFalse() {
        StudentHasPaidPredicate predicate = new StudentHasPaidPredicate(false);
        assertTrue(predicate.test(new PersonBuilder().withPayment("200").build()));
    }

    @Test
    public void toStringMethod() {
        StudentHasPaidPredicate predicateHasPaid = new StudentHasPaidPredicate(true);
        StudentHasPaidPredicate predicateHasNotPaid = new StudentHasPaidPredicate(false);

        String expectedHasPaid = StudentHasPaidPredicate.class.getCanonicalName() + "{payment up to date=" + true
                + "}";
        assertEquals(expectedHasPaid, predicateHasPaid.toString());
        String expectedHasNotPaid =
                StudentHasPaidPredicate.class.getCanonicalName() + "{payment up to date=" + false + "}";
        assertEquals(expectedHasNotPaid, predicateHasNotPaid.toString());
    }
}
