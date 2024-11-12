package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Status in the address book.
 * Guarantees: immutable; value is valid as declared in {@link StatusType}
 */
public class Status {

    /**
     * Represents the allowed status types in the address book.
     * Patient are classified into High, Low, Discharged
     */
    public enum StatusType {
        HIGH, LOW, MEDIUM, DISCHARGED, NEW
    }

    public static final String MESSAGE_CONSTRAINTS = "Status should only be one of the following: "
            + "HIGH, LOW, MEDIUM, DISCHARGED, NEW";

    public final String statusType;
    /**
     * Constructs a {@code Status}.
     *
     * @param statusType A valid status type as a String.
     */
    public Status(String statusType) {
        requireNonNull(statusType);
        if (!isValidStatus(statusType)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.statusType = statusType.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        try {
            StatusType.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    @Override
    public String toString() {
        return statusType;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return Objects.equals(statusType, otherStatus.statusType);
    }

    @Override
    public int hashCode() {
        return statusType.hashCode();
    }
}
