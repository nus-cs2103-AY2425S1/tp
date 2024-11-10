package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showEmptyClientList;
import static seedu.address.testutil.TypicalClients.BENSON;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListTransactionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_listAllTransactions_success() {
        showClientAtIndex(expectedModel, INDEX_SECOND_CLIENT);
        expectedModel.updateTransactionList(BENSON.getTransactions());
        assertCommandSuccess(new ListTransactionCommand(INDEX_SECOND_CLIENT), model,
                String.format(ListTransactionCommand.MESSAGE_SUCCESS,
                        1,
                        Messages.format(BENSON)),
                expectedModel);
    }

    @Test
    public void execute_emptyList_throwCommandException() {
        showEmptyClientList(model);

        Index outOfBoundIndex = INDEX_FIRST_CLIENT;

        ListTransactionCommand listTransactionCommand = new ListTransactionCommand(outOfBoundIndex);

        String expectedMessage = String.format(Messages.MESSAGE_EMPTY_CLIENT_LIST, "listt");

        assertCommandFailure(listTransactionCommand, model, expectedMessage);

    }

    @Test
    public void execute_listTransactionsInvalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        ListTransactionCommand command = new ListTransactionCommand(outOfBoundIndex);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_transactionListView_throwsCommandException() {
        ListTransactionCommand command = new ListTransactionCommand(INDEX_FIRST_CLIENT);
        model.setIsViewTransactions(true);
        String expectedMessage = String.format(Messages.MESSAGE_MUST_BE_CLIENT_LIST, "listt");
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        ListTransactionCommand command1 = new ListTransactionCommand(INDEX_FIRST_CLIENT);
        ListTransactionCommand command2 = new ListTransactionCommand(INDEX_FIRST_CLIENT);
        ListTransactionCommand command3 = new ListTransactionCommand(INDEX_SECOND_CLIENT);

        // same object -> return true
        assertTrue(command1.equals(command1));

        // different object but same index -> return true
        assertTrue(command1.equals(command2));

        // different index -> return true
        assertFalse(command1.equals(command3));

        // compare object with null -> return false
        assertFalse(command1.equals(null));

        // compare with other types of command -> return false
        assertFalse(command1.equals(new ListCommand()));
    }
}
