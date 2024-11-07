package seedu.address.logic.commands.task;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;


/**
 * Utility class to modify the status of tasks in the address book.
 * This class can mark or unmark tasks and update each relevant person
 * if they have the specified task.
 */
public class TaskStatusModifier {

    private final Set<Index> targetIndexes;
    private final boolean markAsDone;


    /**
     * Constructs a {@code TaskStatusModifier} with the specified target indexes and status flag.
     *
     * @param targetIndexes The indexes of the tasks to be modified.
     * @param markAsDone A flag indicating whether tasks should be marked as done (true) or undone (false).
     */
    public TaskStatusModifier(Set<Index> targetIndexes, boolean markAsDone) {
        this.targetIndexes = targetIndexes;
        this.markAsDone = markAsDone;
    }

    public Set<Index> getTargetIndexes() {
        return targetIndexes;
    }

    /**
     * Executes the task modification by marking or unmarking tasks based on the {@code markAsDone} flag.
     * It iterates over each target index, modifies the task's status, updates persons if necessary,
     * and updates the task's status in the model.
     *
     * @param model The model containing the task and person lists.
     * @return A set of modified tasks that were marked or unmarked.
     * @throws CommandException if any of the provided indexes are invalid.
     */
    public Set<Task> modifyTasks(Model model) throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();
        List<Person> personList = model.getFilteredPersonList();
        Set<Task> modifiedTasks = new HashSet<>();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX,
                        targetIndex.getOneBased(),
                        1, lastShownList.size()));
            }
            Task taskToModify = lastShownList.get(targetIndex.getZeroBased());
            if (markAsDone) {
                taskToModify.markAsDone();
            } else {
                taskToModify.markAsUndone();
            }
            modifiedTasks.add(taskToModify);
            updatePersonsWithTask(model, personList, taskToModify);

            if (markAsDone) {
                model.markTask(taskToModify);
            } else {
                model.unmarkTask(taskToModify);
            }
        }

        return modifiedTasks;
    }

    /**
     * Updates each person in the model if they have the modified task by creating a new
     * person with an updated task set that includes the modified task.
     *
     * @param model The model containing the person list.
     * @param personList The list of persons to check and update if they have the modified task.
     * @param taskToModify The task that was modified.
     */
    private void updatePersonsWithTask(Model model, List<Person> personList, Task taskToModify) {
        for (Person person : personList) {
            if (person.hasTask(taskToModify)) {
                Set<Task> updatedPersonTasks = createUpdatedTaskSet(person, taskToModify);
                Person editedPerson = PersonTaskEditorUtil.createEditedPersonWithUpdatedTasks(person,
                        updatedPersonTasks);
                model.setPerson(person, editedPerson);
            }
        }
    }

    /**
     * Creates an updated set of tasks for a person, replacing the modified task if it exists
     * in the person's task list.
     *
     * @param person The person whose tasks are being updated.
     * @param taskToModify The task that has been modified and should replace the original task if found.
     * @return A set of tasks for the person, with the modified task replacing the original if necessary.
     */
    private Set<Task> createUpdatedTaskSet(Person person, Task taskToModify) {
        Set<Task> updatedTasks = new HashSet<>();
        for (Task personTask : person.getTasks()) {
            Task updatedTask = personTask.isSameTask(taskToModify) ? taskToModify : personTask;
            updatedTasks.add(updatedTask);
        }
        return updatedTasks;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TaskStatusModifier otherModifier)) {
            return false;
        }
        return markAsDone == otherModifier.markAsDone && targetIndexes.equals(otherModifier.targetIndexes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndexes, markAsDone);
    }
}
