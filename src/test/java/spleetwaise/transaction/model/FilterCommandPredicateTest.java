package spleetwaise.transaction.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.person.Person;
import spleetwaise.transaction.model.filterpredicate.AmountFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.DateFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.DescriptionFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.PersonFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.StatusFilterPredicate;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class FilterCommandPredicateTest {
    private static final Person testPerson = TypicalTransactions.SEANOWESME.getPerson();
    private static final Amount testAmount = TypicalTransactions.SEANOWESME.getAmount();
    private static final Description testDescription = TypicalTransactions.SEANOWESME.getDescription();
    private static final Date testDate = TypicalTransactions.SEANOWESME.getDate();
    private static final Status testStatus = TypicalTransactions.SEANOWESME.getStatus();
    private static final Predicate<Transaction> testPersonPred = new PersonFilterPredicate(testPerson);
    private static final Predicate<Transaction> testAmountPred = new AmountFilterPredicate(testAmount);
    private static final Predicate<Transaction> testDescriptionPred = new DescriptionFilterPredicate(testDescription);
    private static final Predicate<Transaction> testDatePred = new DateFilterPredicate(testDate);
    private static final Predicate<Transaction> testStatusPred = new StatusFilterPredicate(testStatus);


    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new FilterCommandPredicate(null));
        assertThrows(NullPointerException.class, () ->
                new FilterCommandPredicate(new ArrayList<>()));
    }

    @Test
    public void execute_singleParams_success() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void execute_allParams_success() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        subPredicates.add(testAmountPred);
        subPredicates.add(testDescriptionPred);
        subPredicates.add(testDatePred);
        subPredicates.add(testStatusPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        subPredicates.add(testAmountPred);
        FilterCommandPredicate pred1 = new FilterCommandPredicate(subPredicates);

        ArrayList<Predicate<Transaction>> subPredicates2 = new ArrayList<>();
        subPredicates2.add(testPersonPred);
        subPredicates2.add(testAmountPred);
        FilterCommandPredicate pred2 = new FilterCommandPredicate(subPredicates2);

        // Test different ordering of predicates
        ArrayList<Predicate<Transaction>> subPredicates3 = new ArrayList<>();
        subPredicates3.add(testAmountPred);
        subPredicates3.add(testPersonPred);
        FilterCommandPredicate pred3 = new FilterCommandPredicate(subPredicates3);

        assertEquals(pred1, pred1);
        assertEquals(pred1, pred2);
        assertEquals(pred1, pred3);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        FilterCommandPredicate pred1 = new FilterCommandPredicate(subPredicates);
        ArrayList<Predicate<Transaction>> subPredicates2 = new ArrayList<>();
        subPredicates2.add(testDescriptionPred);
        FilterCommandPredicate pred2 = new FilterCommandPredicate(subPredicates2);

        assertNotEquals(pred1, pred2);
    }

    @Test
    public void equals_genericObject_returnsFalse() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);

        assertNotEquals(new Object(), pred);
    }

    @Test
    public void equals_null_returnsFalse() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);

        assertNotEquals(null, pred);
    }

    @Test
    public void toString_success() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testDescriptionPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);
        assertEquals("spleetwaise.transaction.model.FilterCommandPredicate{"
                        + "pred0=spleetwaise.transaction.model.filterpredicate.DescriptionFilterPredicate{"
                        + "description=Sean owes me a lot for a landed property in Sentosa}}",
                pred.toString()
        );
    }
}
