package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the storage and retrieval of the history of command texts entered by
 * the user.
 */
public class CommandTextHistory {
    private static final int MAX_HISTORY_SIZE = 50;
    private final List<String> commandTextHistory;
    private int currentCommandIndex = -1;

    public CommandTextHistory() {
        commandTextHistory = new ArrayList<>();
    }

    /**
     * Adds a command text to the history.
     */
    public void addCommandText(String commandText) {
        commandTextHistory.add(commandText);
        truncateHistory();
        currentCommandIndex = commandTextHistory.size() - 1;
    }

    /**
     * Returns the command text entered before the currently displayed command text.
     * If there is no previous command text, the current command text is returned.
     */
    public String getPreviousCommandString() {
        currentCommandIndex = Math.max(0, currentCommandIndex - 1);
        return getCommandText();
    }

    /**
     * Returns the command text entered after the currently displayed command text.
     * If there is no next command text, an empty string is returned.
     */
    public String getNextCommandString() {
        currentCommandIndex = Math.min(commandTextHistory.size(), currentCommandIndex + 1);
        return getCommandText();
    }

    private String getCommandText() {
        System.out.println(commandTextHistory);
        if (commandTextHistory.isEmpty() || currentCommandIndex < 0
                || currentCommandIndex >= commandTextHistory.size()) {
            return "";
        } else {
            return commandTextHistory.get(currentCommandIndex);
        }
    }

    private void truncateHistory() {
        if (commandTextHistory.size() > MAX_HISTORY_SIZE) {
            commandTextHistory.remove(0);
        }
    }
}
