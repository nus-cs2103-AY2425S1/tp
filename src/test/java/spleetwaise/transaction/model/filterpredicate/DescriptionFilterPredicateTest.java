package spleetwaise.transaction.model.filterpredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class DescriptionFilterPredicateTest {

    private static final Description testDescription = TypicalTransactions.SEANOWESME.getDescription();

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new DescriptionFilterPredicate(null));
    }

    @Test
    public void execute_description_success() {
        DescriptionFilterPredicate pred = new DescriptionFilterPredicate(testDescription);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void execute_descriptionDifferentCase_success() {
        Description testDescriptionDifferentCase = new Description(testDescription.toString().toLowerCase());
        DescriptionFilterPredicate pred = new DescriptionFilterPredicate(testDescriptionDifferentCase);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        DescriptionFilterPredicate pred = new DescriptionFilterPredicate(testDescription);
        DescriptionFilterPredicate pred2 = new DescriptionFilterPredicate(testDescription);

        assertEquals(pred, pred);
        assertEquals(pred, pred2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        DescriptionFilterPredicate pred = new DescriptionFilterPredicate(testDescription);
        DescriptionFilterPredicate pred2 = new DescriptionFilterPredicate(
                TypicalTransactions.BOBOWES.getDescription());

        assertNotEquals(pred, pred2);
        assertNotEquals(pred, null);
        assertNotEquals(pred, new Object());
    }

    @Test
    public void toString_success() {
        DescriptionFilterPredicate pred = new DescriptionFilterPredicate(testDescription);
        assertEquals("spleetwaise.transaction.model.filterpredicate.DescriptionFilterPredicate{"
                        + "description=Sean owes me a lot for a landed property in Sentosa}",
                pred.toString()
        );
    }
}
