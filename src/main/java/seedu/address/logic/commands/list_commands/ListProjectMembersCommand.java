package seedu.address.logic.commands.list_commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentProjectPredicate;
import seedu.address.model.person.Person;
import seedu.address.ui.DisplayType;

/**
 * Lists all members of a specified project in the address book.
 */
public class ListProjectMembersCommand extends ListCommand {

    // Command word using the 'list' prefix + 'projectmembers'
    public static final String COMMAND_WORD = COMMAND_PREFIX + "projectmembers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all members of the specified project.\n"
            + "Parameters: PROJECT_NAME\n"
            + "Example: " + COMMAND_WORD + " Alpha";

    private final AssignmentProjectPredicate predicate;

    public ListProjectMembersCommand(AssignmentProjectPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredAssignmentList(predicate);
        List<Assignment> filteredAssignments = model.getFilteredAssignmentList();

        model.updateFilteredPersonList(person ->
                filteredAssignments.stream()
                        .map(Assignment::getPerson)
                        .distinct()
                        .anyMatch(person::equals)
        );

        return new CommandResult(
                String.format(Messages.MESSAGE_PROJECT_MEMBERS_LISTED, filteredAssignments.size()), DisplayType.PERSON_LIST);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListProjectMembersCommand
                && predicate.equals(((ListProjectMembersCommand) other).predicate));
    }
}
