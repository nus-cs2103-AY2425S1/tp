package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.PersonDateComparator;
import seedu.address.model.person.PersonDateReverseComparator;

/**
 * Sorts the contents of StaffSync by name.
 */
public class SortDateCommand extends SortCommand {

    public static final String ARGUMENT_WORD = "date";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by date";

    public SortDateCommand(boolean isReversed) {
        super(isReversed);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isReversed) {
            model.updateSortedPersonList(new PersonDateReverseComparator());
            return new CommandResult(MESSAGE_SUCCESS + DESCENDING_SUCCESS);
        } else {
            model.updateSortedPersonList(new PersonDateComparator());
            return new CommandResult(MESSAGE_SUCCESS + ASCENDING_SUCCESS);
        }
    }

}
