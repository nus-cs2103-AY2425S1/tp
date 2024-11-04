package seedu.address.logic.commands;

import java.util.ArrayList;

/**
 * Represents the command history of the current user session.
 */
public class CommandHistory extends ArrayList<String> {
    private static final CommandHistory COMMAND_HISTORY = new CommandHistory();
    private static int pointer = -1;

    /**
     * Constructs a command history.
     */
    private CommandHistory() {
    }

    /**
     * Factory method used to get the singleton instance of the command history. (used only for testing)
     */
    static CommandHistory getInstance() {
        return COMMAND_HISTORY;
    }

    /**
     * Gets the pointer of the command history. (used only for testing)
     */
    static int getPointer() {
        return pointer;
    }

    /**
     * Adds a command to the command history.
     * @param command The command to be added.
     */
    public static void addCommand(String command) {
        COMMAND_HISTORY.add(command);
        pointer = COMMAND_HISTORY.size();
    }

    /**
     * Gets the previous command in the command history. An empty string is returned if there are no command inputs.
     * If the pointer is at the first command, the first command is returned regardless of calls to this method.
     * @return The previous command in the command history.
     */
    public static String getPreviousPointerCommand() {
        if (COMMAND_HISTORY.isEmpty()) {
            return "";
        }

        if (pointer == 0) {
            return COMMAND_HISTORY.get(pointer);
        }

        return COMMAND_HISTORY.get(--pointer);
    }

    /**
     * Gets the next command in the command history. An empty string is returned if there are no more command inputs
     * to be retrieved.
     * @return The next command in the command history.
     */
    public static String getNextPointerCommand() {
        if (COMMAND_HISTORY.isEmpty() || pointer == COMMAND_HISTORY.size()) {
            return "";
        }

        if (pointer == COMMAND_HISTORY.size() - 1) {
            ++pointer;
            return "";
        }

        return COMMAND_HISTORY.get(++pointer);
    }
}
