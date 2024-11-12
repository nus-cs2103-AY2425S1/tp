package seedu.hiredfiredpro.logic.commands;

import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hiredfiredpro.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
