package seedu.address.logic.commands;

/**
 * Lists either all persons or events in the address book to the user.
 * Keyword matching is case-insensitive.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
}
