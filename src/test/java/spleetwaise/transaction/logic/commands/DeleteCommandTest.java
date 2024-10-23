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
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.TypicalIndexes;
import spleetwaise.transaction.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private final TransactionBookModel transactionBookModel = new TransactionBookModelManager(
            TypicalTransactions.getTypicalTransactionBook()
    );

    @BeforeEach
    void setup() {
        CommonModel.initialise(null, transactionBookModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Transaction transactionToDelete = transactionBookModel.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete
        );

        TransactionBookModelManager expectedModel = new TransactionBookModelManager(
                transactionBookModel.getTransactionBook());
        expectedModel.deleteTransaction(transactionToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCommand, transactionBookModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(transactionBookModel.getFilteredTransactionList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(
                deleteCommand, transactionBookModel, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showTransactionAtIndex(transactionBookModel, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Transaction transactionToDelete = transactionBookModel.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete
        );

        TransactionBookModelManager expectedModel = new TransactionBookModelManager(
                transactionBookModel.getTransactionBook());
        CommandTestUtil.showTransactionAtIndex(expectedModel, TypicalIndexes.INDEX_FIRST_TRANSACTION);
        expectedModel.deleteTransaction(transactionToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCommand, transactionBookModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showTransactionAtIndex(transactionBookModel, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of transaction book list
        assertTrue(
                outOfBoundIndex.getZeroBased() < transactionBookModel.getTransactionBook().getTransactionList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(
                deleteCommand, transactionBookModel, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different transaction -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
