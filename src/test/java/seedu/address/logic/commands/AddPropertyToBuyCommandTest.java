package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AddPropertyToBuyCommandTest {

    @Test
    public void execute_validModel_success() throws Exception {
        AddPropertyToBuyCommand command = new AddPropertyToBuyCommand();

        CommandResult result = command.execute(null);

        assertEquals("Hello from AddPropertyToBuyCommand", result.getFeedbackToUser());
    }
}
