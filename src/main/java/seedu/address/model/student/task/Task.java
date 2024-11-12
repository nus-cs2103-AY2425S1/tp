package seedu.address.model.student.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a TaskList's task in the address book.
 * Guarantees: immutable; is always valid
 */
public class Task {
    public static final String MESSAGE_CONSTRAINTS = "Task should have task description and deadline";
    private final TaskDescription taskDescription;
    private final TaskDeadline taskDeadline;

    /**
     * Constructs a {@code Task}.
     *
     * @param taskDescription A valid task description.
     * @param taskDeadline A valid task deadline.
     */
    public Task(TaskDescription taskDescription, TaskDeadline taskDeadline) {
        requireNonNull(taskDescription);
        requireNonNull(taskDeadline);
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
    }

    /**
     * Returns task description of task.
     */
    public TaskDescription getTaskDescription() {
        return taskDescription;
    }

    /**
     * Returns task deadline of task.
     */
    public TaskDeadline getTaskDeadline() {
        return taskDeadline;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("task description", taskDescription)
                .add("task deadline", taskDeadline)
                .toString();
    }

    public String toDescription() {
        return taskDescription + " by " + taskDeadline.toDescription();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return taskDescription.equals(otherTask.taskDescription)
                && taskDeadline.equals(otherTask.taskDeadline);
    }

    @Override
    public int hashCode() {
        return taskDescription.hashCode() * taskDeadline.hashCode();
    }

    /**
     * Compares task to other task based on their deadline.
     */
    public int compareTo(Task other) {
        return taskDeadline.compareTo(other.taskDeadline);
    }
}
