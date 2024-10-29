package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NAME_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Changes the status of a task.
 */
public class MarkTaskCommand extends Command {

    public static final String COMMAND_WORD = "mark_t";
    public static final String COMMAND_WORD_ALIAS = "mt";
    public static final int LIST_GROUP_TASK_MARKER = 3;
    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Changes the status of a task.\n"
        + "Parameters: "
        + PREFIX_GROUP_NAME + "GROUP_NAME "
        + PREFIX_INDEX + "INDEX\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GROUP_NAME + "Team 5 "
        + PREFIX_INDEX + "2";

    public static final String MESSAGE_SUCCESS = "Changed the status of task: %1$s to %2$s";

    private final Index index;
    private final GroupName toMarkFrom;

    /**
     * Creates a MarkTaskCommand to change the status of the task on {@code index} from the
     * group with {@code groupName}.
     */
    public MarkTaskCommand(Index index, GroupName groupName) {
        requireNonNull(index);
        requireNonNull(groupName);
        this.index = index;
        toMarkFrom = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.containsGroupName(toMarkFrom)) {
            throw new CommandException(MESSAGE_GROUP_NAME_NOT_FOUND);
        }

        Group group = model.getGroupByName(toMarkFrom);
        List<Task> lastShownList = group.getTasks().stream().toList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_INDEX);
        }
        Task taskToMark = lastShownList.get(index.getZeroBased());
        Status changedStatus = (taskToMark.getStatus().equals(Status.PENDING)
                || taskToMark.getStatus().equals(Status.OVERDUE))
                ? Status.COMPLETED : Status.PENDING;
        Task editedTask = new Task(taskToMark.getTaskName(), taskToMark.getDeadline(), changedStatus,
            taskToMark.getGroupsWithTask());

        model.setTask(taskToMark, editedTask, group);
        model.setMostRecentGroupTaskDisplay(group.getGroupName().fullName);
        model.updateFilteredGroupList(x -> x.getGroupName().equals(group.getGroupName()));
        model.setStateGroupTask();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(taskToMark), changedStatus),
                LIST_GROUP_TASK_MARKER);
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

        if (!(other instanceof MarkTaskCommand)) {
            return false;
        }

        MarkTaskCommand otherMarkTaskCommand = (MarkTaskCommand) other;
        return index.equals(otherMarkTaskCommand.index)
            && toMarkFrom.equals(otherMarkTaskCommand.toMarkFrom);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("toMarkFrom", toMarkFrom)
            .toString();
    }

}
