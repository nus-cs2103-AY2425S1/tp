package seedu.address.testutil;

import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskDeadline;
import seedu.address.model.student.task.TaskDescription;

/**
 * A utility class to help with building Task objects
 */
public class TaskBuilder {
    public static final String DEFAULT_TASK_DESCRIPTION = "Mark homework";
    public static final String DEFAULT_TASK_DEADLINE = "2024-01-01";

    private TaskDescription taskDescription;
    private TaskDeadline taskDeadline;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        taskDescription = new TaskDescription(DEFAULT_TASK_DESCRIPTION);
        taskDeadline = new TaskDeadline(DEFAULT_TASK_DEADLINE);
    }

    /**
     * Initialises the TaskBuilder with the data of {@code taskToCopy}.
     * @param taskToCopy
     */
    public TaskBuilder(Task taskToCopy) {
        taskDescription = taskToCopy.getTaskDescription();
        taskDeadline = taskToCopy.getTaskDeadline();
    }

    /**
     * Sets the {@code TaskDescription} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDescription(String taskDescription) {
        this.taskDescription = new TaskDescription(taskDescription);
        return this;
    }

    /**
     * Sets the {@code TaskDeadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDeadline(String taskDeadline) {
        this.taskDeadline = new TaskDeadline(taskDeadline);
        return this;
    }

    public Task build() {
        return new Task(taskDescription, taskDeadline);
    }
}
