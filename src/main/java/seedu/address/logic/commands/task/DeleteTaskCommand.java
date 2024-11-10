package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Deletes a task identified using it's displayed index from the address book.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "delete-task";

    public static final String COMMAND_KEYWORD = "dtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX,
                    targetIndex.getOneBased(), 1, lastShownList.size()));
        }
        Task taskToDelete = lastShownList.get(targetIndex.getZeroBased());

        boolean anyChanges = false;
        for (Person person : model.getFilteredPersonList()) {
            if (person.hasTask(taskToDelete)) {
                Set<Task> updatedTasks = new HashSet<>(person.getTasks());
                updatedTasks.remove(taskToDelete);
                Person editedPerson = PersonTaskEditorUtil.createEditedPersonWithUpdatedTasks(person, updatedTasks);
                model.setPerson(person, editedPerson);
                anyChanges = true;
            }
        }

        model.deleteTask(taskToDelete);
        if (anyChanges) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }

        return new CommandResult(String.format(
                Messages.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.toString()
        ));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        DeleteTaskCommand otherIndex = (DeleteTaskCommand) other;
        return targetIndex.equals(otherIndex.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
