package seedu.address.model.task;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private Status status;
    private int groupsWithTask = 1;

    /**
     * Allows serialization.
     */
    public Task() {
        name = new TaskName("default");
        deadline = new Deadline(LocalDateTime.now());
        status = Status.PENDING;
    }

    /**
     * Creates a Task with {@code name} and {@code deadline}.
     * Every field must be present and not null.
     */
    public Task(TaskName name, Deadline deadline) {
        this.name = name;
        this.deadline = deadline;
        this.status = Status.PENDING;
    }

    /**
     * Creates a Task with {@code name}, {@code deadline}, {@code status}, {@code groupsWithTask}.
     * Every field must be present and not null.
     */
    public Task(TaskName name, Deadline deadline, Status status, int groupsWithTask) {
        this.name = name;
        this.deadline = deadline;
        this.status = status;
        this.groupsWithTask = groupsWithTask;
    }

    /**
     * Creates a defensive copy of the task
     * @param otherTask  The task whose values are to be copied
     */
    public Task(Task otherTask) {
        this.name = otherTask.name;
        this.deadline = otherTask.getDeadline();
        this.status = otherTask.getStatus();
        this.groupsWithTask = otherTask.getGroupsWithTask();
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

    public int getGroupsWithTask() {
        return groupsWithTask;
    }

    public void setStatus() {
        ZoneId zid = ZoneId.of("Asia/Singapore");
        LocalDateTime currentTime = LocalDateTime.now(zid);
        if (deadline.time.compareTo(currentTime) < 0
                && (this.status == Status.PENDING || this.status == Status.OVERDUE)) {
            this.status = Status.OVERDUE;
        }
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
            && otherTask.getDeadline().equals(getDeadline());
    }

    /**
     * Increases the number of groups with {@code Task} by 1.
     */
    public void increaseGroupWithTask() {
        this.groupsWithTask++;
    }

    /**
     * Decreases the number of groups with {@code Task} by 1.
     */
    public void decreaseGroupWithTask() {
        this.groupsWithTask--;
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
            && deadline.equals(otherTask.getDeadline());
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
