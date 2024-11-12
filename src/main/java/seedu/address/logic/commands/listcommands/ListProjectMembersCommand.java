package seedu.address.logic.commands.listcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentProjectPredicate;
import seedu.address.ui.DisplayType;

/**
 * Lists all members of a specified project in the address book.
 */
public class ListProjectMembersCommand extends ListCommand {

    // Command word using the 'list' prefix + 'projectmembers'
    public static final String COMMAND_WORD = COMMAND_PREFIX + "projectmembers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all members of the specified project.\n"
            + "Parameters: " + PREFIX_PROJECT_NAME + "PROJECT_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PROJECT_NAME + " Alpha";

    private final AssignmentProjectPredicate predicate;

    public ListProjectMembersCommand(AssignmentProjectPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // TODO: Check that this does not cause any side effects (e.g. change displayed
        // assignment list)
        model.updateFilteredAssignmentList(predicate);
        List<Assignment> filteredAssignments = model.getFilteredAssignmentList();

        model.updateFilteredEmployeeList(employee ->
                filteredAssignments.stream()
                        .map(Assignment::getEmployee)
                        .distinct()
                        .anyMatch(employee::equals)
        );

        return new CommandResult(
                String.format(Messages.MESSAGE_PROJECT_MEMBERS_LISTED,
                        filteredAssignments.size()), DisplayType.EMPLOYEE_LIST);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListProjectMembersCommand
                && predicate.equals(((ListProjectMembersCommand) other).predicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
