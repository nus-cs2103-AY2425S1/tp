package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskDeadline;
import seedu.address.model.student.task.TaskDescription;

/**
 * Jackson-friendly version of {@link Task}.
 */
public class JsonAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String taskDescription;
    private final String taskDeadline;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("taskDescription") String taskDescription,
                           @JsonProperty("taskDeadline") String taskDeadline) {
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        this.taskDescription = source.getTaskDescription().toString();
        this.taskDeadline = source.getTaskDeadline().toString();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     */
    public Task toModelType() throws IllegalValueException {
        if (taskDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskDescription.class.getSimpleName()));
        }
        if (!TaskDescription.isValidTaskDescription(taskDescription)) {
            throw new IllegalValueException(TaskDescription.MESSAGE_CONSTRAINTS);
        }
        final TaskDescription modelTaskDesc = new TaskDescription(taskDescription);

        if (taskDeadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskDeadline.class.getSimpleName()));
        }
        if (!TaskDeadline.isValidTaskDeadline(taskDeadline)) {
            throw new IllegalValueException(TaskDeadline.MESSAGE_CONSTRAINTS);
        }
        final TaskDeadline modelTaskDeadline = new TaskDeadline(taskDeadline);

        return new Task(modelTaskDesc, modelTaskDeadline);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedTask)) {
            return false;
        }

        JsonAdaptedTask otherTask = (JsonAdaptedTask) other;
        return taskDescription.equals(otherTask.taskDescription) && taskDeadline.equals(otherTask.taskDeadline);
    }
}
