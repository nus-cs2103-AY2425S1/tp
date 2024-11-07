package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null));
    }

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointment = "";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }

    @Test
    public void isValidAppointment() {
        // null appointment
        assertThrows(NullPointerException.class, () -> Appointment.isValidAppointment(null));

        // invalid appointments
        assertFalse(Appointment.isValidAppointment("")); // empty string
        assertFalse(Appointment.isValidAppointment(" ")); // spaces only
        assertFalse(Appointment.isValidAppointment("2025-12-31")); // missing time
        assertFalse(Appointment.isValidAppointment("appointment")); // non-datetime string

        // valid appointments
        assertTrue(Appointment.isValidAppointment("2025-12-31 23:59"));
    }

    @Test
    public void equals() {
        Appointment appointment = new Appointment("2025-12-31 14:30");

        // same values -> returns true
        assertTrue(appointment.equals(new Appointment("2025-12-31 14:30")));

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different types -> returns false
        assertFalse(appointment.equals(5));

        // different values -> returns false
        assertFalse(appointment.equals(new Appointment("2025-12-31 15:30")));
    }
}
