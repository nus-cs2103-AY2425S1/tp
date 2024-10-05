package seedu.researchroster.logic.commands;

import static seedu.researchroster.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.researchroster.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.researchroster.model.Model;
import seedu.researchroster.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
