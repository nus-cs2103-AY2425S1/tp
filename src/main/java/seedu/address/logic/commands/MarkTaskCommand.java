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
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Changes the status of a task.
 */
public class MarkTaskCommand extends Command {

    public static final String COMMAND_WORD = "mark_task";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the status of a task. "
            + "Parameters: "
            + PREFIX_GROUP_NAME + "GROUP_NAME "
            + PREFIX_INDEX + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP_NAME + "Team 5 "
            + PREFIX_INDEX + "2";

    public static final String MESSAGE_SUCCESS = "Changed the status of task: %1$s";
    public static final String GROUP_NOT_FOUND = "Group not found";

    private final Index index;
    private final GroupName toMarkFrom;

    /**
     * Creates a MarkTaskCommand to change the status of the specified task on {@code index} from the
     * specified {@code groupName}.
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
            throw new CommandException(GROUP_NOT_FOUND);
        }

        List<Task> lastShownList = model.getFilteredTaskList();

        // the index here refers to the index in the list of all tasks from all groups
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Task taskToMark = lastShownList.get(index.getZeroBased());
        Status changedStatus = taskToMark.getStatus().equals(Status.PENDING) ? Status.COMPLETED : Status.PENDING;
        Task markedTask = new Task(taskToMark.getTaskName(), taskToMark.getDeadline(), changedStatus);

        model.setTask(taskToMark, markedTask);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(markedTask)));
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
