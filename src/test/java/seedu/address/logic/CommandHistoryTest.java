package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
        history.add("first command");
        history.add("second command");
    }

    @Test
    public void add() {
        history.add("third command");

        assertEquals(history.getHistory().size(), 3);
        assertEquals(history.getHistory().get(2), "third command");
    }

    @Test
    public void getHistory() {
        ObservableList<String> historyList = history.getHistory();
        assertEquals(historyList.size(), 2);
        assertEquals(historyList.get(0), "first command");
        assertEquals(historyList.get(1), "second command");
    }

    @Test
    void getPrevious() {
        assertEquals("second command", history.getPrevious());
        assertEquals("first command", history.getPrevious());
        assertEquals("first command", history.getPrevious()); // Stay at earliest command
    }

    @Test
    void getNext() {
        assertEquals("second command", history.getPrevious());
        assertEquals("first command", history.getPrevious());
        assertEquals("second command", history.getNext());
        assertEquals("", history.getNext());
        assertEquals("", history.getNext()); // Stay blank at the end
    }

    @Test
    void equals() {
        CommandHistory newHistory = new CommandHistory();
        CommandHistory otherHistory = new CommandHistory();

        assertTrue(newHistory.equals(newHistory));

        assertTrue(newHistory.equals(otherHistory));
        assertFalse(newHistory.equals(1));

        newHistory.add("first command");
        assertFalse(newHistory.equals(otherHistory));

        otherHistory.add("first command");
        assertTrue(newHistory.equals(otherHistory));
    }
}
