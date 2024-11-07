package seedu.address.model.status;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Status in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Status can be left blank, or set to 'non_urgent' or 'urgent'. "
            + "Please use one of these options if specifying a status.";


    public final StatusEnum status;

    /**
     * Constructs a {@code Status}.
     *
     * @param statusString A valid status value, which is found in the {@code StatusEnum}
     */
    public Status(String statusString) {
        requireNonNull(statusString);
        checkArgument(isValidStatus(statusString), MESSAGE_CONSTRAINTS);
        if (statusString.isEmpty()) {
            this.status = StatusEnum.NA;
        } else {
            this.status = StatusEnum.valueOf(statusString.toUpperCase());
        }
    }

    /**
     * Returns true if a given string is a valid status value.
     *
     * @param test The string to be tested
     * @return A boolean value that checks if a given string, belongs to one of the valid status enum values
     */
    public static boolean isValidStatus(String test) {
        if (test.isEmpty()) {
            return false;
        }
        for (StatusEnum currentEnum: StatusEnum.values()) {
            if (currentEnum.name().equals(test.toUpperCase())) {
                return true;
            }
        }
        return false;
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
        return this.status.equals(otherStatus.status);
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    @Override
    public String toString() {
        return "[" + this.status.toString() + "]";
    }

    /**
     * Formats the status into a string form, without square brackets.
     * @return The string value of the status without brackets
     */
    public String toParsableString() {
        return status.toString();
    }

    /**
     * Represent what values Status can take.
     */
    public enum StatusEnum {
        URGENT,
        NON_URGENT,
        NA
    }
}
