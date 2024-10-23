package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_SCORE;
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
 * Edits the score of an existing assignment belonging to an existing student in the app.
 */
public class EditAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "edit_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the score of an existing assignment "
            + "belonging to an existing student based on the student's index number in the displayed student list, "
            + "and the assignment's index belonging to the student."
            + "Parameters: "
            + "[" + PREFIX_STUDENT_INDEX + "INDEX] "
            + "[" + PREFIX_ASSIGNMENT_INDEX + "INDEX] "
            + "[" + PREFIX_ASSIGNMENT_SCORE + "SCORE] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_ASSIGNMENT_INDEX + "1 "
            + PREFIX_ASSIGNMENT_SCORE + "85";

    public static final String MESSAGE_EDIT_SUCCESS = "Edited Assignment: %1$s belonging "
            + "to Student: %2$s with new score of: %3$d";
    private final Index studentIndex;
    private final Index assignmentIndex;
    private final int newScore;

    /**
     * @param studentIndex of the student in the filtered student list
     * @param assignmentIndex of the assignment belonging to the assignmentList of the student
     * @param newScore the new score to be set for the assignment
     */
    public EditAssignmentCommand(Index studentIndex, Index assignmentIndex, int newScore) {
        assert studentIndex != null;
        assert assignmentIndex != null;
        requireNonNull(studentIndex);
        requireNonNull(assignmentIndex);
        this.studentIndex = studentIndex;
        this.assignmentIndex = assignmentIndex;
        this.newScore = newScore;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(studentIndex.getZeroBased());
        List<Assignment> assignmentList = studentToEdit.getAssignmentList();

        if (assignmentIndex.getZeroBased() >= assignmentList.size() || assignmentIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_INDEX);
        }

        Assignment assignmentToEdit = assignmentList.get(assignmentIndex.getZeroBased());
        assignmentToEdit.setScore(newScore);

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, assignmentToEdit.getName(),
                studentToEdit.getName().fullName, newScore), studentToEdit, studentIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles null
        if (!(other instanceof EditAssignmentCommand)) {
            return false;
        }
        EditAssignmentCommand otherEditAssignmentCommand = (EditAssignmentCommand) other;
        return studentIndex.equals(otherEditAssignmentCommand.studentIndex)
                && assignmentIndex.equals(otherEditAssignmentCommand.assignmentIndex)
                && newScore == otherEditAssignmentCommand.newScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("assignmentIndex", assignmentIndex)
                .add("newScore", newScore)
                .toString();
    }
}
