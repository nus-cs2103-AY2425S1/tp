package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_GROUP_MARKER;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NAME_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_STUDENT_NO_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.ExceedGroupSizeException;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.exceptions.PersonNotFoundException;

/**
 * Adds a student to a group.
 */
public class AddStudentToGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_s_g";
    public static final String COMMAND_WORD_ALIAS = "asg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds a student to a group identified by the group name used.\n"
        + "Parameters: "
        + "[" + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER]... "
        + PREFIX_GROUP_NAME + "GROUP_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_STUDENT_NUMBER + "A02345678J "
        + PREFIX_STUDENT_NUMBER + "A0456789K "
        + PREFIX_GROUP_NAME + "CS2103T-T14-1";

    public static final String MESSAGE_SUCCESS = "Added the following students to %1$s:\n%2$s";

    public static final String MESSAGE_DUPLICATE_STUDENT_IN_GROUP = "A student already belongs to a group!";
    public static final String MESSAGE_EXCEED_GROUP_SIZE = "Group exceeded the maximum size!";

    private final Set<StudentNumber> toAdd;

    private final GroupName toAddInto;

    /**
     * Creates an AddStudentToGroupCommand to add the specified {@code Student} to the specified {@code Group}.
     */
    public AddStudentToGroupCommand(Set<StudentNumber> studentNumbers, GroupName groupName) {
        requireNonNull(studentNumbers);
        requireNonNull(groupName);
        toAdd = studentNumbers;
        toAddInto = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group group = model.getGroupByName(toAddInto);
        if (group == null) {
            throw new CommandException(MESSAGE_GROUP_NAME_NOT_FOUND);
        }

        List<StudentNumber> students = toAdd.stream().toList();
        StringBuilder studentsAdded = new StringBuilder();

        try {
            for (StudentNumber studentNumber : students) {
                Student student = model.getPersonByNumber(studentNumber);
                if (student.getGroupName().isPresent()) {
                    throw new CommandException(MESSAGE_DUPLICATE_STUDENT_IN_GROUP);
                }
                model.addPersonToGroup(student, group);
                studentsAdded.append(studentNumber).append("\n");
            }
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_STUDENT_NO_NOT_FOUND);
        } catch (ExceedGroupSizeException e) {
            throw new CommandException(MESSAGE_EXCEED_GROUP_SIZE);
        }

        model.updateFilteredGroupList(x -> x.getGroupName().equals(group.getGroupName()));
        model.setStateGroups();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAddInto), studentsAdded),
            LIST_GROUP_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddStudentToGroupCommand)) {
            return false;
        }

        AddStudentToGroupCommand otherAddStudentToGroupCommand = (AddStudentToGroupCommand) other;
        return toAdd.equals(otherAddStudentToGroupCommand.toAdd)
            && toAddInto.equals(otherAddStudentToGroupCommand.toAddInto);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .add("toAddInto", toAddInto)
            .toString();
    }
}
