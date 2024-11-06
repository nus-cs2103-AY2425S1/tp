package seedu.address.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_GROUP_TASK_MARKER;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Task;

/**
 * Deletes a task from a group.
 */
public class DeleteTaskFromGroupCommand extends Command {

    public static final String COMMAND_WORD = "del_t_g";
    public static final String COMMAND_WORD_ALIAS = "dtg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Deletes a task from a group based on the index when listing all tasks in that group.\n"
        + "Parameters: "
        + PREFIX_GROUP_NAME + "GROUP_NAME "
        + PREFIX_INDEX + "INDEX\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GROUP_NAME + "CS2103T-T14-1 "
        + PREFIX_INDEX + "1";

    public static final String MESSAGE_SUCCESS = "Deleted task: %1$s from %2$s";

    private final Index index;
    private final GroupName toDeleteFrom;

    /**
     * Creates an DeleteTaskFromGroupCommand to delete the task on {@code index} from the
     * group with {@code groupName}.
     */
    public DeleteTaskFromGroupCommand(Index index, GroupName groupName) {
        requireNonNull(index);
        requireNonNull(groupName);
        this.index = index;
        toDeleteFrom = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group group = model.getGroupByName(toDeleteFrom);
        List<Task> lastShownList = group.getTasks().stream().toList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_INDEX);
        }
        Task task = lastShownList.get(index.getZeroBased());

        model.deleteTaskFromGroup(task, group);
        model.decreaseGroupWithTask(task);
        model.setMostRecentGroupTaskDisplay(group.getGroupName().getGroupName());
        model.updateFilteredGroupList(x -> x.getGroupName().equals(group.getGroupName()));
        model.setStateGroupTask();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(task), Messages.format(group)),
            LIST_GROUP_TASK_MARKER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTaskFromGroupCommand)) {
            return false;
        }

        DeleteTaskFromGroupCommand otherDeleteTaskFromGroupCommand = (DeleteTaskFromGroupCommand) other;
        return index.equals(otherDeleteTaskFromGroupCommand.index)
            && toDeleteFrom.equals(otherDeleteTaskFromGroupCommand.toDeleteFrom);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("toDeleteFrom", toDeleteFrom)
            .toString();
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }
}
