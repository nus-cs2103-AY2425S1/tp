package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's scheduled appointment in the address book.
 * Guarantees: immutable; is always valid
 */
public class Schedule {

    public final String dateTime;

    /**
     * Constructs a {@code Schedule} object with the given date and time.
     *
     * @param dateTime The date and time of the scheduled appointment.
     * @throws NullPointerException if the {@code dateTime} is null.
     */
    public Schedule(String dateTime) {
        requireNonNull(dateTime);
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return this.dateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && dateTime.equals(((Schedule) other).dateTime)); // state check
    }
    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
