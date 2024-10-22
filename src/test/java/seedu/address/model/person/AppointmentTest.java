package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void equals() {
        Appointment firstAppointment = new Appointment("01-01-2024 12:00");
        Appointment secondAppointment = new Appointment("02-02-2024 15:00");

        // same object -> returns true
        assertTrue(firstAppointment.equals(firstAppointment));

        // same values -> returns true
        Appointment firstAppointmentCopy = new Appointment("01-01-2024 12:00");
        assertTrue(firstAppointment.equals(firstAppointmentCopy));

        // different types -> returns false
        assertFalse(firstAppointment.equals(1));

        // null -> returns false
        assertFalse(firstAppointment.equals(null));

        // different appointments -> returns false
        assertFalse(firstAppointment.equals(secondAppointment));
    }

    @Test
    public void test_toString_returnsCorrectFormat() {
        Appointment appointment = new Appointment("01-01-2024 12:00");
        assertEquals("01-01-2024 12:00", appointment.toString());
    }

    @Test
    public void test_equalsSymmetry() {
        Appointment appointmentA = new Appointment("03-03-2024 09:00");
        Appointment appointmentB = new Appointment("03-03-2024 09:00");

        // Symmetry: a.equals(b) == b.equals(a)
        assertTrue(appointmentA.equals(appointmentB));
        assertTrue(appointmentB.equals(appointmentA));
    }

    @Test
    public void test_appointmentMatchesDateTime() {
        // Check if the date and time matches after construction
        Appointment appointment = new Appointment("15-08-2024 18:30");
        assertEquals("15-08-2024 18:30", appointment.toString());
    }

    @Test
    public void test_appointmentDoesNotMatchDifferentDateTime() {
        // Check that two different appointments are not considered equal
        Appointment appointment1 = new Appointment("10-01-2024 14:00");
        Appointment appointment2 = new Appointment("10-02-2024 14:00");

        assertFalse(appointment1.equals(appointment2));
    }

    @Test
    public void test_hashCodeConsistency() {
        Appointment appointment = new Appointment("01-04-2024 11:30");

        // Same object should return the same hashCode
        assertEquals(appointment.hashCode(), appointment.hashCode());
    }

    @Test
    public void test_toStringMethod() {
        Appointment appointment = new Appointment("25-12-2024 09:00");

        String expected = "25-12-2024 09:00";
        assertEquals(expected, appointment.toString());
    }
}
