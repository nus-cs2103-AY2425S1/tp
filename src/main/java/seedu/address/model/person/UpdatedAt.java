package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;

/**
 * Represents the time at which the Person was last modified.
 * Guarantees: immutability; is valid as declared in {@link #isValidUpdatedAt(LocalDateTime)}
 */
public class UpdatedAt {

    private static final String MESSAGE_CONSTRAINTS = "UpdatedAt should not be set in the future";

    private final LocalDateTime value;

    /**
     * Constructs a {@code UpdatedAt}.
     */
    public UpdatedAt(LocalDateTime dateTime) {
        checkArgument(isValidUpdatedAt(dateTime), MESSAGE_CONSTRAINTS);
        value = dateTime;
    }

    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // checks for null
        if (!(other instanceof UpdatedAt otherUpdatedAt)) {
            return false;
        }

        return this.value.equals(otherUpdatedAt.value);
    }

    /**
     * Returns true if {@code test} is a valid datetime (i.e. not set in the future).
     * @param test A {@code LocalDateTime} to be tested
     * @return true if {@code test} is valid, false otherwise
     */
    public static boolean isValidUpdatedAt(LocalDateTime test) {
        requireNonNull(test);
        return !test.isAfter(LocalDateTime.now());
    }

    /**
     * A factory method to create an {@code UpdatedAt} with current datetime.
     */
    public static UpdatedAt now() {
        return new UpdatedAt(LocalDateTime.now());
    }
}
