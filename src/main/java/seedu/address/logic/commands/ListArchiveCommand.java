package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all archived persons in the address book to the user.
 */
public class ListArchiveCommand extends Command {

    public static final String COMMAND_WORD = "listarchive";

    public static final String MESSAGE_SUCCESS = "Listed all archived persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ARCHIVED_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
