package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class AppointmentTypeTest {

    @Test
    public void testValidAppointmentType() {
        assertTrue(AppointmentType.isValidAppointmentType("Check Up"));
        assertTrue(AppointmentType.isValidAppointmentType("Follow Up"));
    }

    @Test
    public void testInvalidAppointmentType() {
        assertFalse(AppointmentType.isValidAppointmentType(" Check Up"));
        assertFalse(AppointmentType.isValidAppointmentType(""));
        assertFalse(AppointmentType.isValidAppointmentType(" "));
    }

    @Test
    public void testConstructorWithValidInput() {
        AppointmentType appointmentType = new AppointmentType("Check Up");
        assertEquals("Check Up", appointmentType.value);
    }

    @Test
    public void testConstructorWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> new AppointmentType(""));
        assertThrows(IllegalArgumentException.class, () -> new AppointmentType(" "));
        assertThrows(IllegalArgumentException.class, () -> new AppointmentType(" CheckUp"));
    }

    @Test
    public void testToString() {
        AppointmentType appointmentType = new AppointmentType("Checkup");
        assertEquals("Checkup", appointmentType.toString());
    }

    @Test
    public void testEquals() {
        AppointmentType appointmentType1 = new AppointmentType("Checkup");
        AppointmentType appointmentType2 = new AppointmentType("Checkup");
        AppointmentType appointmentType3 = new AppointmentType("Followup");

        assertEquals(appointmentType1, appointmentType2);
        assertNotEquals(appointmentType1, appointmentType3);
        assertNotEquals(null, appointmentType1);
        assertNotEquals("Checkup", appointmentType1);
        assertEquals(appointmentType1, appointmentType1);
    }

    @Test
    public void testHashCode() {
        AppointmentType appointmentType1 = new AppointmentType("Checkup");
        AppointmentType appointmentType2 = new AppointmentType("Checkup");
        AppointmentType appointmentType3 = new AppointmentType("Followup");

        assertEquals(appointmentType1.hashCode(), appointmentType2.hashCode());
        assertNotEquals(appointmentType1.hashCode(), appointmentType3.hashCode());
    }
}
