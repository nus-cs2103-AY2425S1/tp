package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.Transaction;
public class DeleteTransactionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_filteredList_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showClientAtIndex(model, INDEX_FIRST_CLIENT);
        showClientAtIndex(expectedModel, INDEX_FIRST_CLIENT);
        Client clientToDeleteFrom = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        List<Transaction> transactions = clientToDeleteFrom.getTransactions();
        model.updateTransactionList(transactions);
        List<Transaction> expectedTransactions = new ArrayList<>(transactions);

        Transaction deletedTransaction = expectedTransactions.remove(INDEX_FIRST_TRANSACTION.getZeroBased());

        Client editedClient = new Client(clientToDeleteFrom.getName(), clientToDeleteFrom.getCompany(),
                clientToDeleteFrom.getPhone(), clientToDeleteFrom.getEmail(), clientToDeleteFrom.getAddress(),
                clientToDeleteFrom.getTags(), expectedTransactions);
        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                Messages.format(deletedTransaction), Messages.format(editedClient));
        expectedModel.setClient(clientToDeleteFrom, editedClient);

        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyFilteredTransactionList_throwsCommandException() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);
        List<Transaction> transactions = new ArrayList<>();
        model.updateTransactionList(transactions);

        String expectedMessage = Messages.MESSAGE_EMPTY_TRANSACTION_LIST;

        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        assertCommandFailure(deleteTransactionCommand, model, expectedMessage);
    }

    @Test
    public void execute_unfilteredListFailure_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showClientAtIndex(model, INDEX_FIRST_CLIENT);
        showClientAtIndex(expectedModel, INDEX_FIRST_CLIENT);
        model.setIsViewTransactions(true);

        String expectedMessage = Messages.MESSAGE_EMPTY_TRANSACTION_LIST;

        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        assertCommandFailure(deleteTransactionCommand, model, expectedMessage);
    }

    @Test
    public void execute_clientListView_throwsCommandException() {
        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        String expectedMessage = String.format(Messages.MESSAGE_MUST_BE_TRANSACTION_LIST, "deletet");
        assertCommandFailure(deleteTransactionCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteTransactionCommand deleteFirstCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        DeleteTransactionCommand deleteSecondCommand = new DeleteTransactionCommand(INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTransactionCommand deleteFirstCommandCopy = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
