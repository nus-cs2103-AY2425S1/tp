package seedu.address.model.appointment;

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
        if (person.getAppointment() == null) {
            return false;
        } else if (this.date == null) {
            return true;
        }

        LocalDate startDate = person.getAppointment().getStart().toLocalDate();
        LocalDate endDate = person.getAppointment().getEnd().toLocalDate();

        return !this.date.isBefore(startDate) && !this.date.isAfter(endDate);
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
