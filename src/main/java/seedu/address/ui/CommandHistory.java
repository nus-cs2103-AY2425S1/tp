package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a history of commands executed, allowing navigation through previous and next commands.
 */
public class CommandHistory {
    private final List<String> history;
    private int currentIndex;

    /**
     * Constructs a new CommandHistory instance with an empty history.
     */
    public CommandHistory() {
        this.history = new ArrayList<>();
        this.currentIndex = -1;
    }

    /**
     * Adds a command to the command history.
     * Resets the current index to the end of the history.
     *
     * @param command The command string to add to the history.
     */
    public void addCommand(String command) {
        history.add(command);
        currentIndex = history.size(); // Reset index after adding a new command
    }

    /**
     * Retrieves the previous command in the history.
     * Moves the index back by one, if possible.
     *
     * @return The previous command as a String, or {@code null} if there is no previous command.
     */
    public String getPreviousCommand() {
        if (currentIndex > 0) {
            currentIndex--;
            return history.get(currentIndex);
        }
        return null; // Return null if no previous command
    }

    /**
     * Retrieves the next command in the history.
     * Moves the index forward by one, if possible.
     *
     * @return The next command as a String, or an empty String if there is no next command.
     */
    public String getNextCommand() {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            return history.get(currentIndex);
        } else {
            currentIndex = history.size(); // Reset to end if at the last command
            return "";
        }
    }
}
