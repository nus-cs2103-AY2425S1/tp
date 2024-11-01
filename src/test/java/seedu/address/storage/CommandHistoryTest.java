package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;

public class CommandHistoryTest {
    private static final String VALID_COMMAND = "github n/John Doe";
    private static final String INVALID_COMMAND = "iloveoop";
    private CommandHistory commandHistory = CommandHistory.getInstance();

    @BeforeEach
    public void setUp() {
        // Resets command history
        commandHistory.clearListOfCommands();
        commandHistory = CommandHistory.getInstance();
    }

    @Test
    void addCommand_nullStringCommand_success() {
        commandHistory.addCommand(null);
    }

    @Test
    public void getPastCommand_emptyListOfCommandsWithArrowKey_emptyStringReturned() {
        assertEquals(commandHistory.getPastCommand(KeyCode.UP), "");
        commandHistory.clearListOfCommands(); //reset list of commands
        assertEquals(commandHistory.getPastCommand(KeyCode.DOWN), "");
    }

    @Test
    public void getPastCommand_filledListOfCommandsWithUpArrowKey_commandReturned() {
        commandHistory.addCommand(VALID_COMMAND);
        commandHistory.addCommand(INVALID_COMMAND);

        String firstReturnedCommand = commandHistory.getPastCommand(KeyCode.UP);
        assertEquals(firstReturnedCommand, INVALID_COMMAND);

        String secondReturnedCommand = commandHistory.getPastCommand(KeyCode.UP);
        assertEquals(secondReturnedCommand, VALID_COMMAND);
    }

    /**
     * Tests the edge case where the earliest command is always returned after sufficiently large number of UP arrow
     * keys input.
     */
    @Test
    public void getPastCommand_earliestCommandEntered_commandReturned() {
        // Adds two commands into the command history
        commandHistory.addCommand(VALID_COMMAND);
        commandHistory.addCommand(INVALID_COMMAND);

        // Retrieves the first two commands
        String firstReturnedCommand = commandHistory.getPastCommand(KeyCode.UP);
        String secondReturnedCommand = commandHistory.getPastCommand(KeyCode.UP);

        // Attempting the third command -> expected to get the same command as the second command
        String thirdReturnedCommand = commandHistory.getPastCommand(KeyCode.UP);
        assertEquals(secondReturnedCommand, thirdReturnedCommand);
    }

    @Test
    public void getPastCommand_navigateToCurrentCommand_emptyStringReturned() {
        commandHistory.addCommand(VALID_COMMAND);
        commandHistory.addCommand(INVALID_COMMAND);

        // Returns the last stored command in the command history.
        commandHistory.getPastCommand(KeyCode.UP);

        String currentCommand = commandHistory.getPastCommand(KeyCode.DOWN);
        assertEquals(currentCommand, "");
    }

    @Test
    public void getPastCommand_getCommandWithDownArrowKey_emptyStringReturned() {
        commandHistory.addCommand(VALID_COMMAND);
        commandHistory.addCommand(INVALID_COMMAND);

        // Returns the 2 commands stored in the command history.
        commandHistory.getPastCommand(KeyCode.UP);
        commandHistory.getPastCommand(KeyCode.UP);

        String lastCommandStored = commandHistory.getPastCommand(KeyCode.DOWN);
        assertEquals(lastCommandStored, INVALID_COMMAND);
    }


    @Test
    public void clearListOfCommands_emptyStringReturned() {
        commandHistory.addCommand(VALID_COMMAND);
        commandHistory.addCommand(INVALID_COMMAND);

        commandHistory.clearListOfCommands();

        String currentCommand = commandHistory.getPastCommand(KeyCode.UP);
        assertEquals(currentCommand, "");
    }
}
