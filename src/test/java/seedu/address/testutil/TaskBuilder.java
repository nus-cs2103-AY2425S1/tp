package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.task.Deadline;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

//@@author gho7sie

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {
    public static final TaskName DEFAULT_TASK_NAME = new TaskName("Submit v1.3");
    public static final Deadline DEFAULT_DEADLINE = new Deadline(LocalDateTime.of(2024, 10, 14, 12, 0));

    private TaskName taskName;
    private Deadline deadline;
    private Status status;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        taskName = DEFAULT_TASK_NAME;
        deadline = DEFAULT_DEADLINE;
        status = Status.PENDING;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        taskName = taskToCopy.getTaskName();
        deadline = taskToCopy.getDeadline();
        status = taskToCopy.getStatus();
    }

    /**
     * Sets the {@code TaskName} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskName(String taskName) {
        this.taskName = new TaskName(taskName);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(LocalDateTime deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Task} that we are building.
     */
    public TaskBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public Task build() {
        return new Task(taskName, deadline, status, 1);
    }
}
