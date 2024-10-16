package seedu.address.model.person.task;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.TaskNotFoundException;
import seedu.address.storage.JsonAdaptedTask;

/**
 * Represents a Person's task list in the address book.
 * Guarantees: is always valid
 */
public class TaskList implements Iterable<Task> {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    private final ObservableList<Task> unmodifiableTasks =
            FXCollections.unmodifiableObservableList(tasks);

    /**
     * Sorts task by deadline, earlier tasks are placed at the front
     */
    public void sortByDeadline() {
        tasks.sort(Task::compareTo);
    }

    /**
     * Returns true if there are no tasks in the task list.
     *
     * @return true if the task list contains no tasks, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Adds a task to the list and sorts it by date.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        tasks.add(toAdd);
        sortByDeadline();
    }

    /**
     * Removes the equivalent task from the list.
     */
    public void remove(Task toRemove) {
        requireNonNull(toRemove);
        if (!tasks.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Returns the size of the task list.
     */
    public int size() {
        return this.tasks.size();
    }

    public void setTasks(TaskList replacement) {
        requireNonNull(replacement);
        tasks.setAll(replacement.tasks);
    }

    /**
     * Replaces the contents of this list with {@code replacementTasks}.
     */
    public void setTasks(List<Task> replacementTasks) {
        requireNonNull(replacementTasks);
        tasks.setAll(replacementTasks);
    }

    /**
     * Checks if the task list contains the specified task.
     *
     * @param toCheck The task to check for.
     * @return true if the task is in the list, false otherwise.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return tasks.contains(toCheck);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return unmodifiableTasks;
    }

    /**
     * Converts task list into something Jackson can use.
     */
    public List<JsonAdaptedTask> getjsonAdaptedTaskList() {
        return tasks.stream().map(JsonAdaptedTask::new).toList();
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskList)) {
            return false;
        }

        TaskList otherTaskList = (TaskList) other;
        return tasks.equals(otherTaskList.tasks);
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }

    @Override
    public String toString() {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < this.tasks.size(); i++) {
            result.add(String.valueOf(i + 1) + ". " + tasks.get(i).toString());
        }

        return String.join("\n", result);
    }

    /**
     * Returns description of tasks to be displayed to user.
     */
    public String toDescription() {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < this.tasks.size(); i++) {
            result.add(String.valueOf(i + 1) + ". " + tasks.get(i).toDescription());
        }

        return String.join("\n", result);
    }

    /**
     * Creates and returns a copy of the current TaskList.
     * This performs a deep copy of the tasks.
     *
     * @return a new TaskList that is a copy of the current one.
     */
    public TaskList copy() {
        TaskList copiedTaskList = new TaskList();
        for (Task task : tasks) {
            copiedTaskList.add(new Task(task.getTaskDescription(), task.getTaskDeadline()));
        }
        return copiedTaskList;
    }
}
