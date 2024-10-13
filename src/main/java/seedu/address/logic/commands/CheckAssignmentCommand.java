package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;

/**
 * Command to check statuses of specified assignment.
 */
public class CheckAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "checkAsg";
    public static final String SUCCESS_MESSAGE = "Below are the assignment completion statistics for: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Check statuses of the assignment. "
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Assignment 1 ";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "There is no matching assignment with the given name!";

    private Assignment assignment;

    /**
     * Constructs an AddAssignmentCommand with the specified assignment.
     *
     * @param assignment The assignment to be added.
     */
    public CheckAssignmentCommand(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            String assignmentStatus = model.checkAssignment(assignment);
            return new CommandResult(SUCCESS_MESSAGE
                    + "\n" + assignmentStatus);
        } catch (AssignmentNotFoundException e) {
            throw new CommandException(MESSAGE_ASSIGNMENT_NOT_FOUND);
        }
    }
}
