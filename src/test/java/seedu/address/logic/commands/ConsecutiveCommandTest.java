package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ConsecutiveCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_consecutiveCommand_success() {
        String commandWord = "list";
        String argument = "";
        String expectedMessage = String.format(ConsecutiveCommand.MESSAGE_CONSECUTIVE_COMMAND, commandWord);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        // Create a ConsecutiveCommand with the command word "list"
        ConsecutiveCommand consecutiveCommand = new ConsecutiveCommand(commandWord, argument);
        CommandResult result = consecutiveCommand.execute(model);

        // Verify that the result matches the expected CommandResult
        assertEquals(expectedCommandResult, result);
    }
}
