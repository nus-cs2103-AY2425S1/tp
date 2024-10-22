package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.PersonDateComparator;

/**
 * Sorts the contents of StaffSync by name.
 */
public class SortDateCommand extends SortCommand {

    public static final String ARGUMENT_WORD = "date";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by date";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        model.updateSortedPersonList(new PersonDateComparator());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
