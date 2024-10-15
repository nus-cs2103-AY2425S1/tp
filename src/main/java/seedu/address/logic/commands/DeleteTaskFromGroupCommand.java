package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Task;

/**
 * Deletes a task from a group.
 */
public class DeleteTaskFromGroupCommand extends Command {

    public static final String COMMAND_WORD = "delete_task";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a task from a group. "
            + "Parameters: "
            + PREFIX_GROUP_NAME + "TASK_NAME "
            + PREFIX_INDEX + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP_NAME + "Team 5 "
            + PREFIX_INDEX + "2";

    public static final String MESSAGE_SUCCESS = "Deleted task: %1$s from %2$s";
    public static final String MESSAGE_TASK_NOT_IN_GROUP = "This task is not in the group";

    private final Index index;
    private final GroupName toDeleteFrom;

    /**
     * Creates an DeleteTaskToGroupCommand to delete the specified task on {@code index} from the
     * specified {@code groupName}.
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

        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task task = lastShownList.get(index.getZeroBased());
        Group group = model.getGroupByName(toDeleteFrom);

        if (!model.hasTaskInGroup(task, group)) {
            throw new CommandException(MESSAGE_TASK_NOT_IN_GROUP);
        }

        model.deleteTaskFromGroup(task, group);
        model.deleteTask(task);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(task), Messages.format(group)));
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
}
