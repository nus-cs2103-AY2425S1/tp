package seedu.address.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_TASK_MARKER;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.task.Task;

/**
 * Deletes a task from all groups having the task.
 */
public class DeleteTaskForAllGroupsCommand extends Command {
    public static final String COMMAND_WORD = "del_t";
    public static final String COMMAND_WORD_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Deletes a task from all groups based on the index when listing all tasks.\n"
        + "Parameters: "
        + PREFIX_INDEX + "INDEX\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_INDEX + "1";

    public static final String MESSAGE_SUCCESS = "Deleted task: %1$s from:\n %2$s";

    private final Index taskIndex;

    /**
     * Creates an DeleteTaskForAllGroupCommand to delete the task on {@code index} from all
     * groups
     */
    public DeleteTaskForAllGroupsCommand(Index index) {
        requireNonNull(index);
        this.taskIndex = index;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = new ArrayList<>(model.getFilteredTaskList());
        List<Group> groupList = new ArrayList<>(model.getFilteredGroupList());
        if (taskIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_INDEX);
        }
        Task targetTask = lastShownList.get(taskIndex.getZeroBased());
        List<Group> toBeDeletedFrom = getGroupsToBeDeletedFrom(groupList, targetTask);
        updateTaskInGroup(toBeDeletedFrom, targetTask, model);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        String formattedGroups = toBeDeletedFrom.stream()
            .map(group -> Messages.format(group))
            .collect(Collectors.joining("\n"));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(targetTask), formattedGroups),
            LIST_TASK_MARKER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTaskForAllGroupsCommand)) {
            return false;
        }

        DeleteTaskForAllGroupsCommand otherDeleteTaskForAllGroupsCommand = (DeleteTaskForAllGroupsCommand) other;
        return taskIndex.equals(otherDeleteTaskForAllGroupsCommand.taskIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("taskIndex", taskIndex)
            .toString();
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    private List<Group> getGroupsToBeDeletedFrom(List<Group> groups, Task targetTask) {
        List<Group> toBeDeletedFrom = new ArrayList<Group>();
        for (Group group : groups) {
            if (group.hasTask(targetTask)) {
                toBeDeletedFrom.add(group);
            }
        }
        return toBeDeletedFrom;
    }
    private void updateTaskInGroup(List<Group> groups, Task task, Model model) {
        for (Group group : groups) {
            model.deleteTaskFromGroup(task, group);
            model.decreaseGroupWithTask(task);
            model.setMostRecentGroupTaskDisplay(group.getGroupName().getGroupName());
            model.updateFilteredGroupList(x -> x.getGroupName().equals(group.getGroupName()));
            model.setStateGroupTask();
        }
    }
}
