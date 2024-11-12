package seedu.address.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_STUDENT_MARKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Deletes a student from the address book.
 */
public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "del_s";
    public static final String COMMAND_WORD_ALIAS = "ds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Deletes the student identified by the student number used.\n"
        + "Parameters: "
        + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_STUDENT_NUMBER + "A02345678J";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student: %1$s";

    private final StudentNumber targetStudentNo;

    /**
     * Creates a DeleteStudentCommand to remove the specified student with {@code sno}.
     */
    public DeleteStudentCommand(StudentNumber sno) {
        this.targetStudentNo = sno;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getAddressBook().getStudentList();

        boolean hasFoundTarget = false;
        Student studentToBeDeleted = null;
        for (Student stu : lastShownList) {
            if (stu.getStudentNumber().equals(targetStudentNo)) {
                hasFoundTarget = true;
                studentToBeDeleted = stu;
                break;
            }
        }

        if (!hasFoundTarget) {
            throw new CommandException(Messages.MESSAGE_STUDENT_NO_NOT_FOUND);
        }

        if (studentToBeDeleted.getGroupName().isPresent()) {
            model.deleteStudentFromGroup(model.getGroupByName(studentToBeDeleted.getGroupName().get()),
                studentToBeDeleted);
        }
        model.deletePerson(studentToBeDeleted);
        model.setStateStudents();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(studentToBeDeleted)),
            LIST_STUDENT_MARKER);
    }

    public StudentNumber getTargetStudentNo() {
        return targetStudentNo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteStudentCommand = (DeleteStudentCommand) other;
        return targetStudentNo.equals(otherDeleteStudentCommand.targetStudentNo);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetStudentNumber", targetStudentNo)
            .toString();
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }
}
