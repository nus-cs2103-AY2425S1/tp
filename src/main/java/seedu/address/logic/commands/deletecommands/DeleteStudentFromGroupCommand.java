package seedu.address.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_GROUP_MARKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Deletes a student from a group.
 */
public class DeleteStudentFromGroupCommand extends Command {
    public static final String COMMAND_WORD = "del_s_g";
    public static final String COMMAND_WORD_ALIAS = "dsg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Deletes the student identified by the student number used from its assigned group.\n"
        + "Parameters: "
        + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_STUDENT_NUMBER + "A02345678J";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student: %1$s from %2$s";
    public static final String MESSAGE_STUDENT_NOT_IN_GROUP = "The student doesn't belong to any group";

    private final StudentNumber targetStudentNo;

    /**
     * Creates an DeleteStudentFromGroupCommand to remove the student with {@code sno}
     * from group with {@code groupName}.
     */
    public DeleteStudentFromGroupCommand(StudentNumber sno) {
        this.targetStudentNo = sno;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredPersonList();

        boolean hasFoundTarget = false;
        Student studentToBeDeleted = null;
        Group group;
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
            group = model.getGroupByName(studentToBeDeleted.getGroupName().get());
        } else {
            throw new CommandException(MESSAGE_STUDENT_NOT_IN_GROUP);
        }
        model.deleteStudentFromGroup(group, studentToBeDeleted);

        model.updateFilteredGroupList(x -> x.getGroupName().equals(group.getGroupName()));
        model.setStateGroups();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
            Messages.format(targetStudentNo), Messages.format(group.getGroupName())), LIST_GROUP_MARKER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteStudentFromGroupCommand)) {
            return false;
        }

        DeleteStudentFromGroupCommand otherDeleteStudentCommand = (DeleteStudentFromGroupCommand) other;
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
