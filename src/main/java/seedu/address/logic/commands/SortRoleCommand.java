package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.PersonRoleComparator;

/**
 * Sorts the contents of StaffSync by name.
 */
public class SortRoleCommand extends SortCommand {

    public static final String ARGUMENT_WORD = "role";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by role";

    public SortRoleCommand(boolean isReversed) {
        super(isReversed);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isReversed) {
            model.updateSortedPersonList(new PersonRoleComparator().reversed());
            return new CommandResult(MESSAGE_SUCCESS + DESCENDING_SUCCESS);
        } else {
            model.updateSortedPersonList(new PersonRoleComparator());
            return new CommandResult(MESSAGE_SUCCESS + ASCENDING_SUCCESS);
        }
    }

}
