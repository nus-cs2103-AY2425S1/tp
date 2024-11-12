package seedu.address.model.appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Appointments}'s {@code Date} matches any of the dates given.
 */
public class AppointmentDateIsOnPredicate implements Predicate<Appointment> {
    private final List<LocalDate> dates;

    public AppointmentDateIsOnPredicate(List<LocalDate> dates) {
        this.dates = dates;
    }

    @Override
    public boolean test(Appointment appointment) {
        return dates.stream()
            .anyMatch(date ->
                    DateUtil.isSameDate(appointment.getAppointmentDateTime().toLocalDate(), date));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentDateIsOnPredicate)) {
            return false;
        }

        AppointmentDateIsOnPredicate otherPredicate = (AppointmentDateIsOnPredicate) other;
        return dates.equals(otherPredicate.dates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("dates", dates).toString();
    }
}
