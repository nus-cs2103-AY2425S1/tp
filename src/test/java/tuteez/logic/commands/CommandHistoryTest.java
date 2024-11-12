package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class CommandHistoryTest {
    private CommandHistory commandHistory;

    @BeforeEach
    public void setUp() {
        commandHistory = new CommandHistory();
    }

    @Test
    public void add_nullCommand_throwsException() {
        // EP test: Adding a null command throws an exception
        assertThrows(AssertionError.class, () -> commandHistory.add(null));
    }

    @Test
    public void add_correctlyAddsCommands() {
        // EP test: Adding valid commands to history.
        // Checks if commands are added correctly and navigable in reverse order.
        commandHistory.add("command1");
        commandHistory.add("command2");

        // Check if the commands have been added correctly
        assertEquals("command2", commandHistory.getPreviousCommand());
        assertEquals("command1", commandHistory.getPreviousCommand());

        // Ensure currentIndex is reset to the end of the list after adding commands
        commandHistory.add("command3");
        assertEquals("command3", commandHistory.getPreviousCommand()); // Should return command3
    }

    @Test
    public void getPreviousCommand_noCommandsAdded_returnsNull() {
        // BVA Test: getPreviousCommand when history is empty
        assertNull(commandHistory.getPreviousCommand());
    }

    @Test
    public void getNextCommand_noCommandsAdded_returnsEmptyString() {
        // BVA Test: getNextCommand when history is empty
        assertEquals("", commandHistory.getNextCommand());
    }

    @Test
    public void getPreviousCommand_correctlyGetsPreviousCommands() {
        // EP Test: Retrieving commands in order of addition.
        // Checks correct navigation through previous commands.
        commandHistory.add("command1");
        commandHistory.add("command2");
        commandHistory.add("command3");

        // Check if the last command can be retrieved
        assertEquals("command3", commandHistory.getPreviousCommand());
        assertEquals("command2", commandHistory.getPreviousCommand());
        assertEquals("command1", commandHistory.getPreviousCommand());
        // Boundary value: No more previous commands, so should return null
        assertNull(commandHistory.getPreviousCommand());
    }

    @Test
    public void getNextCommand_correctlyGetsNextCommands() {
        // EP Test: Retrieving next command after navigating backwards.
        // Verifies correct behavior of getNextCommand following previous commands.
        commandHistory.add("command1");
        commandHistory.add("command2");
        assertEquals("command2", commandHistory.getPreviousCommand());
        assertEquals("command1", commandHistory.getPreviousCommand());
        assertEquals("command2", commandHistory.getNextCommand());
        // Boundary value: No more next commands
        assertEquals("", commandHistory.getNextCommand());
    }

    @Test
    public void getNextCommand_invalidCurrentIndex_throwsAssertionError() throws Exception {
        commandHistory.add("command1");
        commandHistory.add("command2");

        // Use reflection to set currentIndex to an invalid value (e.g., larger than history.size())
        Field field = CommandHistory.class.getDeclaredField("currentIndex");
        field.setAccessible(true);
        field.set(commandHistory, 999); // Invalid index

        // Expect an AssertionError when calling getNextCommand()
        assertThrows(AssertionError.class, () -> commandHistory.getNextCommand());
    }

    @Test
    public void getPreviousCommand_invalidCurrentIndex_throwsAssertionError() throws Exception {
        commandHistory.add("command1");
        commandHistory.add("command2");

        // Use reflection to set currentIndex to an invalid value (e.g., negative beyond -1)
        Field field = CommandHistory.class.getDeclaredField("currentIndex");
        field.setAccessible(true);
        field.set(commandHistory, -999); // Invalid index

        // Expect an AssertionError when calling getPreviousCommand()
        assertThrows(AssertionError.class, () -> commandHistory.getPreviousCommand());
    }

}
