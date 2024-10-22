package seedu.address.model;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Helper class to link Appointment to patient;
 */
public record OwnedAppointment(Appointment appointment, Person owner) {
}
