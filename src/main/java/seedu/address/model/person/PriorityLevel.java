package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's priority level in the address book.
 * Guarantees: immutable; is always valid as it is checked during construction.
 */
public class PriorityLevel {
    public static final String MESSAGE_CONSTRAINTS = "Priority level must be 1, 2, or 3.";
    private final int value;

    /**
     * Constructs a {@code PriorityLevel}.
     *
     * @param value A valid priority level.
     */
    public PriorityLevel(int value) {
        requireNonNull(value);
        if (!isValidPriorityLevel(value)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = value;
    }

    /**
     * Returns true if a given integer is a valid priority level.
     */
    public static boolean isValidPriorityLevel(int test) {
        return test >= 1 && test <= 3;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PriorityLevel)) {
            return false;
        }
        PriorityLevel that = (PriorityLevel) other;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
