package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {
    private CommandHistory commandHistory;
    private int pointer;

    @BeforeEach
    public void setUp() {
        commandHistory = CommandHistory.getInstance();
        pointer = CommandHistory.getPointer();

        // Clear command history before every test
        commandHistory.clear();
        pointer = -1;
    }

    @Test
    public void addCommand() {
        commandHistory.addCommand("command");
        assertEquals(commandHistory.get(0), "command");
        assertEquals(CommandHistory.getPointer(), 1);
    }

    @Test
    public void getPreviousPointerCommand() {
        // EP: command history is empty
        assertEquals(commandHistory.getPreviousPointerCommand(), "");

        // EP: pointer is at the zero index
        commandHistory.addCommand("command1");
        commandHistory.getPreviousPointerCommand();
        assertEquals(commandHistory.getPreviousPointerCommand(), "command1");

        // EP: pointer is not at zero index
        commandHistory.addCommand("command2");
        assertEquals(commandHistory.getPreviousPointerCommand(), "command2");

        commandHistory.clear();
    }

    @Test
    public void getNextPointerCommand() {
        // EP: command history is empty
        assertEquals(commandHistory.getNextPointerCommand(), "");

        // EP: pointer is more than or equals the last index
        commandHistory.addCommand("command1");
        assertEquals(commandHistory.getNextPointerCommand(), "");
        commandHistory.getPreviousPointerCommand();
        assertEquals(commandHistory.getNextPointerCommand(), "");

        // EP: pointer is less than the last index
        commandHistory.addCommand("command2");
        commandHistory.getPreviousPointerCommand();
        commandHistory.getPreviousPointerCommand();
        assertEquals(commandHistory.getNextPointerCommand(), "command2");

        commandHistory.clear();
    }
}
