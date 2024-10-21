package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExportCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_export_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS, "CSV"),
            false, false, false);
        assertCommandSuccess(new ExportCommand("CSV"), model, expectedCommandResult, expectedModel);
    }
}
