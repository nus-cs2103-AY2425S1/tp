package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalDoctors.ALICE;
import static seedu.address.testutil.TypicalDoctors.BENSON;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.DANIEL;

import org.junit.jupiter.api.Test;

import seedu.address.model.patient.DateOfBirth;

public class AppointmentTest {

    public static final Appointment APPOINTMENT_A = new Appointment(
        ALICE, CARL, new DateOfBirth("11-02-2024"), new Time("2359")
    );

    public static final Appointment APPOINTMENT_B = new Appointment(
        BENSON, DANIEL, new DateOfBirth("12-02-2024"), new Time("0900")
    );

    @Test
    public void deleteAppointmentById() {
        // Create new appointment
        Appointment appointment = new Appointment(
            ALICE, DANIEL, new DateOfBirth("12-02-2024"), new Time("0900")
        );

        // Deleting an existing appointment returns true
        Integer id = appointment.getId();
        assertTrue(Appointment.deleteAppointmentById(id));

        // Deleting a non-existent appointment returns false
        assertFalse(Appointment.deleteAppointmentById(id));
    }

    @Test
    public void isSameAppointment() {
        // same object -> returns true
        assertTrue(APPOINTMENT_A.isSameAppointment(APPOINTMENT_A));

        // null -> returns false
        assertFalse(APPOINTMENT_A.isSameAppointment(null));

        // different appointment -> returns false
        assertFalse(APPOINTMENT_A.isSameAppointment(APPOINTMENT_B));
    }
    @Test
    public void getAppointmentById_invalidId_returnsNull() {
        // Attempt to retrieve an appointment using a non-existing ID
        Appointment retrievedAppointment = Appointment.getAppointmentById(999);

        // Check that the result is null
        assertNull(retrievedAppointment);
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(APPOINTMENT_A.equals(APPOINTMENT_A));

        // null -> returns false
        assertFalse(APPOINTMENT_A.equals(null));

        // different type -> returns false
        assertFalse(APPOINTMENT_A.equals(5));

        // different doctor -> returns false
        assertFalse(APPOINTMENT_A.equals(APPOINTMENT_B));
    }

    @Test
    public void toStringMethod() {
        String expected = Appointment.class.getCanonicalName() + "{id=" + APPOINTMENT_A.getId()
                + ", doctor=" + APPOINTMENT_A.getDoctor() + ", patient=" + APPOINTMENT_A.getPatient()
                + ", date=" + APPOINTMENT_A.getDate() + ", time=" + APPOINTMENT_A.getTime()
                + "}";
        assertEquals(expected, APPOINTMENT_A.toString());
    }
}
