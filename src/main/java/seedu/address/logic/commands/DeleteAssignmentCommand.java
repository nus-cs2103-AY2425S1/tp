package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Deletes an assignment identified using it's title from the address book.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "deleteAsg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the assignment identified by the title used in the displayed assignment list."
            + " Parameters: TITLE\n"
            + "Example: " + COMMAND_WORD + " CS2100 Assignment";

    public static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Deleted Assignment: %1$s";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "This assignment doesn't exist!";

    private final Assignment assignment;

    public DeleteAssignmentCommand(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasAssignment(assignment)) {
            throw new CommandException(MESSAGE_ASSIGNMENT_NOT_FOUND);
        }

        model.deleteAssignment(assignment);
        return new CommandResult(String.format(MESSAGE_DELETE_ASSIGNMENT_SUCCESS, assignment.getTitle()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAssignmentCommand)) {
            return false;
        }

        DeleteAssignmentCommand otherDeleteCommand = (DeleteAssignmentCommand) other;
        return assignment.equals(otherDeleteCommand.assignment);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignment", assignment)
                .toString();
    }
}
