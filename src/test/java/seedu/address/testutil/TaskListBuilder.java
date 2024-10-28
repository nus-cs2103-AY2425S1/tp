package seedu.address.testutil;

import java.util.Arrays;

import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskList;

/**
 * A utility class to help with building TaskList objects.
 */
public class TaskListBuilder {
    private final TaskList taskList;

    /**
     * Creates a {@code TaskListBuilder} with no tasks as default
     */
    public TaskListBuilder() {
        this.taskList = new TaskList();
    }

    /**
     * Creates a {@code TaskListBuilder} with given tasks
     */
    public TaskListBuilder(TaskList taskListToCopy) {
        this.taskList = new TaskList();
        this.taskList.setTasks(taskListToCopy);
    }


    /**
     * Sets the {@code Tasks} of the {@code TaskList} that we are building.
     */
    public void withTasks(Task ... tasks) {
        taskList.setTasks(Arrays.stream(tasks).toList());
    }

    /**
     * Creates a new deep copy of TaskList
     * @return new deep copy of {@code TaskList}
     */
    public TaskList build() {
        TaskList result = new TaskList();
        Iterable<Task> tasks = taskList;
        tasks.forEach(task ->result.add(new TaskBuilder(task).build()));
        return result;
    }
}
