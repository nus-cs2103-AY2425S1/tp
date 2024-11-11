package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's priority in the address book.
 * The priority can only be one of these values: "NONE", "LOW", "MEDIUM", or "HIGH".
 *
 * Guarantees: The priority is immutable and always valid.
 */
public class Priority {

    // Regex to validate the priority value (LOW, MEDIUM, or HIGH).
    public static final String VALIDATION_REGEX = "NONE|LOW|MEDIUM|HIGH";

    // Error message for invalid priority values.
    public static final String MESSAGE_CONSTRAINTS = "Priority should only be 'NONE', 'LOW', 'MEDIUM', or 'HIGH'.";

    public final String priority;

    /**
     * Default constructor that sets the priority to "NONE".
     */
    public Priority() {
        this.priority = "NONE";
    }

    /**
     * Constructs a {@code Priority} object with the specified priority string.
     *
     * @param priority The priority value for the person.
     *                 Must be either "NONE", "LOW", "MEDIUM", or "HIGH".
     * @throws IllegalArgumentException if the {@code priority} is invalid.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        this.priority = priority;
    }

    /**
     * Checks if the given priority is valid (NONE, LOW, MEDIUM, or HIGH).
     *
     * @param test The priority string to check.
     * @return {@code true} if the priority is valid, otherwise {@code false}.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the priority level of the person.
     *
     * @return The priority string (NONE, LOW, MEDIUM, or HIGH).
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Returns a string representation of the priority level.
     *
     * @return The priority string.
     */
    @Override
    public String toString() {
        return priority;
    }

    /**
     * Compares this {@code Priority} object with another object for equality.
     * Two {@code Priority} objects are considered equal if they have the same priority value.
     *
     * @param other The object to compare with.
     * @return {@code true} if the other object is a {@code Priority} with the same value,
     *     otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && priority.equals(((Priority) other).priority));
    }

    /**
     * Returns a hash code for this {@code Priority} object.
     *
     * @return The hash code based on the priority string.
     */
    @Override
    public int hashCode() {
        return priority.hashCode();
    }

    /**
     * Checks if the priority is set to "NONE".
     *
     * @return True if the priority is "NONE", indicating that no priority is assigned;
     *         false otherwise.
     */
    public boolean isEmpty() {
        return this.priority.equals("NONE");
    }
}
