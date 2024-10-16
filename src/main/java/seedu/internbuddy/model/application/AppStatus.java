package seedu.internbuddy.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.commons.util.AppUtil.checkArgument;

/**
 * Represents an Application's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class AppStatus {
    public static final String MESSAGE_CONSTRAINTS =
        "Application Status should be either 'APPLIED',"
        + " 'INTERVIEWED', 'OA', 'OFFERED', 'ACCEPTED' or 'REJECTED'";

    public final String value;

    /**
     * Constructs a {@code AppStatus}.
     *
     * @param status A valid status.
     */
    public AppStatus(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        value = status.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        switch (test.toUpperCase()) {
        case "APPLIED":
        case "INTERVIEWED":
        case "OA":
        case "OFFERED":
        case "ACCEPTED":
        case "REJECTED":
            return true;
        default:
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppStatus)) {
            return false;
        }

        AppStatus otherStatus = (AppStatus) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
