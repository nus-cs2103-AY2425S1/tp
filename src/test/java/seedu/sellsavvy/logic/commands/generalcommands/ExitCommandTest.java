package seedu.sellsavvy.logic.commands.generalcommands;

import static seedu.sellsavvy.logic.commands.generalcommands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
