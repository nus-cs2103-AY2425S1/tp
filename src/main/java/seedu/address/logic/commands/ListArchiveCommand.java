package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all archived persons in the address book to the user.
 */
public class ListArchiveCommand extends Command {
    public static final String COMMAND_WORD = "listarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all archived persons in the address book as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all archived persons";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFilteredPersonListMasterPredicate(
                Model.FilteredPersonListMasterPredicate.SHOW_ONLY_ARCHIVED_PERSONS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
