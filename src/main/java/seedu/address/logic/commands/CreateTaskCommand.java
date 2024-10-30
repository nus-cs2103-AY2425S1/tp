package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.HashSet;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds a Task to the address book.
 */
public class CreateTaskCommand extends Command {

    public static final String COMMAND_WORD = "create-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a task in the address book. \n"
            + "Parameters: "
            + PREFIX_TASK + "TASK_TYPE TASK_DESCRIPTION [ADDITIONAL_FIELDS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "todo Setup venue decorations\n"
            + COMMAND_WORD + " "
            + PREFIX_TASK + "deadline Submit proposal /by 2024-10-31";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

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
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            model.addTask(task);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, tasksToAdd));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // null case handled by instanceof
        if (!(obj instanceof CreateTaskCommand)) {
            return false;
        }

        CreateTaskCommand otherCreateTaskCommand = (CreateTaskCommand) obj;
        return tasksToAdd.equals(otherCreateTaskCommand.tasksToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("taskToAdd", tasksToAdd)
                .toString();
    }
}
