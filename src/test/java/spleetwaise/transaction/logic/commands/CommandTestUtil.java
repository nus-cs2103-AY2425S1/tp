package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.commons.testutil.Assert;
import spleetwaise.transaction.model.TransactionBook;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.model.transaction.TransactionIdPredicate;

/**
 * Contains helper methods for testing transaction commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br> - the returned {@link CommandResult} matches
     * {@code expectedCommandResult} <br> - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(
            Command command, TransactionBookModel actualModel, CommandResult expectedCommandResult,
            TransactionBookModel expectedModel
    ) {
        try {
            CommonModelManager.initialise(null, actualModel);
            CommandResult result = command.execute();
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of txn command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, TransactionBookModel, CommandResult, TransactionBookModel)} that takes a
     * string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(
            Command command, TransactionBookModel actualModel, String expectedMessage,
            TransactionBookModel expectedModel
    ) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br> - a {@code CommandException} is thrown <br> - the
     * CommandException message matches {@code expectedMessage} <br> - the transaction book, filtered transaction list
     * and selected transaction in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, TransactionBookModel actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TransactionBook expectedTransactionBook = new TransactionBook(actualModel.getTransactionBook());
        List<Transaction> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());
        Assert.assertThrows(CommandException.class, expectedMessage, command::execute);
        assertEquals(expectedTransactionBook, actualModel.getTransactionBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredTransactionList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the transaction at the given {@code targetIndex} in the
     * {@code model}'s transaction book.
     */
    public static void showTransactionAtIndex(TransactionBookModel model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        Transaction txn = model.getFilteredTransactionList().get(targetIndex.getZeroBased());
        final String txnId = txn.getId();
        model.updateFilteredTransactionList(new TransactionIdPredicate(txnId));

        assertEquals(1, model.getFilteredTransactionList().size());
    }
}
