package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAgentAssist;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditClientDescriptorBuilder;

public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalAgentAssist(), new UserPrefs());

    @Test
    public void execute_undo_success() {
        Client editedClient = new ClientBuilder().build();
        EditCommand.EditClientDescriptor descriptor = new EditClientDescriptorBuilder(editedClient).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CLIENT, descriptor);
        try {
            CommandResult result = editCommand.execute(model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        UndoCommand undoCommand = new UndoCommand();
        Model expectedModel = new ModelManager(getTypicalAgentAssist(), new UserPrefs());

        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noPreviousCommand_failure() {
        Model model = new ModelManager();
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_COMMAND_TO_UNDO);
    }

}
