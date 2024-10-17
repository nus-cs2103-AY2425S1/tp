package seedu.address.logic.commands;

/**
 * Filters the address book by a given prefix and displays the filtered list to the user
 * Users can only filter by one field at a time
 */
public abstract class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
}
