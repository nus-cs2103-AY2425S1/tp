package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandHistoryTest {

    private CommandHistory commandHistory;

    @BeforeEach
    void setUp() {
        commandHistory = new CommandHistory();
    }

    @Test
    void addCommand_addsNonBlankCommand() {
        commandHistory.addCommand("first command");
        assertEquals("first command", commandHistory.getPreviousCommand());
    }

    @Test
    void addCommand_doesNotAddBlankCommand() {
        commandHistory.addCommand("");
        assertEquals("", commandHistory.getPreviousCommand()); // Expect empty since no commands added
    }

    @Test
    void getPreviousCommand_navigatesBackThroughHistory() {
        commandHistory.addCommand("first command");
        commandHistory.addCommand("second command");
        commandHistory.addCommand("third command");

        assertEquals("third command", commandHistory.getPreviousCommand());
        assertEquals("second command", commandHistory.getPreviousCommand());
        assertEquals("first command", commandHistory.getPreviousCommand());
        assertEquals("first command", commandHistory.getPreviousCommand()); // Stays at first command
    }

    @Test
    void getNextCommand_navigatesForwardThroughHistory() {
        commandHistory.addCommand("first command");
        commandHistory.addCommand("second command");

        commandHistory.getPreviousCommand(); // Go to "second command"
        commandHistory.getPreviousCommand(); // Go to "first command"

        assertEquals("second command", commandHistory.getNextCommand());
        assertEquals("", commandHistory.getNextCommand()); // No more commands, expect empty
    }

    @Test
    void getPreviousCommand_whenHistoryIsEmpty_returnsEmptyString() {
        assertEquals("", commandHistory.getPreviousCommand());
    }

    @Test
    void getNextCommand_whenHistoryIsEmpty_returnsEmptyString() {
        assertEquals("", commandHistory.getNextCommand());
    }

    @Test
    void addCommand_resetsCurrentIndexToEnd() {
        commandHistory.addCommand("first command");
        commandHistory.addCommand("second command");

        commandHistory.getPreviousCommand(); // "second command"
        commandHistory.addCommand("third command"); // Adding new command should reset currentIndex

        assertEquals("third command", commandHistory.getPreviousCommand()); // New latest command
    }
}
