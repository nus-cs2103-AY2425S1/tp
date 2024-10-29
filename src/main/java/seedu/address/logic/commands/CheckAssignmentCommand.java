package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private static BooleanProperty isCheckingAssignment = new SimpleBooleanProperty(false);

    private Assignment assignment;

    /**
     * Constructs a CheckAssignmentCommand with the specified assignment.
     *
     * @param assignment The assignment to be checked.
     */
    public CheckAssignmentCommand(Assignment assignment) {
        requireNonNull(assignment);

        this.assignment = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            String assignmentStatus = model.checkAssignment(assignment);
            setCheckingAssignment(true);
            return new CommandResult(SUCCESS_MESSAGE
                    + "\n" + assignmentStatus);
        } catch (AssignmentNotFoundException e) {
            throw new CommandException(MESSAGE_ASSIGNMENT_NOT_FOUND);
        }
    }

    /**
     * Returns the boolean property indicating whether the assignment is currently being checked.
     */
    public static BooleanProperty isCheckingAssignmentProperty() {
        return isCheckingAssignment;
    }

    /**
     * Returns a boolean representing whether the system is checking assignment currently.
     */
    public static boolean isCheckingAssignment() {
        return isCheckingAssignment.get();
    }

    /**
     * Sets the isCheckingAssignment boolean to the desired input.
     * @param isCheckingAssignment New boolean value to be set.
     */
    public static void setCheckingAssignment(boolean isCheckingAssignment) {
        CheckAssignmentCommand.isCheckingAssignmentProperty().set(isCheckingAssignment);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CheckAssignmentCommand)) {
            return false;
        }
        CheckAssignmentCommand e = (CheckAssignmentCommand) other;
        return assignment.equals(e.assignment);
    }
}
