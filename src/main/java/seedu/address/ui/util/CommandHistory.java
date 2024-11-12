package seedu.address.ui.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the history of commands entered by the user.
 */
public class CommandHistory {
    private final List<String> commandHistory;
    private int historyIndex;

    /**
     * Constructs a {@code CommandHistory} object.
     */
    public CommandHistory() {
        commandHistory = new ArrayList<>();
        historyIndex = -1; // Start with no commands
    }

    /**
     * Adds a command to the history.
     * Resets the index to point to the latest command.
     *
     * @param command The command to be added to the history.
     */
    public void addCommand(String command) {
        commandHistory.add(command);
        historyIndex = commandHistory.size(); // Reset to end of list
    }

    /**
     * Returns the previous command in history.
     * If at the start, it returns null.
     *
     * @return The previous command, or null if at the start of the list.
     */
    public String getPreviousCommand() {
        if (historyIndex > 0) {
            historyIndex--;
            return commandHistory.get(historyIndex);
        }
        return null; // No previous command
    }

    /**
     * Returns the next command in history.
     * If at the end, it returns null.
     *
     * @return The next command, or null if at the end of the list.
     */
    public String getNextCommand() {
        if (historyIndex < commandHistory.size() - 1) {
            historyIndex++;
            return commandHistory.get(historyIndex);
        }
        historyIndex = commandHistory.size(); // Reset to end
        return null; // No next command
    }
}
