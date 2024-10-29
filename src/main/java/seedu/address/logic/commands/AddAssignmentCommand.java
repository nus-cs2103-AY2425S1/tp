package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;

/**
 * Adds an Assignment to the app.
 */
public class AddAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "add_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an Assignment to the app. "
            + "\nParameters: "
            + PREFIX_STUDENT_INDEX + "STUDENT INDEX "
            + PREFIX_ASSIGNMENT_NAME + "ASSIGNMENT NAME "
            + PREFIX_ASSIGNMENT_MAX_SCORE + "MAX SCORE "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_ASSIGNMENT_NAME + "Assignment 1 "
            + PREFIX_ASSIGNMENT_MAX_SCORE + "100";
    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book";
    private final Index index;
    private final AssignmentDescriptor toAddDescriptor;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Index index, AssignmentDescriptor toAddDescriptor) {
        assert toAddDescriptor != null;
        assert index != null;
        requireNonNull(toAddDescriptor);
        requireNonNull(index);
        this.index = index;
        this.toAddDescriptor = toAddDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        Student studentToAddAssignmentTo = lastShownList.get(index.getZeroBased());
        Assignment toAdd = createAssignment(toAddDescriptor);
        if (studentToAddAssignmentTo.hasAssignment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }
        if (toAdd.getName().length() > AssignmentName.MAXIMUM_NAME_LENGTH) {
            throw new CommandException(String.format(AssignmentName.MESSAGE_NAME_TOO_LONG,
                    AssignmentName.MAXIMUM_NAME_LENGTH));
        }
        Student editedStudent = studentToAddAssignmentTo.addAssignment(toAdd);
        model.setStudent(studentToAddAssignmentTo, editedStudent);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)),
                editedStudent, index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAssignmentCommand)) {
            return false;
        }

        AddAssignmentCommand otherAddAssignmentCommand = (AddAssignmentCommand) other;
        return index.equals(otherAddAssignmentCommand.index)
                && toAddDescriptor.equals(otherAddAssignmentCommand.toAddDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("toAddDescriptor", toAddDescriptor)
                .toString();
    }
    private static Assignment createAssignment(AssignmentDescriptor assignmentDescriptor) {
        assert assignmentDescriptor != null;
        return new Assignment(assignmentDescriptor.assignmentName, assignmentDescriptor.maxScore);
    }
    /**
     * Stores the details to create the Assignment. Each non-empty field value will be used to
     * create the Assignment.
     */
    public static class AssignmentDescriptor {
        private AssignmentName assignmentName;
        private int maxScore;

        /**
         * Constructor for AssignmentDescriptor
         */
        public AssignmentDescriptor(int maxScore, AssignmentName assignmentName) {
            this.maxScore = maxScore;
            this.assignmentName = assignmentName;
        }

        /**
         * Copy constructor.
         */
        public AssignmentDescriptor(AssignmentDescriptor toCopy) {
            setScore(toCopy.maxScore);
            setAssignmentName(toCopy.assignmentName);
        }

        public void setAssignmentName(AssignmentName assignmentName) {
            this.assignmentName = assignmentName;
        }

        public Optional<AssignmentName> getAssignmentName() {
            return Optional.ofNullable(assignmentName);
        }

        public void setScore(int score) {
            this.maxScore = score;
        }

        public Optional<Integer> getScore() {
            return Optional.ofNullable(maxScore);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            //instance of handles nulls
            if (!(other instanceof AssignmentDescriptor)) {
                return false;
            }
            AssignmentDescriptor otherAssignmentDescriptor = (AssignmentDescriptor) other;
            return Objects.equals(assignmentName, otherAssignmentDescriptor.assignmentName)
                    && Objects.equals(maxScore, otherAssignmentDescriptor.maxScore);
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
