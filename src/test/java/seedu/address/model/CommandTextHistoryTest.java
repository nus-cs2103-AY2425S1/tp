package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCommandTextHistory.getTypicalCommandTextHistory;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class CommandTextHistoryTest {

    private final CommandTextHistory commandTextHistory = new CommandTextHistory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), commandTextHistory.getCommandTextList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> commandTextHistory.resetData(null));
    }

    @Test
    public void resetData_withValidCommandTextHistory_replacesData() {
        CommandTextHistory newData = getTypicalCommandTextHistory();
        commandTextHistory.resetData(newData);
        assertEquals(newData, commandTextHistory);
    }

    @Test
    public void addCommandText_nullCommandText_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> commandTextHistory.addCommandText(null));
    }

    @Test
    public void addCommandText_moreThanMaxSize_commandHistoryTruncated() {
        CommandTextHistory expected = new CommandTextHistory();
        for (int i = 1; i <= CommandTextHistory.MAX_HISTORY_SIZE; i++) {
            expected.addCommandText("command " + i);
        }

        CommandTextHistory toTest = new CommandTextHistory();
        for (int i = 0; i <= CommandTextHistory.MAX_HISTORY_SIZE; i++) {
            toTest.addCommandText("command " + i);
        }

        assertEquals(expected, toTest);
    }

    @Test
    public void getPreviousCommandText_noCommandHistory_returnsEmptyString() {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        assertEquals("", commandTextHistory.getPreviousCommandString());
    }

    @Test
    public void getPreviousCommandText_noPreviousCommand_returnsCurrentCommandText() {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        commandTextHistory.addCommandText("command 1");
        assertEquals("command 1", commandTextHistory.getPreviousCommandString());
        assertEquals("command 1", commandTextHistory.getPreviousCommandString());
    }

    @Test
    public void getNextCommandText_noCommandHistory_returnsEmptyString() {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        assertEquals("", commandTextHistory.getNextCommandString());
    }

    @Test
    public void getNextCommandText_noNextCommand_returnsEmpty() {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        commandTextHistory.addCommandText("command 1");
        assertEquals("", commandTextHistory.getNextCommandString());
    }

    @Test
    public void setCommandTextList_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> commandTextHistory.setCommandTextList(null));
    }

    @Test
    public void setCommandTextList_validList_success() {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        CommandTextHistory newData = getTypicalCommandTextHistory();
        commandTextHistory.setCommandTextList(newData.getCommandTextList());
        assertEquals(newData, commandTextHistory);
    }

    @Test
    public void setCommandTextList_validList_replacesData() {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        commandTextHistory.addCommandText("some command");
        CommandTextHistory newData = getTypicalCommandTextHistory();
        commandTextHistory.setCommandTextList(newData.getCommandTextList());
        assertEquals(newData, commandTextHistory);
    }

    @Test
    public void equals() {
        CommandTextHistory commandTextHistory1 = getTypicalCommandTextHistory();
        CommandTextHistory commandTextHistory2 = getTypicalCommandTextHistory();
        CommandTextHistory commandTextHistory3 = new CommandTextHistory();

        // same values -> returns true
        assertTrue(commandTextHistory1.equals(commandTextHistory2));

        // same object -> returns true
        assertTrue(commandTextHistory1.equals(commandTextHistory1));

        // null -> returns false
        assertFalse(commandTextHistory1.equals(null));

        // different type -> returns false
        assertFalse(commandTextHistory1.equals(5));

        // different values -> returns false
        assertFalse(commandTextHistory1.equals(commandTextHistory3));
    }

}
