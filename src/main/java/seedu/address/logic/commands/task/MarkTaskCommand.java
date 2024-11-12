package seedu.address.logic.commands.task;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Marks a Task in the address book.
 */
public class MarkTaskCommand extends Command {
    public static final String COMMAND_WORD = "mark-task";

    public static final String COMMAND_KEYWORD = "mtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks one or more tasks in the address book. \n"
            + "The index specified in the parameters must be a positive integer, "
            + "with a whitespace between any two indexes"
            + "Parameters: INDEX (must be a positive integer) [MORE INDEXES]\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + COMMAND_WORD + " 1 3 5\n";

    private final TaskStatusModifier taskStatusModifier;

    public MarkTaskCommand(Set<Index> targetIndexes) {
        this.taskStatusModifier = new TaskStatusModifier(targetIndexes, true);
    }

    public Set<Index> getTargetIndexes() {
        return taskStatusModifier.getTargetIndexes();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Set<Task> markedTasks = taskStatusModifier.modifyTasks(model);
        return new CommandResult(String.format(
                Messages.MESSAGE_MARK_TASK_SUCCESS, markedTasks
        ));
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
        return this.taskStatusModifier.getTargetIndexes()
                .equals(otherMarkTaskCommand.taskStatusModifier.getTargetIndexes());
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("taskStatusModifier", taskStatusModifier)
                .toString();
    }

}
