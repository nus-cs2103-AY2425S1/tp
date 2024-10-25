package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;


/**
 * Deletes an assignment.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "deletea";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an assignment for a student. "
            + "Parameters: "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_DEADLINE + "DEADLINE (OPTIONAL)"
            + PREFIX_STATUS + "SUBMISSION STATUS (OPTIONAL)"
            + PREFIX_STATUS + "GRADING STATUS (OPTIONAL)"
            + PREFIX_GRADE + "GRADE (OPTIONAL)"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ASSIGNMENT + "Math Quiz ";

    public static final String MESSAGE_SUCCESS = "Deleted Assignment: %1$s from %2$s";

    public static final String MESSAGE_NO_STUDENT_FOUND = "No such student found!";
    public static final String MESSAGE_NO_ASSIGNMENT_FOUND = "No such assignment found!";

    public final AssignmentQuery assignmentQuery;
    public final Name name;
    private int index;
    private Assignment assignment;

    /**
     * Creates an DeleteAssignmentCommand to add the specified {@code Assignment}
     */
    public DeleteAssignmentCommand(Name name, AssignmentQuery assignmentQuery) {
        this.name = name;
        this.assignmentQuery = assignmentQuery;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Student student = model.getStudentByName(name);

        if (student == null) {
            throw new CommandException(MESSAGE_NO_STUDENT_FOUND);
        }

        index = student.getAssignmentIndex(assignmentQuery);

        if (index == -1) {
            throw new CommandException(MESSAGE_NO_ASSIGNMENT_FOUND);
        }

        assignment = student.deleteAssignment(index);
        return new CommandResult(String.format(MESSAGE_SUCCESS, assignment.getAssignmentName(), student.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteAssignmentCommand)) {
            return false;
        }

        DeleteAssignmentCommand otherCommand = (DeleteAssignmentCommand) other;
        return otherCommand.name.equals(this.name)
                && otherCommand.assignmentQuery.equals(this.assignmentQuery);
    }

    @Override
    public boolean undo(Model model) {
        // The command will only have been executed if assignment was assigned a value
        if (assignment == null) {
            return false;
        }

        Student student = model.getStudentByName(name);
        student.addAssignment(index, assignment);
        return true;
    }
}
