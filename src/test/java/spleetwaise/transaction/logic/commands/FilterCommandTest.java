package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static spleetwaise.transaction.testutil.TransactionBuilder.DEFAULT_CATEGORY;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.model.FilterCommandPredicate;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.filterpredicate.AmountFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.CategoryFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.DateFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.DescriptionFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.PersonFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.StatusFilterPredicate;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class FilterCommandTest {

    private static final Person testPerson = TypicalTransactions.SEANOWESME.getPerson();
    private static final Amount testAmount = TypicalTransactions.SEANOWESME.getAmount();
    private static final Description testDescription = TypicalTransactions.SEANOWESME.getDescription();
    private static final Date testDate = TypicalTransactions.SEANOWESME.getDate();
    private static final Status testStatus = TypicalTransactions.SEANOWESME.getStatus();
    private static final Category testCategory = DEFAULT_CATEGORY;
    private static final Predicate<Transaction> testPersonPred = new PersonFilterPredicate(testPerson);
    private static final Predicate<Transaction> testAmountPred = new AmountFilterPredicate(testAmount);
    private static final Predicate<Transaction> testDescriptionPred = new DescriptionFilterPredicate(testDescription);
    private static final Predicate<Transaction> testDatePred = new DateFilterPredicate(testDate);
    private static final Predicate<Transaction> testStatusPred = new StatusFilterPredicate(testStatus);
    private static final Predicate<Transaction> testCategoryPred = new CategoryFilterPredicate(testCategory);
    private static final AddressBookModel abModel = new AddressBookModelManager();
    private static final TransactionBookModel txnModel = new TransactionBookModelManager();

    @BeforeAll
    public static void setUp() {
        abModel.setAddressBook(TypicalPersons.getTypicalAddressBook());
        txnModel.setTransactionBook(TypicalTransactions.getTypicalTransactionBook());
        CommonModelManager.initialise(abModel, txnModel);
    }

    @BeforeEach
    public void setUpEach() {
        txnModel.updateFilteredTransactionList(TransactionBookModel.PREDICATE_SHOW_ALL_TXNS);
        assertTrue(txnModel.hasTransaction(TypicalTransactions.SEANOWESME));
        assertTrue(txnModel.hasTransaction(TypicalTransactions.BOBOWES));
    }

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new FilterCommand(null));
    }

    @Test
    public void execute_allParams_success() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        subPredicates.add(testAmountPred);
        subPredicates.add(testDescriptionPred);
        subPredicates.add(testDatePred);
        subPredicates.add(testStatusPred);
        subPredicates.add(testCategoryPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);

        FilterCommand cmd = new FilterCommand(pred);
        CommandResult cmdRes = assertDoesNotThrow(cmd::execute);

        assertEquals(FilterCommand.MESSAGE_SUCCESS, cmdRes.getFeedbackToUser());
        assertEquals(1, txnModel.getFilteredTransactionList().size());
        assertTrue(txnModel.getFilteredTransactionList().contains(TypicalTransactions.SEANOWESME));
        assertFalse(txnModel.getFilteredTransactionList().contains(TypicalTransactions.BOBOWES));
    }

    @Test
    public void equals_sameTransaction_returnsTrue() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        subPredicates.add(testAmountPred);
        FilterCommandPredicate pred1 = new FilterCommandPredicate(subPredicates);

        ArrayList<Predicate<Transaction>> subPredicates2 = new ArrayList<>();
        subPredicates2.add(testPersonPred);
        subPredicates2.add(testAmountPred);
        FilterCommandPredicate pred2 = new FilterCommandPredicate(subPredicates2);

        FilterCommand cmd1 = new FilterCommand(pred1);
        FilterCommand cmd2 = new FilterCommand(pred1);
        FilterCommand cmd3 = new FilterCommand(pred2);

        assertEquals(cmd1, cmd1);
        assertEquals(cmd1, cmd2);
        assertEquals(cmd1, cmd3);
    }

    @Test
    public void equals_diffTransaction_returnsFalse() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        FilterCommandPredicate pred1 = new FilterCommandPredicate(subPredicates);
        ArrayList<Predicate<Transaction>> subPredicates2 = new ArrayList<>();
        subPredicates2.add(testDescriptionPred);
        FilterCommandPredicate pred2 = new FilterCommandPredicate(subPredicates2);
        FilterCommand cmd1 = new FilterCommand(pred1);
        FilterCommand cmd2 = new FilterCommand(pred2);

        assertNotEquals(cmd1, cmd2);
    }

    @Test
    public void equals_genericObject_returnsFalse() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);
        FilterCommand cmd = new FilterCommand(pred);

        assertNotEquals(new Object(), cmd);
    }

    @Test
    public void equals_null_returnsFalse() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);
        FilterCommand cmd = new FilterCommand(pred);

        assertNotEquals(null, cmd);
    }

    @Test
    public void toString_success() {
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testDescriptionPred);
        FilterCommandPredicate pred = new FilterCommandPredicate(subPredicates);
        FilterCommand cmd = new FilterCommand(pred);
        assertEquals("spleetwaise.transaction.logic.commands.FilterCommand{"
                        + "predicate=spleetwaise.transaction.model.FilterCommandPredicate{"
                        + "pred0=spleetwaise.transaction.model.filterpredicate.DescriptionFilterPredicate{"
                        + "description=Sean owes me a lot for a landed property in Sentosa}}}",
                cmd.toString()
        );
    }
}

