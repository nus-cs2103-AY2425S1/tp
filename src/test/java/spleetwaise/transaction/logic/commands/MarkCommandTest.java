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
 * Contains integration tests (interaction with the Model) and unit tests for {@code MarkCommand}.
 */
public class MarkCommandTest {
    private final TransactionBookModel transactionBookModel = new TransactionBookModelManager(
            TypicalTransactions.getTypicalTransactionBook()
    );

    @BeforeEach
    void setup() {
        CommonModel.initialise(null, transactionBookModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Transaction transactionToMark = transactionBookModel.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());
        MarkCommand markCommand = new MarkCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        Transaction updatedTxn = transactionToMark.setStatus(new Status(true));

        String expectedMessage = String.format(
                MarkCommand.MESSAGE_MARK_TRANSACTION_SUCCESS,
                updatedTxn
        );

        TransactionBookModelManager expectedModel = new TransactionBookModelManager(
                transactionBookModel.getTransactionBook());
        expectedModel.setTransaction(transactionToMark, updatedTxn);

        CommandTestUtil.assertCommandSuccess(markCommand, transactionBookModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(transactionBookModel.getFilteredTransactionList().size() + 1);
        MarkCommand markCommand = new MarkCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(
                markCommand, transactionBookModel, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showTransactionAtIndex(transactionBookModel, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of transaction book list
        assertTrue(
                outOfBoundIndex.getZeroBased() < transactionBookModel.getTransactionBook().getTransactionList().size());

        MarkCommand markCommand = new MarkCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(
                markCommand, transactionBookModel, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkCommand markFirstCommand = new MarkCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        MarkCommand markSecondCommand = new MarkCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertEquals(markFirstCommand, markFirstCommand);

        // same values -> returns true
        MarkCommand markFirstCommandCopy = new MarkCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertEquals(markFirstCommand, markFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, markFirstCommand);

        // null -> returns false
        assertNotEquals(null, markFirstCommand);

        // different transaction -> returns false
        assertNotEquals(markFirstCommand, markSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkCommand markCommand = new MarkCommand(targetIndex);
        String expected = MarkCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, markCommand.toString());
    }
}
