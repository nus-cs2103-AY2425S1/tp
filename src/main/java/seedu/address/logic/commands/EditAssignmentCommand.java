package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;

/**
 * Edits the score of an existing assignment belonging to an existing student in the app.
 */
public class EditAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "edit_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the detail of an existing assignment "
            + "belonging to an existing student based on the student's index number in the displayed student list, "
            + "and the assignment's index belonging to the student. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_STUDENT_INDEX + "INDEX "
            + PREFIX_ASSIGNMENT_INDEX + "INDEX "
            + "[" + PREFIX_ASSIGNMENT_NAME + "NEW ASSIGNMENT NAME] "
            + "[" + PREFIX_ASSIGNMENT_MAX_SCORE + "NEW MAX SCORE] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_ASSIGNMENT_INDEX + "1 "
            + PREFIX_ASSIGNMENT_NAME + "Assignment 100 "
            + PREFIX_ASSIGNMENT_MAX_SCORE + "100";

    public static final String MESSAGE_EDIT_SUCCESS = "Edited Assignment: assignment index %1$s belonging "
            + "to Student: %2$s, with a new name of %3$s and a new max score of %4$s.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book.";
    private final Index studentIndex;
    private final Index assignmentIndex;
    private final EditAssignmentDescriptor editAssignmentDescriptor;

    /**
     * @param studentIndex of the student in the filtered student list
     * @param assignmentIndex of the assignment belonging to the assignmentList of the student
     * @param editAssignmentDescriptor details to edit the assignment with
     */
    public EditAssignmentCommand(Index studentIndex, Index assignmentIndex,
                                 EditAssignmentDescriptor editAssignmentDescriptor) {
        assert studentIndex != null;
        assert assignmentIndex != null;
        requireNonNull(studentIndex);
        requireNonNull(assignmentIndex);
        this.studentIndex = studentIndex;
        this.assignmentIndex = assignmentIndex;
        this.editAssignmentDescriptor = editAssignmentDescriptor;
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
        Assignment editedAssignment = createEditedAssignment(assignmentToEdit, editAssignmentDescriptor);

        if (!assignmentToEdit.isSameAssignment(editedAssignment) && studentToEdit.hasAssignment(editedAssignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        studentToEdit.updateAssignment(assignmentIndex, editedAssignment);

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, assignmentIndex.getOneBased(),
                studentToEdit.getName().fullName, editedAssignment.getName(), editedAssignment.getMaxScore()),
                studentToEdit, studentIndex.getZeroBased());
    }

    private static Assignment createEditedAssignment(Assignment assignmentToEdit,
                                           EditAssignmentDescriptor editAssignmentDescriptor) {
        assert assignmentToEdit != null;

        AssignmentName updatedName =
                editAssignmentDescriptor.getName().orElse(assignmentToEdit.getAssignmentName());
        int updatedMaxScore = editAssignmentDescriptor.getMaxScore().orElse(assignmentToEdit.getMaxScore());

        return new Assignment(updatedName, updatedMaxScore);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles null
        if (!(other instanceof EditAssignmentCommand otherEditAssignmentCommand)) {
            return false;
        }
        return studentIndex.equals(otherEditAssignmentCommand.studentIndex)
                && assignmentIndex.equals(otherEditAssignmentCommand.assignmentIndex)
                && editAssignmentDescriptor.equals(otherEditAssignmentCommand.editAssignmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("assignmentIndex", assignmentIndex)
                .add("editAssignmentDescriptor", editAssignmentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the assignment with. Each non-empty field value will replace the
     * corresponding field value of the assignment.
     */
    public static class EditAssignmentDescriptor {
        private AssignmentName assignmentName;
        private Integer maxScore;

        public EditAssignmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAssignmentDescriptor(EditAssignmentDescriptor toCopy) {
            setName(toCopy.assignmentName);
            setMaxScore(toCopy.maxScore);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(assignmentName, maxScore);
        }

        public void setName(AssignmentName name) {
            this.assignmentName = name;
        }

        public Optional<AssignmentName> getName() {
            return Optional.ofNullable(assignmentName);
        }

        public void setMaxScore(int maxScore) {
            this.maxScore = maxScore;
        }

        public Optional<Integer> getMaxScore() {
            return Optional.ofNullable(maxScore);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAssignmentDescriptor otherEditAssignmentDescriptor)) {
                return false;
            }

            return Objects.equals(assignmentName, otherEditAssignmentDescriptor.assignmentName)
                    && maxScore == otherEditAssignmentDescriptor.maxScore;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("assignmentName", assignmentName)
                    .add("maxScore", maxScore)
                    .toString();
        }
    }
}
