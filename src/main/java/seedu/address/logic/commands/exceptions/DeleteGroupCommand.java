package seedu.address.logic.commands.exceptions;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

public class DeleteGroupCommand extends Command {
    public static final String COMMAND_WORD = "delete_group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the group identified by the group name used.\n"
        + "Parameters: GROUP_NAME\n"
        + "Example: " + COMMAND_WORD + PREFIX_GROUP_NAME + " " + "CS2103";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";
    public static final String MESSAGE_STUDENTS_AFFECTED = "Number of students affected: %1$s";

    private final GroupName targetGroupName;

    public DeleteGroupCommand(GroupName targetGroupName) {
        this.targetGroupName = targetGroupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();
        // find group
        boolean hasFoundTarget = false;
        Group groupToBeDeleted = null;
        for (Group group : lastShownList) {
            if (group.getGroupName().equals(targetGroupName)) {
                hasFoundTarget = true;
                groupToBeDeleted = group;
                break;
            }
        }
        if (!hasFoundTarget) {
            throw new CommandException(Messages.MESSAGE_GROUP_NAME_NOT_FOUND);
        }
        int studentsAffected = 0;
        // loop through group to update students' group name to null
        if (groupToBeDeleted.hasStudents()) {
            for (Student student : groupToBeDeleted.getStudents()) {
                // update group in student
                model.setPerson(student, new Student(student.getName(), student.getEmail(), student.getTags(),
                    student.getStudentNumber()));
                studentsAffected++;
            }
        }
        // delete group from UniqueGroupList
        model.deleteGroup(groupToBeDeleted);
        // TODO: can add number of students affected by this command
        String result = String.format(MESSAGE_DELETE_GROUP_SUCCESS, Messages.format(groupToBeDeleted));
        result += "\n" + String.format(MESSAGE_STUDENTS_AFFECTED, studentsAffected);

        return new CommandResult(result);
    }

    public GroupName getTargetGroupName() {
        return targetGroupName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGroupCommand otherDeleteGroupCommand)) {
            return false;
        }
        return targetGroupName.equals(otherDeleteGroupCommand.targetGroupName);
    }
}
