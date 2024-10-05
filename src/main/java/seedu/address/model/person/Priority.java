package seedu.address.model.person;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's priority in the address book.
 * Guarantee: immutable; is always valid
 */
public class Priority {

    /**
     * Represents the possible levels of priority.
     * The levels are: LOW, MEDIUM, and HIGH.
     */
    public enum Level {
        LOW, MEDIUM, HIGH
    }

    // The priority level of the person.
    private final Level priority;

    /**
     * Constructs a {@code Priority} object with the specified {@code Level}.
     *
     * @param priority The priority level (LOW, MEDIUM, or HIGH) for the person.
     *                 Must not be null.
     * @throws NullPointerException if the {@code priority} is null.
     */
    public Priority(Level priority) {
        requireNonNull(priority);
        this.priority = priority;
    }

    /**
     * Returns the priority level of the person.
     *
     * @return The priority level (LOW, MEDIUM, or HIGH).
     */
    public Level getPriority() {
        return priority;
    }

    /**
     * Returns a string representation of the priority level.
     * The string will match the name of the enum value: "LOW", "MEDIUM", or "HIGH".
     *
     * @return The string representation of the priority level.
     */
    @Override
    public String toString() {
        return priority.name();
    }

    /**
     * Compares this {@code Priority} object with another object for equality.
     * Two {@code Priority} objects are considered equal if they have the same priority level.
     *
     * @param other The object to compare with.
     * @return {@code true} if the other object is a {@code Priority} with the same priority level, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Priority
                && priority.equals(((Priority) other).priority));
    }

    /**
     * Returns a hash code for this {@code Priority} object.
     *
     * @return The hash code based on the priority level.
     */
    @Override
    public int hashCode() {
        return priority.hashCode();
    }
}
