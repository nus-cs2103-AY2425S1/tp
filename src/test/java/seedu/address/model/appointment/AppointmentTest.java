package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppointmentTest {
    @Test
    public void equals() {
        Appointment appointment = new Appointment(new Date("121224"), new From("0800"), new To("1000"));

        // null value -> return false
        assertFalse(appointment.equals(null));

        // different type -> return false
        assertFalse(appointment.equals(5));

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // same values -> returns true
        Appointment otherAppointment = new Appointment(new Date("121224"), new From("0800"), new To("1000"));
        assertTrue(appointment.equals(otherAppointment));

        // different date -> returns false
        otherAppointment = new Appointment(new Date("131224"), new From("0800"), new To("1000"));
        assertFalse(appointment.equals(otherAppointment));

        // different from -> returns false
        otherAppointment = new Appointment(new Date("121224"), new From("0900"), new To("1000"));
        assertFalse(appointment.equals(otherAppointment));

        // different to -> returns false
        otherAppointment = new Appointment(new Date("121224"), new From("0800"), new To("1100"));
        assertFalse(appointment.equals(otherAppointment));

    }
}
