package spleetwaise.transaction.model.filterpredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class StatusFilterPredicateTest {

    private static final Status testStatus = TypicalTransactions.SEANOWESME.getStatus();

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new StatusFilterPredicate(null));
    }

    @Test
    public void execute_statusOnly_success() {
        StatusFilterPredicate pred = new StatusFilterPredicate(testStatus);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        StatusFilterPredicate pred = new StatusFilterPredicate(testStatus);
        StatusFilterPredicate pred2 = new StatusFilterPredicate(testStatus);

        assertEquals(pred, pred);
        assertEquals(pred, pred2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        StatusFilterPredicate pred = new StatusFilterPredicate(testStatus);
        StatusFilterPredicate pred2 = new StatusFilterPredicate(TypicalTransactions.BOBOWES.getStatus());

        assertNotEquals(pred, pred2);
        assertNotEquals(pred, null);
        assertNotEquals(pred, new Object());
    }

    @Test
    public void toString_success() {
        StatusFilterPredicate pred = new StatusFilterPredicate(testStatus);
        assertEquals("spleetwaise.transaction.model.filterpredicate.StatusFilterPredicate{status=Not Done}",
                pred.toString()
        );
    }
}
