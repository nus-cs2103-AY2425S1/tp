package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddPropertyToBuyCommandTest {

    @Test
    public void execute_validModel_success() throws Exception {
        AddPropertyToBuyCommand command = new AddPropertyToBuyCommand();

        CommandResult result = command.execute(null);

        assertEquals("Hello from AddPropertyToBuyCommand", result.getFeedbackToUser());
    }
}