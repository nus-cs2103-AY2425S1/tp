package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Remark;
import seedu.address.model.student.Student;

/**
 * Adds a remark to an existing student.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to an existing student "
            + "identified by the index number used in the displayed student list. "
            + "\nParameters: "
            + PREFIX_STUDENT_INDEX + "INDEX "
            + PREFIX_REMARK + "REMARK "
            + "\nExample: " + COMMAND_WORD + " " + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_REMARK + "Weak in Math";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Student: %1$s";

    private final Index studentIndex;
    private final Remark remark;

    /**
     * @param studentIndex of the student in the filtered student list
     * @param remark to add to the student
     */
    public RemarkCommand(Index studentIndex, Remark remark) {
        assert studentIndex != null;
        assert remark != null;

        requireNonNull(studentIndex);
        requireNonNull(remark);

        this.studentIndex = studentIndex;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToAddRemark = lastShownList.get(studentIndex.getZeroBased());
        Student updatedStudent = new Student(studentToAddRemark, remark);

        model.setStudent(studentToAddRemark, updatedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_ADD_REMARK_SUCCESS, Messages.format(updatedStudent)),
                updatedStudent, studentIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand otherRemarkCommand = (RemarkCommand) other;
        return studentIndex.equals(otherRemarkCommand.studentIndex)
                && remark.equals(otherRemarkCommand.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("remark", remark)
                .toString();
    }
}
