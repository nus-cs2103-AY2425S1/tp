package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.PersonDepartmentComparator;

/**
 * Sorts the contents of StaffSync by name.
 */
public class SortDepartmentCommand extends SortCommand {

    public static final String ARGUMENT_WORD = "dept";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by department";

    public SortDepartmentCommand(boolean isReversed) {
        super(isReversed);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isReversed) {
            model.updateSortedPersonList(new PersonDepartmentComparator().reversed());
            return new CommandResult(MESSAGE_SUCCESS + DESCENDING_SUCCESS);
        } else {
            model.updateSortedPersonList(new PersonDepartmentComparator());
            return new CommandResult(MESSAGE_SUCCESS + ASCENDING_SUCCESS);
        }
    }

}
