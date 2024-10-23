package seedu.sellsavvy.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.testutil.OrderBuilder;

public class StatusEqualsKeywordPredicateTest {

    @Test
    public void equals() {
        StatusEqualsKeywordPredicate firstPredicate = new StatusEqualsKeywordPredicate(Status.COMPLETED);
        StatusEqualsKeywordPredicate secondPredicate = new StatusEqualsKeywordPredicate(Status.PENDING);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same status -> returns true
        StatusEqualsKeywordPredicate firstPredicateCopy = new StatusEqualsKeywordPredicate(Status.COMPLETED);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different status -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_statusMatchesPredicate_returnsTrue() {
        StatusEqualsKeywordPredicate predicate = new StatusEqualsKeywordPredicate(Status.COMPLETED);
        assertTrue(predicate.test(new OrderBuilder().withStatus(Status.COMPLETED).build()));
    }

    @Test
    public void test_statusDoesNotMatchPredicate_returnsFalse() {
        StatusEqualsKeywordPredicate predicate = new StatusEqualsKeywordPredicate(Status.COMPLETED);
        assertFalse(predicate.test(new OrderBuilder().withStatus(Status.PENDING).build()));
    }

    @Test
    public void toStringMethod() {
        Status status = Status.COMPLETED;
        StatusEqualsKeywordPredicate predicate = new StatusEqualsKeywordPredicate(status);

        String expected = StatusEqualsKeywordPredicate.class.getCanonicalName() + "{status=" + status + "}";
        assertEquals(expected, predicate.toString());
    }
}
