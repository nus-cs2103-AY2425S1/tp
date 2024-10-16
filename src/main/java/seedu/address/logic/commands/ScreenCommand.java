package seedu.address.logic.commands;

/**
 * Lists all person in the address book to the user based on certain conditions.
 */
public abstract class ScreenCommand extends Command {

    public static final String COMMAND_WORD = "screen";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ":  Screen the entity identified by argument 'job' "
                    + "Parameters: [job]\nExample: " + COMMAND_WORD + " job 1";
}
