package seedu.address.model.meetup;

/**
 * Represents a meetup's starting time in the meet up list.
 * Guarantees: immutable;
 */
public class To extends DateTime {

    public static final String MESSAGE_CONSTRAINTS_FORMAT =
            "To date-time provided should only be in the format: YYYY-MM-DD HH:mm";

    public static final String MESSAGE_CONSTRAINTS_DATETIME =
            "To date-time provided is invalid";
    public static final String MESSAGE_CONSTRAINTS_TO_FROM =
            "To date-time must occur after from date-time";

    /**
     * Constructs a {@code To}.
     *
     * @param to A valid string that can transformed to a date.
     */
    public To(String to) {
        super(to);
    }

    /**
     * Ret
     */
    public boolean isValidToFrom(From from) {
        if (from == null) {
            return false;
        }

        return this.value.isAfter(from.value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof To)) {
            return false;
        }

        To otherTo = (To) other;
        return value.equals(otherTo.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
