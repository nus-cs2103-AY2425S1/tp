package seedu.hireme.logic.commands;

import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.internshipapplication.InternshipApplication;

public class HelpCommandTest {
    private Model<InternshipApplication> model = new ModelManager<>();
    private Model<InternshipApplication> expectedModel = new ModelManager<>();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
