package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Lists all persons in the address book to the user.
 */
public class ListAssignmentsCommand extends Command {
    public static final String COMMAND_WORD = "listassignments";
    public static final String MESSAGE_SUCCESS = "Listed all assignments";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        return new CommandResult(MESSAGE_SUCCESS, DisplayType.ASSIGNMENT_LIST);
    }
}
