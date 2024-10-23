package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskList;
import seedu.address.ui.Ui.UiState;

/**
 * Deletes a Task  from a Person identified using it's displayed index from the address book.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "deletetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the student and index number used in the displayed task list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME (must be exact name of an existing student) "
            + PREFIX_TASK_INDEX + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TASK_INDEX + "1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s for %2$s by %3$s";

    private final Name targetName;
    private final Index targetIndex;

    /**
     * Creates an DeleteTaskCommand to delete a task from specified User at the specified Displayed Index{@code Person}
     */
    public DeleteTaskCommand(Name name, Index targetIndex) {
        this.targetName = name;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(targetName);

        // Get the person we are looking for
        ObservableList<Person> personList = model.getAddressBook().getPersonList();
        Optional<Person> optionalPerson = personList.stream().filter(x -> x.getName().equals(targetName)).findFirst();
        if (optionalPerson.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
        }
        Person targetPerson = optionalPerson.get();

        // Get the Task to be deleted
        TaskList taskList = targetPerson.getTaskList();
        if (targetIndex.getZeroBased() >= taskList.asUnmodifiableObservableList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToDelete = taskList.get(targetIndex.getZeroBased());

        taskList.remove(taskToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS,
                taskToDelete.getTaskDescription(),
                targetPerson.getName(),
                taskToDelete.getTaskDeadline()),
                UiState.DETAILS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTaskCommand)) {
            return false;
        }

        DeleteTaskCommand otherDeleteTaskCommand = (DeleteTaskCommand) other;
        boolean isSameTargetIndex = targetIndex.equals(otherDeleteTaskCommand.targetIndex);
        boolean isSameTargetPerson = targetName.equals(otherDeleteTaskCommand.targetName);
        return isSameTargetIndex && isSameTargetPerson;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

