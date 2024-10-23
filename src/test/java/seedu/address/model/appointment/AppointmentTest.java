package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

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

    @Test
    public void getDate_validDate_returnsCorrectDate() {
        LocalDateTime expectedDate = LocalDateTime.of(2025, 11, 11, 11, 0);
        Appointment appointment = new Appointment("11/11/2025 1100");
        assertEquals(expectedDate, appointment.getDate());
    }
    @Test
    public void equals() {
        Appointment appointment = new Appointment("11/11/2025 1100");

        // same values -> returns true
        assertTrue(appointment.equals(new Appointment("11/11/2025 1100")));

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different types -> returns false
        assertFalse(appointment.equals(5.0f));

        // different values -> returns false
        assertFalse(appointment.equals(new Appointment("11/11/2025 1200")));
    }

    @Test
    public void toStringTest() {
        Appointment appointment = new Appointment("11/11/2025 1100");
        assertEquals("[11/11/2025 1100]", appointment.toString());
    }
}
