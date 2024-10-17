package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.hireme.logic.commands.CommandResult;
import seedu.hireme.logic.commands.ExitCommand;
import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.internshipapplication.InternshipApplication;

public class ExitCommandTest {
    private Model<InternshipApplication> model = new ModelManager<>();
    private Model<InternshipApplication> expectedModel = new ModelManager<>();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult =
                new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
