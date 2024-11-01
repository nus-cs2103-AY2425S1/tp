package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private CommandHistory commandHistory;

    @BeforeEach
    public void setUp() {
        commandHistory = new CommandHistory();
    }

    @Test
    public void addCommand_singleCommand_success() {
        String command = "add John Doe";
        commandHistory.addCommand(command);
        assertEquals(command, commandHistory.getPreviousCommand(),
                "Should retrieve the only command added.");
    }

    @Test
    public void addCommand_multipleCommands_success() {
        commandHistory.addCommand("add Alice");
        commandHistory.addCommand("add Bob");
        commandHistory.addCommand("delete Alice");

        // Navigating back through commands
        assertEquals("delete Alice", commandHistory.getPreviousCommand());
        assertEquals("add Bob", commandHistory.getPreviousCommand());
        assertEquals("add Alice", commandHistory.getPreviousCommand());
    }

    @Test
    public void getPreviousCommand_emptyHistory_returnsNull() {
        assertNull(commandHistory.getPreviousCommand(),
                "Previous command should be null when history is empty.");
    }

    @Test
    public void getPreviousCommand_noMoreHistory_returnsNull() {
        commandHistory.addCommand("add Alice");

        // Move back to the first command
        assertEquals("add Alice", commandHistory.getPreviousCommand());

        // No more history, so it should return null
        assertNull(commandHistory.getPreviousCommand(),
                "Should return null when no more previous commands are available.");
    }

    @Test
    public void getNextCommand_emptyHistory_returnsEmptyString() {
        assertEquals("", commandHistory.getNextCommand(),
                "Next command should be empty when history is empty.");
    }

    @Test
    public void getNextCommand_noMoreNextCommand_returnsEmptyString() {
        commandHistory.addCommand("add Alice");
        commandHistory.addCommand("delete Bob");

        // Move back to previous commands and then try to get the next one
        commandHistory.getPreviousCommand(); // "delete Bob"
        commandHistory.getPreviousCommand(); // "add Alice"

        // Navigating forward again
        assertEquals("delete Bob", commandHistory.getNextCommand());

        // No more next commands, should return empty string
        assertEquals("", commandHistory.getNextCommand(),
                "Should return an empty string when no more next commands are available.");
    }

    @Test
    public void navigateBackAndForth_success() {
        commandHistory.addCommand("add Alice");
        commandHistory.addCommand("delete Bob");
        commandHistory.addCommand("list");

        // Navigate back through commands
        assertEquals("list", commandHistory.getPreviousCommand());
        assertEquals("delete Bob", commandHistory.getPreviousCommand());
        assertEquals("add Alice", commandHistory.getPreviousCommand());

        // Navigate forward again
        assertEquals("delete Bob", commandHistory.getNextCommand());
        assertEquals("list", commandHistory.getNextCommand());

        // End of history, should return empty string
        assertEquals("", commandHistory.getNextCommand(),
                "Should return an empty string at the end of command history.");
    }
}
