package seedu.address.model.task;

import java.util.Objects;

/**
 * Represents a task that has been parsed into its type and details.
 */
public class ParsedTask {
    private final String taskType;
    private final String taskDetails;

    /**
     * Constructs a {@code ParsedTask} with the specified task type and task details.
     *
     * @param taskType The type of the task (e.g., todo, deadline, event).
     * @param taskDetails The details of the task (e.g., description, date).
     */
    public ParsedTask(String taskType, String taskDetails) {
        this.taskType = taskType;
        this.taskDetails = taskDetails;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    /**
     * Returns true if both parsed tasks have the same task type and details.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ParsedTask)) {
            return false;
        }

        ParsedTask otherParsedTask = (ParsedTask) other;
        return Objects.equals(taskType, otherParsedTask.taskType)
                && Objects.equals(taskDetails, otherParsedTask.taskDetails);
    }

    /**
     * Returns the hash code for the parsed task based on the task type and details.
     */
    @Override
    public int hashCode() {
        return Objects.hash(taskType, taskDetails);
    }
}
