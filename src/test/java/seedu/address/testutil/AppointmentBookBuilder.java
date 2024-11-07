package seedu.address.testutil;

import seedu.address.model.AppointmentBook;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class to help with building AppointmentBook objects.
 * Example usage: <br>
 * {@code AppointmentBook ab = new AppointmentBookBuilder().withPerson("John", "Doe").build();}
 */
public class AppointmentBookBuilder {

    private final AppointmentBook appointmentBook;

    public AppointmentBookBuilder() {
        appointmentBook = new AppointmentBook();
    }

    /**
     * Adds a new {@code Appointment} to the {@code Appointment} that we are building.
     */
    public AppointmentBookBuilder withAppointment(Appointment appointment) {
        appointmentBook.addAppointment(appointment);
        return this;
    }

    public AppointmentBook build() {
        return appointmentBook;
    }
}
