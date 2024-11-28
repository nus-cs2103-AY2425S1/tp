package seedu.address.model.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.command.Commands;

public class CommandsTest {

    private Commands commands;

    @BeforeEach
    public void setUp() {
        commands = new Commands();
    }

    @Test
    public void testCommandListSize() {
        assertEquals(23, commands.getListofCommands().size(), "The command list should contain 23 commands.");
    }

    @Test
    public void testToString() {
        String expectedOutput = String.join("\n", commands.getListofCommands());
        assertEquals(expectedOutput, commands.toString());
    }
}
