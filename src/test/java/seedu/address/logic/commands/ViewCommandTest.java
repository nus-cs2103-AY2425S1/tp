package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalAgentAssist;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;

class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAgentAssist(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewCommand(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Client clientToView = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_CLIENT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_CLIENT_SUCCESS, Messages.format(clientToView));

        CommandResult result = viewCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(result.isShowClient());
        assertEquals(clientToView, result.getViewedClient());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertThrows(CommandException.class, MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX, () ->
                viewCommand.execute(model));
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_CLIENT);
        ViewCommand viewSecondCommand = new ViewCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_CLIENT);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void testCommandUsageMessage() {
        assertEquals("view: Views the client identified by the index number used in the displayed client list.\n"
                + "Parameters: INDEX (must be a positive integer)\n"
                + "Example: 'view 1'", ViewCommand.MESSAGE_USAGE);
    }
}
