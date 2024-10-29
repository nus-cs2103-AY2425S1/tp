package seedu.address.model.student.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's taskDescription in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskDescription(String)}
 */
public class TaskDescription {
    public static final String MESSAGE_CONSTRAINTS = "Task description can take any values, and it should not be blank";

    /*
     * The first character of the task description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String taskDescription;

    /**
     * Constructs an {@code TaskDescription}.
     *
     * @param taskDescription A valid task description.
     */
    public TaskDescription(String taskDescription) {
        requireNonNull(taskDescription);
        checkArgument(isValidTaskDescription(taskDescription), MESSAGE_CONSTRAINTS);
        this.taskDescription = taskDescription;
    }

    public static boolean isValidTaskDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        // NOTE: this string is how it is saved into the json
        return taskDescription;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskDescription)) {
            return false;
        }

        TaskDescription otherTaskDescription = (TaskDescription) other;
        return taskDescription.equals(otherTaskDescription.taskDescription);
    }

    @Override
    public int hashCode() {
        return taskDescription.hashCode();
    }
}
