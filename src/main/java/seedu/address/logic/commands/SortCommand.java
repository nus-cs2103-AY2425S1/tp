package seedu.address.logic.commands;

/**
 * Sorts the contents of StaffSync.
 */
public abstract class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_PURPOSE = "Sorts the contents of StaffSync.";

    public static final String MESSAGE_FORMAT = COMMAND_WORD + " (name/date)";

    public static final String MESSAGE_EXAMPLE = COMMAND_WORD + " name";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + MESSAGE_PURPOSE
            + "\nFormat: " + MESSAGE_FORMAT
            + "\nExample: " + MESSAGE_EXAMPLE;
}
