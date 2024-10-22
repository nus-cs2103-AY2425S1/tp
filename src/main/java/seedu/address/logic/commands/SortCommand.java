package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public abstract class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts owners or pets in the address book whose "
            + "relevant fields contain the specified keywords (case-insensitive) and displays them as a list with "
            + "index numbers.\n"
            + "To sort owners: sort owners\n"
            + "To sort pets: sort pets\n";

    @Override
    public abstract CommandResult execute(Model model);
}
