package tuteez.logic.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the history of commands entered by the user.
 */
public class CommandHistory {
    private final List<String> history;
    private int currentIndex;

    /**
     * Creates a CommandHistory class to remember the history of commands the user entered.
     * The current index is initially set to -1 to indicate that no commands have been accessed
     * or selected from the history yet.
     */
    public CommandHistory() {
        this.history = new ArrayList<>();
        this.currentIndex = -1;
    }

    /**
     * Adds a new command to the history and resets the current index.
     */
    public void add(String command) {
        assert command != null;
        history.add(command);
        // Reset index to the end of the list.
        currentIndex = history.size();
    }

    /**
     * Returns the previous command in history, or null if no previous command exists.
     */
    public String getPreviousCommand() {
        assert currentIndex >= -1 && currentIndex <= history.size();
        if (currentIndex > 0) {
            currentIndex--;
            return history.get(currentIndex);
        }
        return null; // No previous command available
    }

    /**
     * Returns the next command in history, or an empty string if no next command exists.
     */
    public String getNextCommand() {
        assert currentIndex >= -1 && currentIndex <= history.size();
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            return history.get(currentIndex);
        }
        currentIndex = history.size();
        return "";
    }

}
