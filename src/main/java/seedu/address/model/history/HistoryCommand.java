package seedu.address.model.history;

import seedu.address.logic.commands.Command;

/**
 * Stores the original input from user and the command that have been executed.
 */
public class HistoryCommand {
    private final Command command;
    private final String input;
    private static int pointer = 0;
    private HistoryCommand(Command command, String input, int pointer) {
        this.command = command;
        this.input = input;
        this.pointer = pointer;
    }

    public static HistoryCommand of(Command command, String input) {
        return new HistoryCommand(command, input, pointer++);
    }

    /**
     * Returns the original command text from user.
     */
    public String getOriginalCommandText() {
        return this.input;
    }
}
