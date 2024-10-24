package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateUtil.DATE_DISPLAY_FORMATTER;
import static seedu.address.commons.util.DateUtil.DATE_FORMATTER;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Appointment}'s start or end date matches the date given.
 * Returns True if the date given is null and the person has an appointment.
 */
public class AppointmentContainsDatePredicate implements Predicate<Person> {
    private final LocalDate date;

    public AppointmentContainsDatePredicate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        if (person.getAppointment() == null) {
            return false;
        } else if (this.date == null) {
            return true;
        }

        LocalDate startDate = person.getAppointmentStart().toLocalDate();
        LocalDate endDate = person.getAppointmentEnd().toLocalDate();

        boolean isDateBeforeStart = this.date.isBefore(startDate);
        boolean isDateAfterEnd = this.date.isAfter(endDate);

        return !isDateBeforeStart && !isDateAfterEnd;
    }

    /**
     * Get predicate date in a more displayable format
     *
     * @return String containing date used for predicate
     */
    public String getPredicateDate() {
        return date.format(DATE_DISPLAY_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentContainsDatePredicate)) {
            return false;
        }

        AppointmentContainsDatePredicate otherAppointmentContainsDatePredicate =
                (AppointmentContainsDatePredicate) other;
        return date.equals(otherAppointmentContainsDatePredicate.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date.format(DATE_FORMATTER))
                .toString();
    }
}
