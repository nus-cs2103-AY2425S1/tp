package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String taskName;
    private final String deadline;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("taskname") String taskName,
                            @JsonProperty("deadline") String deadline,
                            @JsonProperty("status") String status) {
        this.taskName = taskName;
        this.deadline = deadline;
        this.status = status;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        taskName = source.getTaskName().toString();
        deadline = source.getDeadline().deadlineInInputFormat();
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Task toModelType() throws IllegalValueException {
        if (taskName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskName.class.getSimpleName()));
        }
        if (!TaskName.isValidName(taskName)) {
            throw new IllegalValueException(TaskName.NAME_CONSTRAINT);
        }
        final TaskName modelTaskName = new TaskName(taskName);

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }
        if (!Deadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        final Deadline modelDeadline = new Deadline(LocalDateTime.parse(deadline, Deadline.DATETIME_FORMATTER));

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        final Status modelStatus = Status.valueOf(status);

        return new Task(modelTaskName, modelDeadline, modelStatus);
    }

}
