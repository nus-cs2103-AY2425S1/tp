package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's priority in the address book.
 */
public class Priority {


    public static final String MESSAGE_CONSTRAINTS =
            "Priority should only be 'HIGH', 'MEDIUM' or 'LOW'";

    public enum PriorityLevel {

        HIGH,
        MEDIUM,
        LOW;
    }

    public PriorityLevel priority;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        switch (priority.toLowerCase()) {
        case "high":
            this.priority = PriorityLevel.HIGH;
            break;
        case "medium":
            this.priority = PriorityLevel.MEDIUM;
            break;
        default:
            this.priority = PriorityLevel.LOW;
            break;
        }
    }

    /**
     * Returns true if a given priority is a valid priority level.
     */
    public static boolean isValidPriority(String test) {
        return test.equalsIgnoreCase("low") ||
               test.equalsIgnoreCase("medium") ||
               test.equalsIgnoreCase("high");
    }

    @Override
    public String toString() {
        return priority.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Priority)) {
            return false;
        }

        Priority otherPriority = (Priority) other;
        return priority.equals(otherPriority.priority);
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }

}
