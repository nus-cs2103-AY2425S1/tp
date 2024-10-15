package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;

/**
 * Deletes a group from the address book and removes students in the group.
 */
public class DeleteGroupCommand extends Command {
    public static final String COMMAND_WORD = "delete_group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the group identified by the group name used.\n"
        + "Parameters: GROUP_NAME\n"
        + "Example: " + COMMAND_WORD + " " + "CS2103";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s\nStudents affected: %2$s";

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

        // remove students from group
        int studentsAffected = 0;
        if (groupToBeDeleted.hasStudents()) {
            for (Student student : groupToBeDeleted.getStudents()) {
                // update group in student
                model.setPerson(student, student.removeGroup());
                studentsAffected++;
            }
        }
        model.deleteGroup(groupToBeDeleted);
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, Messages.format(groupToBeDeleted),
            studentsAffected));
    }
}
