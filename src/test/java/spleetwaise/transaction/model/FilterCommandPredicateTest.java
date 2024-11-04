package spleetwaise.transaction.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.person.Person;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class FilterCommandPredicateTest {
    private static final Person testPerson = TypicalTransactions.SEANOWESME.getPerson();
    private static final Amount testAmount = TypicalTransactions.SEANOWESME.getAmount();
    private static final Description testDescription = TypicalTransactions.SEANOWESME.getDescription();
    private static final Date testDate = TypicalTransactions.SEANOWESME.getDate();
    private static final Status tesStatus = TypicalTransactions.SEANOWESME.getStatus();

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new FilterCommandPredicate(null, null, null, null, null));
    }

    @Test
    public void execute_personOnly_success() {
        FilterCommandPredicate pred = new FilterCommandPredicate(testPerson, null, null, null, null);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void execute_amountOnly_success() {
        FilterCommandPredicate pred = new FilterCommandPredicate(null, testAmount, null, null, null);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void execute_descriptionOnly_success() {
        FilterCommandPredicate pred = new FilterCommandPredicate(null, null, testDescription, null, null);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void execute_descriptionDifferentCaseOnly_success() {
        FilterCommandPredicate pred = new FilterCommandPredicate(null, null,
                new Description(testDescription.toString().toLowerCase()), null, null
        );

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void execute_dateOnly_success() {
        FilterCommandPredicate pred = new FilterCommandPredicate(null, null, null, testDate, null);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void execute_statusOnly_success() {
        FilterCommandPredicate pred = new FilterCommandPredicate(null, null, null, null, tesStatus);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void execute_allParams_success() {
        FilterCommandPredicate pred = new FilterCommandPredicate(testPerson, testAmount, testDescription,
                testDate, tesStatus);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        FilterCommandPredicate pred1 = new FilterCommandPredicate(testPerson, testAmount, testDescription,
                testDate, tesStatus);
        FilterCommandPredicate pred2 = new FilterCommandPredicate(testPerson, testAmount, testDescription,
                testDate, tesStatus);

        assertEquals(pred1, pred1);
        assertEquals(pred1, pred2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        FilterCommandPredicate pred1 = new FilterCommandPredicate(null, testAmount, testDescription,
                testDate, tesStatus);
        FilterCommandPredicate pred2 = new FilterCommandPredicate(testPerson, null, testDescription,
                testDate, tesStatus);
        FilterCommandPredicate pred3 = new FilterCommandPredicate(testPerson, testAmount, null,
                testDate, tesStatus);
        FilterCommandPredicate pred4 = new FilterCommandPredicate(testPerson, testAmount, testDescription,
                null, tesStatus);
        FilterCommandPredicate pred5 = new FilterCommandPredicate(testPerson, testAmount, testDescription,
                testDate, null);

        assertNotEquals(pred1, pred2);
        assertNotEquals(pred1, pred3);
        assertNotEquals(pred1, pred4);
        assertNotEquals(pred1, pred5);
        assertNotEquals(pred2, pred3);
        assertNotEquals(pred2, pred4);
        assertNotEquals(pred2, pred5);
        assertNotEquals(pred3, pred4);
        assertNotEquals(pred3, pred5);
        assertNotEquals(pred4, pred5);
    }

    @Test
    public void equals_genericObject_returnsFalse() {
        FilterCommandPredicate pred = new FilterCommandPredicate(testPerson, testAmount, testDescription,
                testDate, tesStatus);

        assertNotEquals(new Object(), pred);
    }

    @Test
    public void equals_null_returnsFalse() {
        FilterCommandPredicate pred = new FilterCommandPredicate(testPerson, testAmount, testDescription,
                testDate, tesStatus);

        assertNotEquals(null, pred);
    }

    @Test
    public void toString_success() {
        FilterCommandPredicate pred = new FilterCommandPredicate(null, null, testDescription, null, null);
        assertEquals(
                "spleetwaise.transaction.model.FilterCommandPredicate{contact=null, amount=null, "
                        + "description=Sean owes me a lot for a landed property in Sentosa, date=null, status=null}",
                pred.toString()
        );
    }
}
