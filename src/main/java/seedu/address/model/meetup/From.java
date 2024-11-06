package seedu.address.model.meetup;

/**
 * Represents a meetup's starting time in the meet up list.
 * Guarantees: immutable;
 */
public class From extends DateTime {

    public static final String MESSAGE_CONSTRAINTS_FORMAT =
            "From date-time provided should only be in the format: YYYY-MM-DD HH:mm";

    public static final String MESSAGE_CONSTRAINTS_DATETIME =
            "From date-time provided is invalid";

    /**
     * Constructs a {@code From}.
     *
     * @param from A valid string that can transformed from a date.
     */
    public From(String from) {
        super(from);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof From)) {
            return false;
        }

        From otherFrom = (From) other;
        return value.equals(otherFrom.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
