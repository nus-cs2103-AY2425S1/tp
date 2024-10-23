package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointment = "21/10 11:20";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }

    @Test
    public void isValidAppointment() {
        // null appointment
        assertThrows(NullPointerException.class, () -> Appointment.isValidAppointment(null));

        // invalid appointment
        assertFalse(Appointment.isValidAppointment("")); // empty string
        assertFalse(Appointment.isValidAppointment(" ")); // spaces only
        assertFalse(Appointment.isValidAppointment("01/01/2024 06:30")); // before opening hours
        assertFalse(Appointment.isValidAppointment("01/01/2024 22:30")); // after opening hours

        // valid appointments
        assertTrue(Appointment.isValidAppointment("01/01/2024 08:30"));
        assertTrue(Appointment.isValidAppointment("01/01/2024 21:30"));
        assertTrue(Appointment.isValidAppointment("01-01-2024 08:30"));
        assertTrue(Appointment.isValidAppointment("01 01 2024 21:30"));
        assertTrue(Appointment.isValidAppointment("01 January 2024 08:30"));
        assertTrue(Appointment.isValidAppointment("-"));
    }

}
