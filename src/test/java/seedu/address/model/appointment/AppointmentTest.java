package seedu.address.model.appointment;

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

        // valid Appointment date
        assertTrue(() -> Appointment.isValidAppointment("11/11/2025 1100"));
    }
}
