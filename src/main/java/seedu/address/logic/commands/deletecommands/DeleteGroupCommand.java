package seedu.address.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_GROUP_MARKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;

/**
 * Deletes a group from the address book and removes students in the group.
 */
public class DeleteGroupCommand extends Command {
    public static final String COMMAND_WORD = "del_g";
    public static final String COMMAND_WORD_ALIAS = "dg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Deletes the group identified by the group name used.\n"
        + "Parameters: "
        + PREFIX_GROUP_NAME + "GROUP_NAME\n"
        + "Example: "
        + COMMAND_WORD + " "
        + PREFIX_GROUP_NAME + "CS2103T-T14-1";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s\nStudents affected: %2$s";

    private final GroupName targetGroupName;

    /**
     * Creates a DeleteGroupCommand to remove the specified group with {@code targetGroupName}.
     */
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
                // have to do here else there will be an error
                model.setPerson(student, student.removeGroup());
                studentsAffected++;
            }
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }
        groupToBeDeleted.getTasks().forEach(x -> model.decreaseGroupWithTask(x));
        model.deleteGroup(groupToBeDeleted);
        model.setStateGroups();
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, Messages.format(groupToBeDeleted),
            studentsAffected), LIST_GROUP_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }
}
