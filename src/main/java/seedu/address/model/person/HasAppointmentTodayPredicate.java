package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;

/**
 * Tests that a {@code Person} has a {@code Appointment} today.
 */
public class HasAppointmentTodayPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        Appointment appointment = person.getAppointment();
        return appointment.isToday();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof HasAppointmentTodayPredicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
