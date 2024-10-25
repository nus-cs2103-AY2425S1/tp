package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.ui.util.CommandHistory;

/**
 * Unit tests for {@code CommandHistory}.
 */
public class CommandHistoryTest {

    private CommandHistory commandHistory;

    @BeforeEach
    public void setUp() {
        commandHistory = new CommandHistory();
    }

    @Test
    public void addCommand_singleCommand_success() {
        commandHistory.addCommand("first command");
        assertEquals("first command", commandHistory.getPreviousCommand());
    }

    @Test
    public void addCommand_multipleCommands_success() {
        commandHistory.addCommand("first command");
        commandHistory.addCommand("second command");
        commandHistory.addCommand("third command");

        assertEquals("third command", commandHistory.getPreviousCommand());
        assertEquals("second command", commandHistory.getPreviousCommand());
        assertEquals("first command", commandHistory.getPreviousCommand());
    }

    @Test
    public void getPreviousCommand_noHistory_returnsNull() {
        assertNull(commandHistory.getPreviousCommand());
    }

    @Test
    public void getPreviousCommand_atStartOfHistory_returnsNull() {
        commandHistory.addCommand("first command");

        // Move to the start of the history
        commandHistory.getPreviousCommand();
        assertNull(commandHistory.getPreviousCommand());
    }

    @Test
    public void getNextCommand_noHistory_returnsNull() {
        assertNull(commandHistory.getNextCommand());
    }

    @Test
    public void getNextCommand_atEndOfHistory_returnsNull() {
        commandHistory.addCommand("first command");
        commandHistory.addCommand("second command");

        // Move to the start of history
        commandHistory.getPreviousCommand();
        commandHistory.getPreviousCommand();

        // Move forward to the end of history
        commandHistory.getNextCommand();
        commandHistory.getNextCommand();
        assertNull(commandHistory.getNextCommand());
    }

    @Test
    public void getNextCommand_fromMiddleOfHistory_success() {
        commandHistory.addCommand("first command");
        commandHistory.addCommand("second command");
        commandHistory.addCommand("third command");

        // Move to the start of history
        commandHistory.getPreviousCommand();
        commandHistory.getPreviousCommand();
        commandHistory.getPreviousCommand();

        // Move forward one step in history
        assertEquals("second command", commandHistory.getNextCommand());
    }

    @Test
    public void addCommand_afterNavigatingHistory_resetsHistoryIndex() {
        commandHistory.addCommand("first command");
        commandHistory.addCommand("second command");

        // Move to the start of history
        commandHistory.getPreviousCommand();
        commandHistory.getPreviousCommand();

        // Add a new command
        commandHistory.addCommand("new command");

        // New command should be at the end of history
        assertEquals("new command", commandHistory.getPreviousCommand());
        assertNull(commandHistory.getNextCommand()); // At the end of history
    }
}
