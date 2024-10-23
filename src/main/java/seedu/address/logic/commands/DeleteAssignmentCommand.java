package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

/**
 * Deletes an existing assignment belonging to an existing student in the app
 */
public class DeleteAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "delete_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an assignment belonging to a "
            + "student based on the student's index number used in the displayed student list,"
            + " and the assignment's index"
            + " belonging to the student."
            + "Parameters: "
            + "[" + PREFIX_STUDENT_INDEX + "INDEX] "
            + "[" + PREFIX_ASSIGNMENT_INDEX + "INDEX] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_ASSIGNMENT_INDEX + "1 ";
    public static final String MESSAGE_DELETE_SUCCESS = "Deleted Assignment: %1$s belonging "
            + "to Student: %1$s";
    private final Index studentIndex;
    private final Index assignmentIndex;
    /**
     * @param studentIndex of the student in the filtered student list
     * @param assignmentIndex of the assignment belonging to the assignmentList of the student
     */
    public DeleteAssignmentCommand(Index studentIndex, Index assignmentIndex) {
        assert studentIndex != null;
        assert assignmentIndex != null;
        requireNonNull(studentIndex);
        requireNonNull(assignmentIndex);
        this.studentIndex = studentIndex;
        this.assignmentIndex = assignmentIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        Student student = lastShownList.get(studentIndex.getZeroBased());
        List<Assignment> assignmentList = student.getAssignmentList();

        if (assignmentIndex.getZeroBased() >= assignmentList.size() || assignmentIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_INDEX);
        }
        Assignment assignmentToDelete = assignmentList.get(assignmentIndex.getZeroBased());
        assignmentList.remove(assignmentIndex.getZeroBased());
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, assignmentToDelete.getName(),
                student.getName().fullName), student, studentIndex.getZeroBased());
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        //instanceof handles null
        if (!(other instanceof DeleteAssignmentCommand)) {
            return false;
        }
        DeleteAssignmentCommand otherDeleteAssignmentCommand = (DeleteAssignmentCommand) other;
        return studentIndex.equals(otherDeleteAssignmentCommand.studentIndex)
                && assignmentIndex.equals(otherDeleteAssignmentCommand.assignmentIndex);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("assignmentIndex", assignmentIndex)
                .toString();
    }
}
