package seedu.address.model.task;

public class ParsedTask {
    private final String taskType;
    private final String taskDetails;

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
