package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task name in the address book.
 */

public class TaskName {
    public static final String NAME_CONSTRAINT =
        "Task Name cannot be blank.";

    public static final String VALIDATION_REGEX = "(.|\\s)*\\S(.|\\s)*";

    public final String taskName;

    /**
     * Constructs a {@code TaskName}.
     *
     * @param name A valid task name.
     */
    public TaskName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), NAME_CONSTRAINT);
        taskName = name;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns String of TaskName.
     */
    public String getTaskName() {
        return taskName;
    }

    @Override
    public String toString() {
        return taskName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskName otherName)) {
            return false;
        }
        return taskName.equals(otherName.taskName);
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }
}
