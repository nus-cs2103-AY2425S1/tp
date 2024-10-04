package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's scheduled appointment in the address book.
 * Guarantees: immutable; is always valid
 */
public class Schedule {

    public final String dateTime;

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