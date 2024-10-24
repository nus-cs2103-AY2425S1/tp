package spleetwaise.transaction.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.model.transaction.exceptions.DuplicateTransactionException;
import spleetwaise.transaction.testutil.TransactionBuilder;

public class TransactionBookTest {

    private static final Person testPerson = TypicalPersons.ALICE;
    private static final Amount testAmount = new Amount("1.23");
    private static final Description testDescription = new Description("description");
    private static final Date testDate = new Date("01012024");
    private static final Set<Category> testCategories = new HashSet<>(List.of(new Category("FOOD")));

    private static final Transaction testTxn = new Transaction(
            testPerson, testAmount, testDescription, testDate, testCategories);
    private static final Transaction testTxn2 = new Transaction(
            testPerson, testAmount, new Description("2"), testDate, testCategories);
    private static final Transaction testTxn3 = new Transaction(
            testPerson, testAmount, new Description("3"), testDate, testCategories);

    @Test
    public void constructor_noParams_success() {
        TransactionBook testBook = new TransactionBook();
        assertEquals(Collections.EMPTY_LIST, testBook.getTransactionList());
    }

    @Test
    public void constructor_withParams_success() {
        TransactionBook testExistingBook = new TransactionBook();
        TransactionBook testBook = new TransactionBook(testExistingBook);
        assertEquals(testExistingBook.getTransactionList(), testBook.getTransactionList());
    }

    @Test
    public void containsTransaction_isInList_returnsTrue() {
        TransactionBook book = new TransactionBook();
        book.addTransaction(testTxn);

        assertTrue(book.containsTransaction(testTxn));
    }

    @Test
    public void containsTransaction_notInList_returnsFalse() {
        TransactionBook book = new TransactionBook();

        assertFalse(book.containsTransaction(testTxn));
    }

    @Test
    public void containsTransactionById_isInList_returnsTrue() {
        TransactionBook book = new TransactionBook();
        book.addTransaction(testTxn);

        assertTrue(book.containsTransactionById(testTxn));
    }

    @Test
    public void containsTransactionById_notInList_returnsFalse() {
        TransactionBook book = new TransactionBook();
        book.addTransaction(testTxn);
        Transaction txn = (new TransactionBuilder(testTxn)).withId("differentID").build();

        assertTrue(book.containsTransaction(txn));
        assertFalse(book.containsTransactionById(txn));
    }

    @Test
    public void addTransaction_noDuplicate_success() {
        TransactionBook book = new TransactionBook();

        assertDoesNotThrow(() -> book.addTransaction(testTxn));
    }

    @Test
    public void addTransaction_duplicate_exceptionThrown() {
        TransactionBook book = new TransactionBook();
        book.addTransaction(testTxn);

        assertThrows(DuplicateTransactionException.class, () -> book.addTransaction(testTxn));
    }

    @Test
    public void addTransaction_null_exceptionThrown() {
        TransactionBook book = new TransactionBook();

        assertThrows(NullPointerException.class, () -> book.addTransaction(null));
    }

    @Test
    public void removeTransaction_exists_success() {
        TransactionBook book = new TransactionBook();
        book.addTransaction(testTxn);
        assertTrue(book.removeTransaction(testTxn));
    }

    @Test
    public void removeTransaction_nonExistent_exceptionThrown() {
        TransactionBook book = new TransactionBook();
        assertFalse(book.removeTransaction(testTxn));
    }

    @Test
    public void removeTransaction_null_exceptionThrown() {
        TransactionBook book = new TransactionBook();
        assertThrows(NullPointerException.class, () -> book.addTransaction(null));
    }

    @Test
    public void setTransaction_null_exceptionThrown() {
        TransactionBook book = new TransactionBook();

        assertThrows(NullPointerException.class, () -> book.setTransactions(null));
    }

    @Test
    public void setTransaction_validParams_success() {
        TransactionBook book = new TransactionBook();
        book.addTransaction(testTxn);

        TransactionBook replacementBook = new TransactionBook();
        replacementBook.addTransaction(testTxn2);
        replacementBook.addTransaction(testTxn3);

        book.setTransactions(replacementBook);

        assertFalse(book.containsTransaction(testTxn));
        assertTrue(book.containsTransaction(testTxn2));
        assertTrue(book.containsTransaction(testTxn3));
    }

    @Test
    public void equals_isEqual_returnsTrue() {
        TransactionBook book = new TransactionBook();
        book.addTransaction(testTxn);

        assertEquals(book, book);

        TransactionBook book2 = new TransactionBook();
        book2.addTransaction(testTxn);

        assertEquals(book, book2);
    }

    @Test
    public void equals_notEqual_returnsFalse() {
        TransactionBook book = new TransactionBook();
        book.addTransaction(testTxn);

        assertNotEquals(null, book);

        TransactionBook book2 = new TransactionBook();
        book2.addTransaction(testTxn2);

        assertNotEquals(book, book2);
    }

}
