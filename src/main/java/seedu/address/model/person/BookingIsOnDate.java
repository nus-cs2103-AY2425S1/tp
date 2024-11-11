package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Appointment}'s date lies on the specified date.
 */
public class BookingIsOnDate implements Predicate<Person> {

    private final LocalDate date;

    /**
     * Creates a BookingIsOnDate object.
     *
     * @param date Date to check appointment against.
     */
    public BookingIsOnDate(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public boolean test(Person person) {
        return person.getAppointment().isOn(date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingIsOnDate)) {
            return false;
        }

        BookingIsOnDate otherBookingIsOnDate = (BookingIsOnDate) other;
        return date.equals(otherBookingIsOnDate.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date).toString();
    }
}
