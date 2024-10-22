package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's project status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidProjectStatus(String)}
 */
public class ProjectStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Project status must be either 'in progress' or 'completed' (case insensitive)";
    public static final String NO_PROJECT_STATUS = "__No_Project_Status__";
    private static final String IN_PROGRESS = "in progress";
    private static final String COMPLETE = "completed";

    public final boolean isComplete;
    private boolean isEmpty = false;

    /**
     * Constructs a {@code ProjectStatus}.
     *
     * @param status A valid project status.
     */
    public ProjectStatus(String status) {
        requireNonNull(status);
        if (status.equals(NO_PROJECT_STATUS)) {
            isComplete = parseStatus("");
            isEmpty = true;
        } else {
            checkArgument(isValidProjectStatus(status), MESSAGE_CONSTRAINTS);
            isComplete = parseStatus(status);
        }
    }

    /**
     * Returns true if the given string is a valid project status.
     */
    public static boolean isValidProjectStatus(String test) {
        return test.equalsIgnoreCase(IN_PROGRESS) || test.equalsIgnoreCase(COMPLETE);
    }

    /**
     * Parses the project status string into a boolean.
     * @param status The project status string.
     * @return {@code true} if the status is "complete", {@code false} if it is "in progress".
     */
    private static boolean parseStatus(String status) {
        return status.equalsIgnoreCase(COMPLETE);
    }

    @Override
    public String toString() {
        if (isEmpty) {
            return "";
        }
        return isComplete ? COMPLETE : IN_PROGRESS;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectStatus)) {
            return false;
        }

        ProjectStatus otherStatus = (ProjectStatus) other;
        return isComplete == otherStatus.isComplete;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isComplete);
    }

}
