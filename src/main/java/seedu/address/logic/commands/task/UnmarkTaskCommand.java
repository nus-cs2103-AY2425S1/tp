package seedu.address.logic.commands.task;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Unmarks a Task in the address book.
 */
public class UnmarkTaskCommand extends Command {
    public static final String COMMAND_WORD = "unmark-task";

    public static final String COMMAND_KEYWORD = "untask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks one or more tasks in the address book. \n"
            + "The index specified in the parameters must be a positive integer, "
            + "with a whitespace between any two indexes"
            + "Parameters: INDEX (must be a positive integer) [MORE INDEXES]\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + COMMAND_WORD + " 1 3 5\n";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked task: %1$s";

    private final TaskStatusModifier taskStatusModifier;

    public UnmarkTaskCommand(Set<Index> targetIndexes) {
        this.taskStatusModifier = new TaskStatusModifier(targetIndexes, false);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Set<Task> unmarkedTasks = taskStatusModifier.modifyTasks(model);
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, unmarkedTasks));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkTaskCommand)) {
            return false;
        }

        UnmarkTaskCommand otherUnmarkTaskCommand = (UnmarkTaskCommand) other;
        return taskStatusModifier.equals(otherUnmarkTaskCommand.taskStatusModifier);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("taskStatusModifier", taskStatusModifier)
                .toString();
    }
}
