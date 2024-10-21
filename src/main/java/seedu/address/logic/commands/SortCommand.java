package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts all persons in the address book by name.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Sorts alphabetically by name. ";

    public static final String MESSAGE_SORT_LIST_SUCCESS = "Sorted by name";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateSortedPersonList();
        return new CommandResult(MESSAGE_SORT_LIST_SUCCESS);
    }
}
