package seedu.address.testutil;

import java.util.Arrays;

import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskList;

public class TaskListBuilder {
    public TaskList taskList;

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

    public void withTasks(Task ... tasks) {
        taskList.setTasks(Arrays.stream(tasks).toList());
    }

    public TaskList build() {
        TaskList result = new TaskList();
        Iterable<Task> tasks = taskList;
        tasks.forEach(task ->result.add(new TaskBuilder(task).build()));
        return result;
    }
}

