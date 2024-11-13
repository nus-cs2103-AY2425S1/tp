package seedu.address.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Stores the history of command texts entered by the user.
 * It stores all texts entered by the user, whether they are valid or not.
 */
public class CommandTextHistory {

    private final ArrayList<String> commandHistory;
    private int currentCommandIndex;
    private final Logger logger = LogsCenter.getLogger(getClass());


    /**
     * Creates a new CommandTextHistory object.
     */
    public CommandTextHistory() {
        commandHistory = new ArrayList<>();
        currentCommandIndex = 0;
    }

    /**
     * Adds a command text to the history.
     * @param commandText The command text to be added.
     */
    public void addCommandToHistory(String commandText) {
        if (commandText == null) {
            // this should not happen, but just in case
            return;
        }
        commandHistory.add(commandText);
        currentCommandIndex = commandHistory.size();
        logger.info("Command added to history: " + commandText);
    }

    /**
     * Retrieves the previous command text in the history.
     * @return The previous command text in the history.
     */
    public String getPreviousCommand() {
        if (currentCommandIndex > 0) {
            currentCommandIndex--;
        }

        if (currentCommandIndex < 0 || currentCommandIndex >= commandHistory.size()) {
            return "";
        }
        return commandHistory.get(currentCommandIndex);
    }

    /**
     * Retrieves the next command text in the history.
     * @return The next command text in the history.
     */
    public String getNextCommand() {
        if (currentCommandIndex < commandHistory.size() - 1) {
            currentCommandIndex++;
        }

        if (currentCommandIndex < 0 || currentCommandIndex >= commandHistory.size()) {
            return "";
        }
        return commandHistory.get(currentCommandIndex);
    }
}
