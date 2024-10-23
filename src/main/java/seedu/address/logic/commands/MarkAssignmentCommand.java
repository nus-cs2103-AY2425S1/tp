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
 * Edits the details of score of an existing assignment belonging to an existing student in the app.
 */
public class MarkAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an existing assignment belonging to an existing "
            + "student based on the student's index number used in the displayed student list, and the assignment's "
            + "index belonging to the student as submitted."
            + "Parameters: "
            + "[" + PREFIX_STUDENT_INDEX + "INDEX] "
            + "[" + PREFIX_ASSIGNMENT_INDEX + "INDEX] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_ASSIGNMENT_INDEX + "1 ";

    public static final String MESSAGE_MARK_SUCCESS = "Marked Assignment: %1$s belonging "
            + "to Student: %2$s as Submitted";
    private final Index studentIndex;
    private final Index assignmentIndex;

    /**
     * @param studentIndex of the student in the filtered student list
     * @param assignmentIndex of the assignment belonging to the assignmentList of the student
     */
    public MarkAssignmentCommand(Index studentIndex, Index assignmentIndex) {
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

        if (studentIndex.getZeroBased() >= lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        Student studentToMark = lastShownList.get(studentIndex.getZeroBased());
        List<Assignment> assignmentList = studentToMark.getAssignmentList();

        if (assignmentIndex.getZeroBased() >= assignmentList.size() || assignmentIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_INDEX);
        }
        Assignment assignmentToMark = assignmentList.get(assignmentIndex.getZeroBased());
        if (assignmentToMark.getHasSubmitted()) {
            throw new CommandException(Messages.MESSAGE_ALREADY_MARKED);
        }
        assignmentToMark.setHasSubmitted(true);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_MARK_SUCCESS, assignmentToMark.getName(),
                studentToMark.getName().fullName), studentToMark, studentIndex.getZeroBased());
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        //instanceof handles null
        if (!(other instanceof MarkAssignmentCommand)) {
            return false;
        }
        MarkAssignmentCommand otherMarkAssignmentCommand = (MarkAssignmentCommand) other;
        return studentIndex.equals(otherMarkAssignmentCommand.studentIndex)
                && assignmentIndex.equals(otherMarkAssignmentCommand.assignmentIndex);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("assignmentIndex", assignmentIndex)
                .toString();
    }
}
