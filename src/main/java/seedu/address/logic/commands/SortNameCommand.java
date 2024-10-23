package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.PersonNameComparator;

/**
 * Sorts the contents of StaffSync by name.
 */
public class SortNameCommand extends SortCommand {

    public static final String ARGUMENT_WORD = "name";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by name";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedPersonList(new PersonNameComparator());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
