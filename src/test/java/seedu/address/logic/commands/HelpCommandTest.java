package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.HELP_ADD;
import static seedu.address.logic.commands.HelpCommand.HELP_EDIT;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        // showHelp is true, exit is false
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);

        // showHelp is false, exit is false
        expectedCommandResult = new CommandResult(HELP_ADD, false, false);
        assertCommandSuccess(new HelpCommand(HELP_ADD), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand regularHelpCommand = new HelpCommand();
        HelpCommand otherHelpCommand = new HelpCommand();
        HelpCommand addHelpCommand = new HelpCommand(HELP_ADD);
        HelpCommand otherAddHelpCommand = new HelpCommand(HELP_ADD);
        HelpCommand editHelpCommand = new HelpCommand(HELP_EDIT);

        assertNotEquals(regularHelpCommand, 1);
        assertNotEquals(regularHelpCommand, addHelpCommand);
        assertEquals(regularHelpCommand, otherHelpCommand);
        assertEquals(addHelpCommand, otherAddHelpCommand);
        assertNotEquals(addHelpCommand, editHelpCommand);
    }
}
