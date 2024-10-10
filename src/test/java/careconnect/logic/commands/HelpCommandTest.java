package careconnect.logic.commands;

import static careconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static careconnect.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import careconnect.model.Model;
import careconnect.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        CommandTestUtil.assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
