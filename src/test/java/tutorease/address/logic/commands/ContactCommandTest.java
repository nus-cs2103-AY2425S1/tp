package tutorease.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ContactCommandTest {
    @Test
    public void testCommandWord() {
        // Check if the COMMAND_WORD is correctly set
        assertEquals("contact", ContactCommand.COMMAND_WORD, "The COMMAND_WORD should be 'contact'");
    }

    @Test
    public void testSubCommandExecution() {
        // Test a concrete implementation of ContactCommand
        ContactCommand command = new ListContactCommand();
        // Ensure the object is an instance of ContactCommand
        assertEquals("contact", ContactCommand.COMMAND_WORD);
    }
}
