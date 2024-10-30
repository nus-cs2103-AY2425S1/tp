package seedu.address.model.history;

import seedu.address.logic.commands.Command;

/**
 * Stores the original input from user and the command that have been executed.
 */
public class HistoryCommand {
    private final Command command;
    private final String input;
    private static int value = 0;
    private int index;

    private HistoryCommand(Command command, String input, int index) {
        this.command = command;
        this.input = input;
        this.index = index;
    }

    public static HistoryCommand of(Command command, String input) {
        return new HistoryCommand(command, input, value++);
    }

    /**
     * Returns the original command text from user.
     */
    public String getOriginalCommandText() {
        return this.input;
    }

    /**
     * Returns the index for the past command.
     */
    public int getIndex() {
        return index;
    }
}
