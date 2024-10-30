package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NAME_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_STUDENT_NO_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.exceptions.PersonNotFoundException;

/**
 * Adds a student to a group.
 */
public class AddStudentToGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_s_g";
    public static final String COMMAND_WORD_ALIAS = "asg";
    public static final int LIST_GROUP_MARKER = 1;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds a student to a group. \n"
        + "Parameters: "
        + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER "
        + PREFIX_GROUP_NAME + "GROUP_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_STUDENT_NUMBER + "A02345678J "
        + PREFIX_GROUP_NAME + "Group 1";

    public static final String MESSAGE_SUCCESS = "Added student: %1$s to %2$s";

    public static final String MESSAGE_DUPLICATE_STUDENT_IN_GROUP = "This student already belongs to a group!";

    private final StudentNumber toAdd;

    private final GroupName toAddInto;

    /**
     * Creates an AddStudentToGroupCommand to add the specified {@code Student} to the specified {@code Group}.
     */
    public AddStudentToGroupCommand(StudentNumber studentNumber, GroupName groupName) {
        requireNonNull(studentNumber);
        requireNonNull(groupName);
        toAdd = studentNumber;
        toAddInto = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Student student;
        Group group;
        group = model.getGroupByName(toAddInto);
        if (group == null) {
            throw new CommandException(MESSAGE_GROUP_NAME_NOT_FOUND);
        }
        try {
            student = model.getPersonByNumber(toAdd);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_STUDENT_NO_NOT_FOUND);
        }

        if (student.getGroupName().isPresent()) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT_IN_GROUP);
        }

        model.addPersonToGroup(student, group);
        model.updateFilteredGroupList(x -> x.getGroupName().equals(group.getGroupName()));
        model.setStateGroups();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd), Messages.format(toAddInto)),
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
