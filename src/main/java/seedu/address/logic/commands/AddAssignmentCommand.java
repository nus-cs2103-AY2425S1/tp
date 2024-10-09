package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;

/**
 * Command to add an assignment to the model.
 */
public class AddAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "addAsg";
    public static final String SUCCESS_MESSAGE = "Assignment added successfully!";

    private final Assignment assignment;

    /**
     * Constructs an AddAssignmentCommand with the specified assignment.
     *
     * @param assignment The assignment to be added.
     */
    public AddAssignmentCommand(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (model.hasAssignment(assignment)) {
            throw new DuplicateAssignmentException();
        }

        model.addAssignment(assignment);
        return new CommandResult(SUCCESS_MESSAGE + "\n" + assignment.toString());
    }
}
