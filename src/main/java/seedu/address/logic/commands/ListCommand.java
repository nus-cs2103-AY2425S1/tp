package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Lists all persons in PawPatrol to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists owners or pets in the address book whose "
            + "relevant fields contain the specified keywords (case-insensitive) and displays them as a list with "
            + "index numbers.\n"
            + "To list owners: list owners\n"
            + "To list pets: list pets\n"
            + "To find both: list both\n";


    @Override
    public abstract CommandResult execute(Model model);
}
