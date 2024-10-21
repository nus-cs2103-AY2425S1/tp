package seedu.address.logic.commands;

/**
 * Sorts the contents of StaffSync.
 */
public abstract class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the contents of StaffSync.\n"
            + "Parameters: name\n"
            + "Example: " + COMMAND_WORD + " name";
}
