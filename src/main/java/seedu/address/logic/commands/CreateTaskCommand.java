package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.HashSet;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Event;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;

/**
 * Adds a Task to the address book.
 */
public class CreateTaskCommand extends Command {

    public static final String COMMAND_WORD = "create-task";

    public static final String COMMAND_KEYWORD = "ctask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a task in the address book. \n"
            + "Parameters: "
            + PREFIX_TASK + "TASK_TYPE TASK_DESCRIPTION [ADDITIONAL_FIELDS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "todo Setup venue decorations\n"
            + COMMAND_WORD + " "
            + PREFIX_TASK + "deadline Submit proposal /by 2024-10-31";

    public static final String MESSAGE_SUCCESS = "New task(s) added: %1$s";
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
        StringBuilder sb = new StringBuilder();
        for (Task task : tasksToAdd) {
            if (model.hasTask(task)) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            model.addTask(task);

            if (task instanceof Todo) {
                sb.append("\nTodo: ").append(task.getDescription());
            } else if (task instanceof Deadline deadline) {
                // Cast to Deadline to access its specific methods
                sb.append("\nDeadline: ").append(deadline.getDescription())
                        .append(" by ").append(deadline.getBy());
            } else if (task instanceof Event event) {
                // Cast to Event to access its specific methods
                sb.append("\nEvent: ").append(event.getDescription())
                        .append(" from ").append(event.getFrom())
                        .append(" to ").append(event.getTo());
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, sb));
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
