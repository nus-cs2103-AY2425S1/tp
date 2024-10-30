package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.TypicalIndexes;
import spleetwaise.transaction.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnmarkCommand}.
 */
public class UnmarkCommandTest {
    private final TransactionBookModel transactionBookModel = new TransactionBookModelManager(
            TypicalTransactions.getTypicalTransactionBook()
    );

    @BeforeEach
    void setup() {
        CommonModel.initialise(null, transactionBookModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Transaction txnToUnmark = transactionBookModel.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_SECOND_TRANSACTION.getZeroBased());
        UnmarkCommand unmarkCommand = new UnmarkCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION);
        Transaction updatedTxn = txnToUnmark.setStatus(new Status(false));

        String expectedMessage = String.format(
                UnmarkCommand.MESSAGE_UNMARK_TRANSACTION_SUCCESS,
                updatedTxn
        );

        TransactionBookModelManager expectedModel = new TransactionBookModelManager(
                transactionBookModel.getTransactionBook());
        expectedModel.setTransaction(txnToUnmark, updatedTxn);

        CommandTestUtil.assertCommandSuccess(unmarkCommand, transactionBookModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(transactionBookModel.getFilteredTransactionList().size() + 1);
        UnmarkCommand unmarkCommand = new UnmarkCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(
                unmarkCommand, transactionBookModel, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showTransactionAtIndex(transactionBookModel, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of transaction book list
        assertTrue(
                outOfBoundIndex.getZeroBased() < transactionBookModel.getTransactionBook().getTransactionList().size());

        UnmarkCommand unmarkCommand = new UnmarkCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(
                unmarkCommand, transactionBookModel, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnmarkCommand unmarkFirstCommand = new UnmarkCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        UnmarkCommand unmarkSecondCommand = new UnmarkCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertEquals(unmarkFirstCommand, unmarkFirstCommand);

        // same values -> returns true
        UnmarkCommand unmarkFirstCommandCopy = new UnmarkCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertEquals(unmarkFirstCommand, unmarkFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, unmarkFirstCommand);

        // null -> returns false
        assertNotEquals(null, unmarkFirstCommand);

        // different transaction -> returns false
        assertNotEquals(unmarkFirstCommand, unmarkSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnmarkCommand unmarkCommand = new UnmarkCommand(targetIndex);
        String expected = UnmarkCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unmarkCommand.toString());
    }
}
