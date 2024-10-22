package seedu.address.model.task;

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
}
