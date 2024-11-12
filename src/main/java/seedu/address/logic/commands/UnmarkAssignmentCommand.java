package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.student.Student;

/**
 * Command to mark an assignment as not done.
 */
public class UnmarkAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "unmarkAsg";
    public static final String SUCCESS_MESSAGE = "Assignment unmarked successfully!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the student at specified index as not completed for the assignment. " + "Parameters: "
            + "<Index>: STUDENT INDEX "
            + PREFIX_NAME + "ASSIGNMENT NAME \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Assignment 1 ";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "There is no matching assignment with the given name!";

    private final int targetIndex;
    private final Assignment assignment;

    /**
     * Constructs a UnmarkAssignmentCommand with the specified index and assignment.
     *
     * @param targetIndex The index of the assignment to be unmarked.
     * @param assignment The assignment to be added.
     */
    public UnmarkAssignmentCommand(int targetIndex, Assignment assignment) {
        this.assignment = assignment;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        Student targetStudent = lastShownList.get(targetIndex);

        try {
            model.setAssignmentStatus(assignment, targetStudent, false);
        } catch (AssignmentNotFoundException e) {
            throw new CommandException(MESSAGE_ASSIGNMENT_NOT_FOUND);
        }
        return new CommandResult(SUCCESS_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnmarkAssignmentCommand)) {
            return false;
        }
        UnmarkAssignmentCommand e = (UnmarkAssignmentCommand) other;
        return assignment.equals(e.assignment) && targetIndex == e.targetIndex;
    }
}
