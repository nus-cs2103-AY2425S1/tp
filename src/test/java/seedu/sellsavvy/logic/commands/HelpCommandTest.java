package seedu.sellsavvy.logic.commands;

import static seedu.sellsavvy.logic.commands.PersonCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.generalcommands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.generalcommands.HelpCommand;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
