package seedu.address.model.history;

import seedu.address.logic.commands.Command;

/**
 * Stores the original command text from user and the command that have been executed.
 */
public class HistoryCommand {
    private static int value = 0;
    private final Command command;
    private final String commandText;
    private int index;

    private HistoryCommand(Command command, String commandText, int index) {
        this.command = command;
        this.commandText = commandText;
        this.index = index;
    }

    public static HistoryCommand of(Command command, String commandText) {
        return new HistoryCommand(command, commandText, value++);
    }

    /**
     * Returns the original command text from user.
     */
    public String getOriginalCommandText() {
        return this.commandText;
    }

    /**
     * Returns the index for the past command.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the internal command for testing purpose.
     */
    public Command getCommand() {
        return this.command;
    }

    /**
     * Checks the two {@Code HistoryCommand} instance to see if they are equal.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoryCommand)) {
            return false;
        }

        HistoryCommand otherCommand = (HistoryCommand) other;

        return this.command.equals(otherCommand.getCommand())
                && this.commandText.equals(otherCommand.getOriginalCommandText());
    }

    /**
     * Reset the value for pointer for testing purpose.
     */
    public static void resetIndex() {
        value = 0;
    }
}
