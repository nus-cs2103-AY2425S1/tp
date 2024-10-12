package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Task that can be assigned to a Group.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {
    //
    private final TaskName name;
    private final Deadline deadline;
    private final Status status;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName name, Deadline deadline) {
        this.name = name;
        this.deadline = deadline;
        this.status = Status.PENDING;
    }

    public TaskName getTaskName() {
        return name;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both Tasks have the same name and data fields.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
            && otherTask.getTaskName().toString().equalsIgnoreCase(getTaskName().toString())
            && otherTask.getDeadline().equals(getDeadline())
            && otherTask.getStatus() == getStatus();
    }

    /**
     * Returns true if both Tasks have the same name and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Task otherTask)) {
            return false;
        }
        return name.equals(otherTask.getTaskName())
            && deadline.equals(otherTask.getDeadline())
            && status.equals(otherTask.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, deadline, status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", name)
            .add("deadline", deadline)
            .add("status", status)
            .toString();
    }
}
