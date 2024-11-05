package spleetwaise.transaction.model.filterpredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class DateFilterPredicateTest {

    private static final Date testDate = TypicalTransactions.SEANOWESME.getDate();

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new DateFilterPredicate(null));
    }

    @Test
    public void execute_date_success() {
        DateFilterPredicate pred = new DateFilterPredicate(testDate);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        DateFilterPredicate pred = new DateFilterPredicate(testDate);
        DateFilterPredicate pred2 = new DateFilterPredicate(testDate);

        assertEquals(pred, pred);
        assertEquals(pred, pred2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        DateFilterPredicate pred = new DateFilterPredicate(testDate);
        DateFilterPredicate pred2 = new DateFilterPredicate(TypicalTransactions.BOBOWES.getDate());

        assertNotEquals(pred, pred2);
        assertNotEquals(pred, null);
        assertNotEquals(pred, new Object());
    }

    @Test
    public void toString_success() {
        DateFilterPredicate pred = new DateFilterPredicate(testDate);
        assertEquals("spleetwaise.transaction.model.filterpredicate.DateFilterPredicate{date=10/10/2024}",
                pred.toString()
        );
    }
}
