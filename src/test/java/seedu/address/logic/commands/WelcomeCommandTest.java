package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.WelcomeCommand.WELCOME_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class WelcomeCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_welcome_success() {
        CommandResult expectedCommandResult = new CommandResult(WELCOME_MESSAGE, false, false);
        assertCommandSuccess(new WelcomeCommand(), model, expectedCommandResult, expectedModel);
    }
}
