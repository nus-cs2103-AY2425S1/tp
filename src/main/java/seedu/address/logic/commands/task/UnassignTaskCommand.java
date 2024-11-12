package seedu.address.logic.commands.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Disassociates a Task associated with an existing person in the Wedlinker.
 */
public class UnassignTaskCommand extends Command {
    public static final String COMMAND_WORD = "unassign-task";

    public static final String COMMAND_KEYWORD = "unatask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes one or multiple tasks from the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + "TASK_INDEX_IN_PERSON... (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 1";

    private final Index index;
    private final Set<Index> taskIndexes;

    /**
     * Constructs an UnassignTaskCommand to remove weddings from a person.
     *
     * @param index The index of the person in the person list.
     * @param taskIndexes The indexes of tasks to be removed from a person.
     */
    public UnassignTaskCommand(Index index, Set<Index> taskIndexes) {
        this.index = index;
        this.taskIndexes = taskIndexes;
    }

    /**
     * Generates a command execution success message showing the removed tasks and the person.
     *
     * @param personToEdit The person to whom the tasks were removed.
     * @return A success message indicating the tasks that were removed and the name of the person.
     */
    private String generateSuccessMessage(Person personToEdit, Set<Task> tasksToRemove) {
        String removedTasks = tasksToRemove.stream()
                .map(task -> task.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        return String.format(
                Messages.MESSAGE_UNASSIGN_TASK_SUCCESS, removedTasks, personToEdit.getName().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    1, model.getFilteredPersonList().size()));
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Task> currentPersonTasks = new LinkedHashSet<>(personToEdit.getTasks());

        if (currentPersonTasks.isEmpty() || taskIndexes.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_TASK_NOT_FOUND_IN_CONTACT);
        }

        Set<Task> tasksToRemove = getTasksToRemove(currentPersonTasks);

        if (!currentPersonTasks.containsAll(tasksToRemove)) {
            throw new CommandException(Messages.MESSAGE_TASK_NOT_FOUND_IN_CONTACT);
        }

        currentPersonTasks.removeAll(tasksToRemove);

        Person editedPerson = PersonTaskEditorUtil.createEditedPersonWithUpdatedTasks(personToEdit, currentPersonTasks);

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(generateSuccessMessage(personToEdit, tasksToRemove));
    }

    private Set<Task> getTasksToRemove(Set<Task> currentPersonTasks) throws CommandException {
        Set<Task> tasksToRemove = new HashSet<>();
        List<Task> currentPersonTaskList = new ArrayList<>(currentPersonTasks);
        for (Index specifiedTaskIndex : taskIndexes) {
            if (specifiedTaskIndex.getZeroBased() >= currentPersonTasks.size()) {
                throw new CommandException(String.format(
                        Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, specifiedTaskIndex.getOneBased(),
                        1, currentPersonTasks.size()
                ));
            }
            int indexOfTask = specifiedTaskIndex.getZeroBased();
            Task taskToRemove = currentPersonTaskList.get(indexOfTask);
            tasksToRemove.add(taskToRemove);
        }
        return tasksToRemove;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnassignTaskCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index)
                && taskIndexes.equals(otherCommand.taskIndexes);
    }

}
