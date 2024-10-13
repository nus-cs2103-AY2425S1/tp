package seedu.address.model.person.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Task's taskDeadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskDeadline(String)}
 */
public class TaskDeadline {
    public static final String MESSAGE_CONSTRAINTS = "Task deadline should be in the format YYYY-MM-DD.";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final LocalDateTime taskDeadline;

    /**
     * Constucts a {@code TaskDeadline}
     * @param taskDeadline A valid task deadline.
     */
    public TaskDeadline(String taskDeadline) {
        requireNonNull(taskDeadline);
        checkArgument(isValidTaskDeadline(taskDeadline), MESSAGE_CONSTRAINTS);
        this.taskDeadline = LocalDateTime.parse(taskDeadline, formatter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskDeadline)) {
            return false;
        }

        TaskDeadline otherTaskDeadline = (TaskDeadline) other;
        return taskDeadline.equals(otherTaskDeadline.taskDeadline);
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidTaskDeadline(String test) {
        boolean valid = true;
        try {
            LocalDateTime.parse(test, formatter);
        } catch (DateTimeParseException e) {
            valid = false;
        }
        return valid;
    }

    @Override
    public int hashCode() {
        return taskDeadline.hashCode();
    }

    @Override
    public String toString() {
        // NOTE: this string is how it is saved into the json
        return taskDeadline.format(formatter);
    }

    public int compareTo(TaskDeadline other) {
        return taskDeadline.compareTo(other.taskDeadline);
    }
}
