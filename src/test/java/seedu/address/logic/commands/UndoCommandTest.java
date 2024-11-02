package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class UndoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * EP: No previous command to undo
     */
    @Test
    public void execute_noPreviousCommand_failure() {
        UndoCommand command = new UndoCommand();
        assertCommandFailure(command, model, UndoCommand.MESSAGE_NO_PREVIOUS_COMMAND);
    }

    /**
     * EP: Previous command cannot be undone
     */
    @Test
    public void execute_previousCommandCannotBeUndone() {
        model.updatePreviousCommand(new ListCommand());
        UndoCommand command = new UndoCommand();
        assertCommandFailure(command, model, UndoCommand.MESSAGE_COMMAND_CANNOT_BE_UNDONE);
    }
}
