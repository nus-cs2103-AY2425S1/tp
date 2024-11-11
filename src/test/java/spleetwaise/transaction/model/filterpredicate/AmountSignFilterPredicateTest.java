package spleetwaise.transaction.model.filterpredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.testutil.TypicalTransactions;

public class AmountSignFilterPredicateTest {

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new AmountSignFilterPredicate(null));
    }

    @Test
    public void test_amountSign_success() {
        AmountSignFilterPredicate posPred = new AmountSignFilterPredicate(
                AmountSignFilterPredicate.POSITIVE_SIGN);

        assertTrue(posPred.test(TypicalTransactions.SEANOWESME));
        assertFalse(posPred.test(TypicalTransactions.CARLBUYING));

        AmountSignFilterPredicate negPred = new AmountSignFilterPredicate(
                AmountSignFilterPredicate.NEGATIVE_SIGN);
        assertTrue(negPred.test(TypicalTransactions.CARLBUYING));
        assertFalse(negPred.test(TypicalTransactions.SEANOWESME));
    }

    @Test
    public void isValidSign_validInput_returnsTrue() {
        assertTrue(AmountSignFilterPredicate.isValidSign(AmountSignFilterPredicate.POSITIVE_SIGN));
        assertTrue(AmountSignFilterPredicate.isValidSign(AmountSignFilterPredicate.NEGATIVE_SIGN));
    }

    @Test
    public void isValidSign_invalidInput_returnsFalse() {
        assertFalse(AmountSignFilterPredicate.isValidSign(""));
        assertFalse(AmountSignFilterPredicate.isValidSign(" "));
        assertFalse(AmountSignFilterPredicate.isValidSign("neg"));
        assertFalse(AmountSignFilterPredicate.isValidSign("pos"));
        assertFalse(AmountSignFilterPredicate.isValidSign("pos "));
        assertFalse(AmountSignFilterPredicate.isValidSign(" neg"));
        assertFalse(AmountSignFilterPredicate.isValidSign("invalid"));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        AmountSignFilterPredicate pred = new AmountSignFilterPredicate(
                AmountSignFilterPredicate.POSITIVE_SIGN);
        AmountSignFilterPredicate pred2 = new AmountSignFilterPredicate(
                AmountSignFilterPredicate.POSITIVE_SIGN);

        assertEquals(pred, pred);
        assertEquals(pred, pred2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        AmountSignFilterPredicate posPred = new AmountSignFilterPredicate(
                AmountSignFilterPredicate.POSITIVE_SIGN);
        AmountSignFilterPredicate negPred = new AmountSignFilterPredicate(
                AmountSignFilterPredicate.NEGATIVE_SIGN);

        assertNotEquals(posPred, negPred);
        assertNotEquals(posPred, null);
        assertNotEquals(posPred, new Object());
    }

    @Test
    public void toString_success() {
        AmountSignFilterPredicate posPred = new AmountSignFilterPredicate(
                AmountSignFilterPredicate.POSITIVE_SIGN);
        assertEquals("spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate{sign=true}",
                posPred.toString()
        );
        AmountSignFilterPredicate negPred = new AmountSignFilterPredicate(
                AmountSignFilterPredicate.NEGATIVE_SIGN);
        assertEquals("spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate{sign=false}",
                negPred.toString()
        );
    }
}
