package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {

    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, false, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);

        CommandResult expectedCommandResultAdd = new CommandResult(AddCommand.MESSAGE_USAGE);
        assertCommandSuccess(new HelpCommand(AddCommand.MESSAGE_USAGE),
                model, expectedCommandResultAdd, expectedModel);

        CommandResult expectedCommandResultClear = new CommandResult(ClearCommand.MESSAGE_USAGE);
        assertCommandSuccess(new HelpCommand(ClearCommand.MESSAGE_USAGE),
                model, expectedCommandResultClear, expectedModel);

        CommandResult expectedCommandResultDelete = new CommandResult(DeleteCommand.MESSAGE_USAGE);
        assertCommandSuccess(new HelpCommand(DeleteCommand.MESSAGE_USAGE),
                model, expectedCommandResultDelete, expectedModel);

        CommandResult expectedCommandResultEdit = new CommandResult(EditCommand.MESSAGE_USAGE);
        assertCommandSuccess(new HelpCommand(EditCommand.MESSAGE_USAGE),
                model, expectedCommandResultEdit, expectedModel);
    }
}
