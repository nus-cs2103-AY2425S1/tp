package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Adds a task to the task list.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: INDEX d/DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " 1 d/Buy medication";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final String taskDescription;
    private final Index target;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task} and associate it with {@code Person}.
     */
    public AddTaskCommand(Index target, String taskDescription) {
        requireNonNull(taskDescription);
        requireNonNull(target);
        this.taskDescription = taskDescription;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (target.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person patient = model.getFilteredPersonList().get(target.getZeroBased());

        Task taskToAdd = new Task(patient, taskDescription);
        if (model.hasTask(taskToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(taskToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToAdd.getDescription()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTaskCommand)) {
            return false;
        }

        AddTaskCommand otherAddTaskCommand = (AddTaskCommand) other;
        return taskDescription.equals(otherAddTaskCommand.taskDescription)
                && target.equals(otherAddTaskCommand.target);
    }
}
