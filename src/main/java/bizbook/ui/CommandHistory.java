package bizbook.ui;

import java.util.logging.Logger;

import bizbook.commons.core.LogsCenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the command history for the CommandBox.
 */
public class CommandHistory {
    private ObservableList<String> history;
    private int currentIndex;
    private final Logger logger = LogsCenter.getLogger(CommandHistory.class);

    /**
     * Creates a {@code CommandHistory} to store command history.
     */
    public CommandHistory() {
        history = FXCollections.observableArrayList();
        currentIndex = -1;
    }

    /**
     * Adds a new successfully executed command to the history.
     *
     * @param command the command to add.
     * */
    public void addCommand(String command) {
        assert (command != null);
        history.add(command);
        currentIndex = history.size(); // Set to after the last command
        logger.info("Added command to command history: " + command);
    }

    /**
     * Gets the previous command in the history.
     *
     * @return the previous command, or an empty string if at the beginning of the history.
     */
    public String getPreviousCommand() {
        if (currentIndex > 0) {
            currentIndex--;
            logger.fine("Successfully retrieved previous command");
            return history.get(currentIndex);
        }
        return "";
    }

    /**
     * Gets the next command in the history.
     *
     * @return the next command, or an empty string if at the end of the history
     */
    public String getNextCommand() {
        if (currentIndex >= 0 && currentIndex < history.size()) {
            logger.fine("Successfully retrieved next command");
            return history.get(currentIndex++);
        }
        return "";
    }
}
