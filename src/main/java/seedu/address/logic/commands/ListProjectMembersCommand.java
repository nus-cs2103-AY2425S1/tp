package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentProjectPredicate;
import seedu.address.model.person.Person;
import seedu.address.ui.DisplayType;

/**
 * Lists all members of a specified project in the address book.
 */
public class ListProjectMembersCommand extends Command {

    public static final String COMMAND_WORD = "listprojectmembers";

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

        // Step 1: Filter the assignments based on the predicate
        model.updateFilteredAssignmentList(predicate);

        // Step 2: Get the filtered assignment list
        List<Assignment> filteredAssignments = model.getFilteredAssignmentList();

        // Step 3: Extract unique persons from the filtered assignments
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> projectMembers = filteredAssignments.stream()
                .map(Assignment::getPerson)
                .distinct()
                .toList();

        // Step 4: Update the filtered person list in the model to include only project
        // members
        model.updateFilteredPersonList(projectMembers::contains);

        return new CommandResult(
                String.format(Messages.MESSAGE_PROJECT_MEMBERS_LISTED, projectMembers.size()),
                DisplayType.PERSON_LIST);
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
