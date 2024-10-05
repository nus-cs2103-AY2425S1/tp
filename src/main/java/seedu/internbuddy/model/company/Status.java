package seedu.internbuddy.model.company;

/**
 * Represents a Company's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS = "Status should be either 'INTERESTED', 'APPLIED' or 'CLOSED'";

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        value = status.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.equals("INTERESTED") || test.equals("APPLIED") || test.equals("CLOSED");
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
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }
}
