package seedu.address.logic.commands.listcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Lists all assignments in the address book to the user.
 */
public class ListAssignmentsCommand extends ListCommand {

    // Command word using the 'list' prefix + 'assignments'
    public static final String COMMAND_WORD = COMMAND_PREFIX + "assignments";

    public static final String MESSAGE_SUCCESS = "%1$s assignments listed!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        int numAssignmentsShown = model.getFilteredAssignmentList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, numAssignmentsShown), DisplayType.ASSIGNMENT_LIST);
    }
}
