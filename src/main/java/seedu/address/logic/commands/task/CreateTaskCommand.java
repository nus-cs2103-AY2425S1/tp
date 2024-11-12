package seedu.address.logic.commands.task;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.HashSet;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds a Task to the address book.
 */
public class CreateTaskCommand extends Command {

    public static final String COMMAND_WORD = "create-task";

    public static final String COMMAND_KEYWORD = "ctask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates one or more tasks in the address book. \n"
            + "Parameters: "
            + PREFIX_TASK + "TASK_DESCRIPTION [DATE_FIELDS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "Setup venue decorations\n"
            + COMMAND_WORD + " "
            + PREFIX_TASK + "Submit proposal d/2024-10-31\n"
            + COMMAND_WORD + " "
            + PREFIX_TASK + "Buy limited time items d/2024-10-31 d/2024-12-30\n"
            + COMMAND_WORD + " "
            + PREFIX_TASK + "Secure venue d/2024-10-31 "
            + PREFIX_TASK + "Inform groom of itinerary";

    private final HashSet<Task> tasksToAdd;

    /**
     * Creates a CreateTaskCommand to add the specified {@code task} to the Wedlinker
     * @param task The {@code task} to be added to the Wedlinker
     */
    public CreateTaskCommand(HashSet<Task> task) {
        requireNonNull(task);
        tasksToAdd = task;
    }

    /**
     * Returns the set of tasks to be added.
     */
    public HashSet<Task> getTaskToAdd() {
        return tasksToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        for (Task task : tasksToAdd) {
            if (model.hasTask(task)) {
                throw new CommandException(Messages.MESSAGE_DUPLICATE_TASK_IN_WEDLINKER);
            }
            model.addTask(task);
        }

        return new CommandResult(String.format(
                Messages.MESSAGE_TASK_ADDED_SUCCESS, StringUtil.tasksString(tasksToAdd)
        ));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // null case handled by instanceof
        if (!(obj instanceof CreateTaskCommand otherCreateTaskCommand)) {
            return false;
        }

        return tasksToAdd.equals(otherCreateTaskCommand.tasksToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("taskToAdd", tasksToAdd)
                .toString();
    }
}
