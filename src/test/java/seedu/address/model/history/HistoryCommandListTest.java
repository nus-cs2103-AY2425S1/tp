package seedu.address.model.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ANDY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class HistoryCommandListTest {
    private HistoryCommandList historyCommandList;

    @BeforeEach
    public void setUp() {
        historyCommandList = new HistoryCommandList();
        HistoryCommandList.setCommandHistoryText("Initial command text");
        HistoryCommand.resetIndex();
    }

    @Test
    public void addAndGetHistoryCommand() {
        Command command = new ClearCommand();
        historyCommandList.add(command);

        // Retrieve the history commands
        ObservableList<HistoryCommand> historyCommands = historyCommandList.getHistoryCommands();

        // Check that the list contains one command and that it matches the added command
        assertEquals(1, historyCommands.size());
        HistoryCommand historyCommand = historyCommands.get(0);
        assertEquals(command, historyCommand.getCommand());
        assertEquals("Initial command text", historyCommand.getOriginalCommandText());
    }

    @Test
    public void addCommandWithoutSettingText_throwsAssertionError() {
        HistoryCommandList.setCommandHistoryText(null);

        Command command = new ClearCommand();

        // Assert that an AssertionError is thrown when adding a command
        assertThrows(AssertionError.class, () -> historyCommandList.add(command));
    }

    @Test
    public void addCommandWithInvalidCommand_throwsAssertionError() {
        HistoryCommandList.setCommandHistoryText("sample text");

        Command command = new ExitCommand();

        // Assert that an AssertionError is thrown when adding a command that does not change the address book.
        assertThrows(AssertionError.class, () -> historyCommandList.add(command));
    }

    @Test
    public void addMultipleCommands_checkOrder() {
        Command command1 = new ClearCommand();
        Command command2 = new AddCommand(new PersonBuilder(ANDY).build());
        HistoryCommandList.setCommandHistoryText("Command 1");
        historyCommandList.add(command1);
        HistoryCommandList.setCommandHistoryText("Command 2");
        historyCommandList.add(command2);

        // Retrieve the history commands
        ObservableList<HistoryCommand> historyCommands = historyCommandList.getHistoryCommands();

        // Check that the list contains two commands
        assertEquals(2, historyCommands.size());

        // Ensure the first command added is at the top in the list (LIFO)
        assertEquals(command2, historyCommands.get(0).getCommand());
        assertEquals("Command 2", historyCommands.get(0).getOriginalCommandText());
        assertEquals(1, historyCommands.get(0).getIndex());

        assertEquals(command1, historyCommands.get(1).getCommand());
        assertEquals("Command 1", historyCommands.get(1).getOriginalCommandText());
        assertEquals(0, historyCommands.get(1).getIndex());
    }

    @Test
    public void equals_identicalLists_returnsTrue() {
        Command command1 = new ClearCommand();
        Command command2 = new AddCommand(new PersonBuilder(ANDY).build());

        HistoryCommandList.setCommandHistoryText("Command 1");
        historyCommandList.add(command1);
        HistoryCommandList.setCommandHistoryText("Command 2");
        historyCommandList.add(command2);

        HistoryCommandList anotherList = new HistoryCommandList();
        HistoryCommandList.setCommandHistoryText("Command 1");
        anotherList.add(command1);
        HistoryCommandList.setCommandHistoryText("Command 2");
        anotherList.add(command2);

        assertEquals(historyCommandList, anotherList);
        assertTrue(historyCommandList.equals(historyCommandList));
    }

    @Test
    public void equals_differentLists_returnsFalse() {
        Command command1 = new ClearCommand();
        Command command2 = new AddCommand(new PersonBuilder(ANDY).build());

        HistoryCommandList.setCommandHistoryText("Command 1");
        historyCommandList.add(command1);

        HistoryCommandList anotherList = new HistoryCommandList();
        HistoryCommandList.setCommandHistoryText("Command 1");
        anotherList.add(command2); // Different command

        assertNotEquals(historyCommandList, anotherList);
        assertNotEquals(historyCommandList, null);
    }
}
