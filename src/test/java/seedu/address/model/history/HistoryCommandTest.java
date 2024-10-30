package seedu.address.model.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;

public class HistoryCommandTest {
    @BeforeEach
    public void setUp() {
        // Reset the static counter before each test
        HistoryCommand.resetIndex();
    }

    @Test
    public void testCreateHistoryCommand() {
        // Command stub with ClearCommand
        Command command = new ClearCommand();
        String commandText = "sample command text";

        HistoryCommand historyCommand = HistoryCommand.of(command, commandText);

        // Verify that the command text is set correctly
        assertEquals(commandText, historyCommand.getOriginalCommandText());

        // Verify that the index is 0 for the first command created
        assertEquals(0, historyCommand.getIndex());
    }

    @Test
    public void testMultipleHistoryCommandInstances() {
        // Create first HistoryCommand
        Command command1 = new ClearCommand();
        String commandText1 = "first command";
        HistoryCommand historyCommand1 = HistoryCommand.of(command1, commandText1);

        // Create second HistoryCommand
        Command command2 = new ClearCommand();
        String commandText2 = "second command";
        HistoryCommand historyCommand2 = HistoryCommand.of(command2, commandText2);

        // Verify that the second command has a different index
        assertEquals(0, historyCommand1.getIndex());
        assertEquals(1, historyCommand2.getIndex());
        assertNotEquals(historyCommand1.getIndex(), historyCommand2.getIndex());
    }

    @Test
    public void getCommand() {
        Command command = new ClearCommand();
        String commandText = "sample command text";
        HistoryCommand historyCommand1 = HistoryCommand.of(command, commandText);

        assertEquals(command, historyCommand1.getCommand());
    }

    @Test
    public void testEquals() {
        Command command = new ClearCommand();
        Command command2 = new ExitCommand(); //ExitCommand is being used as stub;
        String commandText = "sample command text";

        HistoryCommand historyCommand1 = HistoryCommand.of(command, commandText);
        HistoryCommand historyCommand2 = HistoryCommand.of(command, commandText);

        assertTrue(historyCommand1.equals(historyCommand1));
        assertNotEquals(historyCommand1, null);

        // Check that two HistoryCommand instances with the same command and text are equal
        assertEquals(historyCommand1, historyCommand2);

        // Modify the commandText for historyCommand2
        HistoryCommand historyCommand3 = HistoryCommand.of(command, "different command text");

        // Check that they are not equal if the command text differs
        assertNotEquals(historyCommand1, historyCommand3);

        // Check that they are not equal if the command differs
        assertNotEquals(historyCommand1, HistoryCommand.of(command2, commandText));

        //Check that they are not equal if the command and text both differ.
        assertNotEquals(historyCommand1, HistoryCommand.of(command2, "different command text"));
    }
}
