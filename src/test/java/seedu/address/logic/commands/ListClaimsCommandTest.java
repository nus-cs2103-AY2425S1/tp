package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ListClaimsCommand}.
 */
public class ListClaimsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests the execution of {@code ListClaimsCommand} with a valid index on an unfiltered client list.
     *
     * This test verifies that when a valid index is provided, the command successfully retrieves
     * the corresponding client and their claims from the model. It checks that the expected success
     * message is generated and that the model state remains consistent after the command execution.
     *
     * Specifically, this test:
     * - Retrieves the first client from the filtered client list.
     * - Constructs a {@code ListClaimsCommand} with the specified index.
     * - Asserts that executing the command results in a success message formatted with the client's name
     *   and their associated claims.
     * - Verifies that the model state after executing the command matches the expected model state.
     *
     * @see ListClaimsCommand
     * @see Model
     */
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Client clientToDisplay = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        ListClaimsCommand listClaimsCommand = new ListClaimsCommand(INDEX_FIRST_CLIENT);

        String expectedMessage = String.format(ListClaimsCommand.MESSAGE_SUCCESS,
                clientToDisplay.getName().toString(),
                clientToDisplay.getInsurancePlansManager().accessClaims());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(listClaimsCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests the execution of {@code ListClaimsCommand} with an invalid index on an unfiltered client list.
     *
     * This test verifies that when an invalid index (one that exceeds the bounds of the filtered client list)
     * is provided, the command throws a {@code CommandException}. It ensures that the command does not
     * execute successfully and that the appropriate error message is returned.
     *
     * Specifically, this test:
     * - Constructs an out-of-bounds index based on the size of the unfiltered client list.
     * - Creates a {@code ListClaimsCommand} with the invalid index.
     * - Asserts that executing the command results in a failure with the expected error message,
     *   indicating an invalid client display index.
     *
     * @see ListClaimsCommand
     * @see CommandException
     */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        ListClaimsCommand listClaimsCommand = new ListClaimsCommand(outOfBoundIndex);

        assertCommandFailure(listClaimsCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    /**
     * Tests the execution of {@code ListClaimsCommand} with a valid index on a filtered client list.
     *
     * This test verifies that when a valid index is provided and the client list is filtered,
     * the command successfully retrieves the corresponding client and their claims from the model.
     * It checks that the expected success message is generated and that the model state remains
     * consistent after the command execution.
     *
     * Specifically, this test:
     * - Displays the client at the specified index in the filtered list.
     * - Constructs a {@code ListClaimsCommand} with the specified index.
     * - Asserts that executing the command results in a success message formatted with the client's name
     *   and their associated claims.
     * - Verifies that the model state after executing the command matches the expected model state,
     *   which also reflects showing the client at the specified index.
     *
     * @see ListClaimsCommand
     * @see Model
     */
    @Test
    public void execute_validIndexFilteredList_success() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        Client clientToDisplay = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        ListClaimsCommand listClaimsCommand = new ListClaimsCommand(INDEX_FIRST_CLIENT);

        String expectedMessage = String.format(ListClaimsCommand.MESSAGE_SUCCESS,
                clientToDisplay.getName().toString(),
                clientToDisplay.getInsurancePlansManager().accessClaims());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showClientAtIndex(expectedModel, INDEX_FIRST_CLIENT);

        assertCommandSuccess(listClaimsCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests the execution of {@code ListClaimsCommand} with an invalid index on a filtered client list.
     *
     * This test verifies that when an invalid index is provided (one that is out of bounds for the
     * filtered client list), the command throws a {@code CommandException}. It ensures that the
     * command does not execute successfully and that the appropriate error message is returned.
     *
     * Specifically, this test:
     * - Displays the client at the specified index in the filtered list to set up the context.
     * - Defines an out-of-bounds index for the client list.
     * - Asserts that the out-of-bounds index is still valid within the context of the full address book.
     * - Constructs a {@code ListClaimsCommand} with the invalid index.
     * - Asserts that executing the command results in a failure with the expected error message,
     *   indicating an invalid client display index.
     *
     * @see ListClaimsCommand
     * @see CommandException
     */
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        Index outOfBoundIndex = INDEX_SECOND_CLIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        ListClaimsCommand listClaimsCommand = new ListClaimsCommand(outOfBoundIndex);

        assertCommandFailure(listClaimsCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListClaimsCommand listClaimsFirstCommand = new ListClaimsCommand(INDEX_FIRST_CLIENT);
        ListClaimsCommand listClaimsSecondCommand = new ListClaimsCommand(INDEX_SECOND_CLIENT);

        // same object -> returns true
        assertEquals(listClaimsFirstCommand, listClaimsFirstCommand);

        // same values -> returns true
        ListClaimsCommand listClaimsFirstCommandCopy = new ListClaimsCommand(INDEX_FIRST_CLIENT);
        assertEquals(listClaimsFirstCommand, listClaimsFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, listClaimsFirstCommand);

        // null -> returns false
        assertNotEquals(null, listClaimsFirstCommand);

        // different client -> returns false
        assertNotEquals(listClaimsFirstCommand, listClaimsSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no clients.
     */
    private void showNoClient(Model model) {
        model.updateFilteredClientList(p -> false);

        assertTrue(model.getFilteredClientList().isEmpty());
    }
}
