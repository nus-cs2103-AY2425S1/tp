package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpWindowCommand.SHOWING_HELP_WINDOW_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpWindowCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_WINDOW_MESSAGE, false, true, false);
        assertCommandSuccess(new HelpWindowCommand(), model, expectedCommandResult, expectedModel);
    }
}
