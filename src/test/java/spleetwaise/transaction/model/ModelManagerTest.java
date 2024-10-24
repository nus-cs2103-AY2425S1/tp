package spleetwaise.transaction.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

public class ModelManagerTest {

    private static final Person testPerson = TypicalPersons.ALICE;
    private static final Amount testAmount = new Amount("1.23");
    private static final Description testDescription = new Description("1");
    private static final Date testDate = new Date("01012024");
    private static final Set<Category> testCategories = Collections.emptySet();

    private static final Transaction testTxn = new Transaction(
            testPerson, testAmount, testDescription, testDate, testCategories);
    private static final Transaction testTxn2 = new Transaction(
            testPerson, testAmount, new Description("2"), testDate, testCategories);
    private static final Transaction testTxn3 = new Transaction(
            testPerson, testAmount, new Description("3"), testDate, testCategories);


    private final TransactionBookModel transactionModel = new TransactionBookModelManager();
    private final AddressBookModel addressBookModel = new AddressBookModelManager();

    @Test
    void transactionModelIsNotAddressBookModel() {
        assert !(transactionModel.equals(addressBookModel));
        assert !(addressBookModel.equals(transactionModel));
    }

    @Test
    public void constructor_emptyParam_success() {
        TransactionBookModelManager manager = new TransactionBookModelManager();
        assertEquals(new TransactionBook(), manager.getTransactionBook());
    }

    @Test
    public void constructor_nullParams_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new TransactionBookModelManager(null));
    }

    @Test
    public void constructor_useExistingBook_success() {
        TransactionBook transactionBook = new TransactionBook();
        TransactionBookModelManager manager = new TransactionBookModelManager(transactionBook);

        assertEquals(transactionBook, manager.getTransactionBook());
    }

    @Test
    public void setTransactionBook_null_exceptionThrown() {
        TransactionBookModelManager manager = new TransactionBookModelManager();

        assertThrows(NullPointerException.class, () -> manager.setTransactionBook(null));
    }

    @Test
    public void setTransactionBook_validParams_success() {
        TransactionBook existingTransactionBook = new TransactionBook();
        existingTransactionBook.addTransaction(testTxn);
        TransactionBookModelManager manager = new TransactionBookModelManager(existingTransactionBook);

        TransactionBook newTransactionBook = new TransactionBook();
        newTransactionBook.addTransaction(testTxn2);
        manager.setTransactionBook(newTransactionBook);

        assertFalse(manager.getTransactionBook().getTransactionList().contains(testTxn));
        assertTrue(manager.getTransactionBook().getTransactionList().contains(testTxn2));
    }

    @Test
    public void getTransactionBook_success() {
        TransactionBook existingTransactionBook = new TransactionBook();
        existingTransactionBook.addTransaction(testTxn);
        TransactionBookModelManager manager = new TransactionBookModelManager(existingTransactionBook);

        assertEquals(existingTransactionBook, manager.getTransactionBook());
    }

    @Test
    public void addTransaction_success() {
        TransactionBookModelManager manager = new TransactionBookModelManager();

        manager.addTransaction(testTxn);

        assertTrue(manager.getTransactionBook().getTransactionList().contains(testTxn));
    }

    @Test
    public void addTransaction_null_exceptionThrown() {
        TransactionBookModelManager manager = new TransactionBookModelManager();

        assertThrows(NullPointerException.class, () -> manager.addTransaction(null));

        assertEquals(Collections.emptyList(), manager.getTransactionBook().getTransactionList());
    }

    @Test
    public void hasTransaction_contains_returnsTrue() {
        TransactionBookModelManager manager = new TransactionBookModelManager();
        manager.addTransaction(testTxn);

        assertTrue(manager.hasTransaction(testTxn));
    }

    @Test
    public void hasTransaction_doNotContain_returnsFalse() {
        TransactionBookModelManager manager = new TransactionBookModelManager();

        assertFalse(manager.hasTransaction(testTxn));
    }

    @Test
    public void getFilteredTransactionList_noPredicate_shouldEqual() {
        TransactionBookModelManager manager = new TransactionBookModelManager();
        manager.addTransaction(testTxn);
        manager.addTransaction(testTxn2);

        assertTrue(manager.getFilteredTransactionList().contains(testTxn));
        assertTrue(manager.getFilteredTransactionList().contains(testTxn2));
    }

    @Test
    public void updateFilteredTransactionList_withPredicate_shouldFilter() {
        TransactionBookModelManager manager = new TransactionBookModelManager();
        manager.addTransaction(testTxn);
        manager.addTransaction(testTxn2);

        Predicate<Transaction> pred = (t) -> t.getDescription().equals(testDescription);
        manager.updateFilteredTransactionList(pred);

        assertTrue(manager.getFilteredTransactionList().contains(testTxn));
        assertFalse(manager.getFilteredTransactionList().contains(testTxn2));
    }

    @Test
    public void equals_null_returnsFalse() {
        TransactionBookModelManager manager = new TransactionBookModelManager();

        assertNotEquals(null, manager);
    }

    @Test
    public void equals_notSame_returnsFalse() {
        TransactionBookModelManager manager1 = new TransactionBookModelManager();
        manager1.addTransaction(testTxn);
        TransactionBookModelManager manager2 = new TransactionBookModelManager();
        manager2.addTransaction(testTxn2);

        assertNotEquals(manager1, manager2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        TransactionBookModelManager manager1 = new TransactionBookModelManager();
        manager1.addTransaction(testTxn2);
        manager1.addTransaction(testTxn3);
        Predicate<Transaction> pred1 = (t) -> t.getDescription().equals(new Description("2"));
        manager1.updateFilteredTransactionList(pred1);

        TransactionBookModelManager manager2 = new TransactionBookModelManager();
        manager2.addTransaction(testTxn2);
        manager2.addTransaction(testTxn3);
        Predicate<Transaction> pred2 = (t) -> t.getDescription().equals(new Description("3"));
        manager1.updateFilteredTransactionList(pred2);

        assertNotEquals(manager1, manager2);
    }

    @Test
    public void equals_same_returnsTrue() {
        TransactionBookModelManager manager1 = new TransactionBookModelManager();
        manager1.addTransaction(testTxn);

        assertEquals(manager1, manager1);

        TransactionBookModelManager manager2 = new TransactionBookModelManager();
        manager2.addTransaction(testTxn);

        assertEquals(manager1, manager2);
    }


}
