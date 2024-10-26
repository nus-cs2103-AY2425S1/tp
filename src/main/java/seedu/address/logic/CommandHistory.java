package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a history of commands input by the user, allowing navigation through previous and next commands.
 */
public class CommandHistory {
    private final List<String> history;
    private int currentIndex;

    /**
     * Constructs an empty {@code CommandHistory} with an initial index set to -1.
     */
    public CommandHistory() {
        history = new ArrayList<>();
        currentIndex = -1;
    }

    /**
     * Adds a command to the history if it is non-blank and not a duplicate of the last command.
     *
     * @param command The command to be added to the history.
     */
    public void addCommand(String command) {
        if (!command.isBlank() && (history.isEmpty() || !history.get(history.size() - 1).equals(command))) {
            history.add(command);
        }
        currentIndex = history.size();
    }

    public String getPreviousCommand() {
        if (currentIndex > 0) {
            currentIndex--;
            return history.get(currentIndex);
        }
        return history.isEmpty() ? "" : history.get(0);
    }

    public String getNextCommand() {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            return history.get(currentIndex);
        } else {
            currentIndex = history.size();
            return "";
        }
    }
}
