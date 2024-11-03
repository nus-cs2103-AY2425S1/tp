package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_PROMPT;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_SELECT_FILE;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ImportCommandTest {

    @Test
    public void execute_successfulImport_successMessage() {
        Model model = new ModelManager();

        try {
            CommandResult promptResult = new ImportCommand().execute(model);
            CommandResult importResult = promptResult.confirmPrompt();
            CommandResult result = importResult.processFile(true);
            assertEquals(new CommandResult(MESSAGE_PROMPT, () -> null), promptResult);
            assertEquals(new CommandResult(MESSAGE_SELECT_FILE, false, (success) -> null), importResult);
            assertEquals(new CommandResult(MESSAGE_SUCCESS), result);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_failedImport_failureMessage() {
        Model model = new ModelManager();

        try {
            CommandResult promptResult = new ImportCommand().execute(model);
            CommandResult importResult = promptResult.confirmPrompt();
            CommandResult result = importResult.processFile(false);
            assertEquals(new CommandResult(MESSAGE_PROMPT, () -> null), promptResult);
            assertEquals(new CommandResult(MESSAGE_SELECT_FILE, false, (success) -> null), importResult);
            assertEquals(new CommandResult(MESSAGE_FAILURE), result);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
