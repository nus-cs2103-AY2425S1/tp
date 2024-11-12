package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class AppointmentTypeTest {

    @Test
    public void isValidAppointmentType_validTypes_returnsTrue() {

        // EP: valid types
        assertTrue(AppointmentType.isValidAppointmentType("Check Up"));
        assertTrue(AppointmentType.isValidAppointmentType("Follow Up"));
    }

    @Test
    public void isValidAppointmentType_invalidTypes_returnsFalse() {
        // EP: leading whitespaces
        assertFalse(AppointmentType.isValidAppointmentType(" Check Up"));

        // EP: empty string
        assertFalse(AppointmentType.isValidAppointmentType(""));

        // EP: only whitespace
        assertFalse(AppointmentType.isValidAppointmentType(" "));
    }

    @Test
    public void constructor_validType_createsAppointmentType() {
        AppointmentType appointmentType = new AppointmentType("Check Up");
        assertEquals("Check Up", appointmentType.value);
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        // EP: empty string
        assertThrows(IllegalArgumentException.class, () -> new AppointmentType(""));

        // EP: only whitespace
        assertThrows(IllegalArgumentException.class, () -> new AppointmentType(" "));

        // EP: leading whitespaces
        assertThrows(IllegalArgumentException.class, () -> new AppointmentType(" CheckUp"));
    }

    @Test
    public void toString_validAppointmentType_returnsFormattedString() {
        AppointmentType appointmentType = new AppointmentType("Checkup");
        assertEquals("Checkup", appointmentType.toString());
    }

    @Test
    public void equals_compareIdenticalAndDifferentTypes_returnsCorrectEquality() {
        AppointmentType appointmentType1 = new AppointmentType("Checkup");
        AppointmentType appointmentType2 = new AppointmentType("Checkup");
        AppointmentType appointmentType3 = new AppointmentType("Followup");

        // EP: same name
        assertEquals(appointmentType1, appointmentType2);

        // EP: different name
        assertNotEquals(appointmentType1, appointmentType3);

        // EP: null
        assertNotEquals(null, appointmentType1);

        // EP: non-AppointmentType object
        assertNotEquals("Checkup", appointmentType1);

        // EP: same object
        assertEquals(appointmentType1, appointmentType1);
    }

    @Test
    public void hashCode_compareIdenticalAndDifferentTypes_returnsConsistentHashes() {
        AppointmentType appointmentType1 = new AppointmentType("Checkup");
        AppointmentType appointmentType2 = new AppointmentType("Checkup");
        AppointmentType appointmentType3 = new AppointmentType("Followup");

        // EP: same name
        assertEquals(appointmentType1.hashCode(), appointmentType2.hashCode());

        // EP: different name
        assertNotEquals(appointmentType1.hashCode(), appointmentType3.hashCode());
    }
}
