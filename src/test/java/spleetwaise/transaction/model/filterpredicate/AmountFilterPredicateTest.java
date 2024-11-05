package spleetwaise.transaction.model.filterpredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class AmountFilterPredicateTest {

    private static final Amount testAmount = TypicalTransactions.SEANOWESME.getAmount();

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new AmountFilterPredicate(null));
    }

    @Test
    public void execute_amount_success() {
        AmountFilterPredicate pred = new AmountFilterPredicate(testAmount);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        AmountFilterPredicate pred = new AmountFilterPredicate(testAmount);
        AmountFilterPredicate pred2 = new AmountFilterPredicate(testAmount);

        assertEquals(pred, pred);
        assertEquals(pred, pred2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        AmountFilterPredicate pred = new AmountFilterPredicate(testAmount);
        AmountFilterPredicate pred2 = new AmountFilterPredicate(TypicalTransactions.BOBOWES.getAmount());

        assertNotEquals(pred, pred2);
        assertNotEquals(pred, null);
        assertNotEquals(pred, new Object());
    }

    @Test
    public void toString_success() {
        AmountFilterPredicate pred = new AmountFilterPredicate(testAmount);
        assertEquals("spleetwaise.transaction.model.filterpredicate.AmountFilterPredicate{"
                        + "amount=9999999999.99}",
                pred.toString()
        );
    }
}
