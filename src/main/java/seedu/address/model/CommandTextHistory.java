package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the storage and retrieval of the history of command texts entered by
 * the user.
 */
public class CommandTextHistory {
    public static final int MAX_HISTORY_SIZE = 50;
    private final List<String> commandTextList;
    private int currentCommandIndex = -1;

    public CommandTextHistory() {
        commandTextList = new ArrayList<>();
    }

    /**
     * Creates a Command Text History using the data in {@code toBeCopied}
     */
    public CommandTextHistory(CommandTextHistory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code CommandTextHistory} with
     * {@code newData}.
     */
    public void resetData(CommandTextHistory newData) throws NullPointerException {
        requireNonNull(newData);
        setCommandTextList(newData.getCommandTextList());
    }

    /**
     * Adds a command text to the history.
     */
    public void addCommandText(String commandText) {
        requireNonNull(commandText);
        commandTextList.add(commandText);
        truncateHistory();
        // Pressing the up arrow key should give the last command in the list, or size - 1.
        currentCommandIndex = commandTextList.size();
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
        currentCommandIndex = Math.min(commandTextList.size(), currentCommandIndex + 1);
        return getCommandText();
    }

    /**
     * Returns the history of command texts.
     */
    public List<String> getCommandTextList() {
        return new ArrayList<>(commandTextList);
    }

    /**
     * Sets the history of command texts.
     */
    public void setCommandTextList(List<String> commandTextList) {
        requireNonNull(commandTextList);
        this.commandTextList.clear();
        this.commandTextList.addAll(commandTextList);
        // Pressing the up arrow key should give the last command in the list, or size - 1.
        currentCommandIndex = commandTextList.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof CommandTextHistory
                && commandTextList.equals(((CommandTextHistory) other).commandTextList));
    }

    private String getCommandText() {
        if (commandTextList.isEmpty() || currentCommandIndex < 0
                || currentCommandIndex >= commandTextList.size()) {
            return "";
        } else {
            return commandTextList.get(currentCommandIndex);
        }
    }

    private void truncateHistory() {
        if (commandTextList.size() > MAX_HISTORY_SIZE) {
            commandTextList.remove(0);
        }
    }
}
