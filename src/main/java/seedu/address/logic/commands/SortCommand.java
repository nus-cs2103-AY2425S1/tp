package seedu.address.logic.commands;

/**
 * Sorts the contents of StaffSync.
 */
public abstract class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String ARGUMENT_WORD_ASC = "asc";

    public static final String ARGUMENT_WORD_DESC = "desc";

    public static final String ASCENDING_SUCCESS = " in ascending order";

    public static final String DESCENDING_SUCCESS = " in descending order";

    public static final String MESSAGE_PURPOSE = "Sorts the contents of StaffSync.";

    public static final String MESSAGE_FORMAT = COMMAND_WORD + " (name/date/dept/role) [asc/desc]";

    public static final String MESSAGE_EXAMPLE = COMMAND_WORD + " name asc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + MESSAGE_PURPOSE
            + "\nFormat: " + MESSAGE_FORMAT
            + "\nExample: " + MESSAGE_EXAMPLE;

    protected boolean isReversed;

    /**
     * Creates a SortCommand to sort the list in the specified order.
     */
    public SortCommand(boolean isReversed) {
        this.isReversed = isReversed;
    }
}
