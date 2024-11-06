package seedu.internbuddy.model.company;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS = "Status should be either '" + StatusType.INTERESTED + "', '"
            + StatusType.APPLIED + "', or'" + StatusType.CLOSED + "'.";

    public final StatusType value;

    /**
     * Constructs a {@code Status} using a {@link StatusType}.
     *
     * @param status A valid status.
     */
    public Status(StatusType status) {
        requireNonNull(status);
        value = status;
    }

    /**
     * Constructs a {@code Status} using an input {@code String}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        value = StatusType.valueOf(status);
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        requireNonNull(test);
        return StatusType.hasStatusType(test);
    }

    @Override
    public String toString() {
        return value.name();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }
}
