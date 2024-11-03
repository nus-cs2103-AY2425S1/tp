package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SELECT_FILE;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExportCommandTest {

    @Test
    public void execute_successfulExport_successMessage() {
        Model model = new ModelManager();

        try {
            CommandResult exportResult = new ExportCommand().execute(model);
            CommandResult result = exportResult.processFile(true);
            assertEquals(new CommandResult(MESSAGE_SELECT_FILE, true, (success) -> null), exportResult);
            assertEquals(new CommandResult(MESSAGE_SUCCESS), result);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_failedExport_failureMessage() {
        Model model = new ModelManager();

        try {
            CommandResult exportResult = new ExportCommand().execute(model);
            CommandResult result = exportResult.processFile(false);
            assertEquals(new CommandResult(MESSAGE_SELECT_FILE, true, (success) -> null), exportResult);
            assertEquals(new CommandResult(MESSAGE_FAILURE), result);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
