package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists owners or pets in the address book whose "
            + "relevant fields contain the specified keywords (case-insensitive) and displays them as a list with "
            + "index numbers.\n"
            + "To list owners: list owners\n"
            + "To find pets: list pets\n";


    @Override
    public abstract CommandResult execute(Model model);
}
