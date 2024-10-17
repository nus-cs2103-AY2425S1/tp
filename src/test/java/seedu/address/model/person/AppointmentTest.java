package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.DRALICE;
import static seedu.address.testutil.TypicalPersons.DRBENSON;
import static seedu.address.testutil.TypicalPersons.DRELLE;
import static seedu.address.testutil.TypicalPersons.PATIENTALICE;
import static seedu.address.testutil.TypicalPersons.PATIENTBENSON;
import static seedu.address.testutil.TypicalPersons.PATIENTCARL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentTest {

    private Appointment appointment;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = PATIENTALICE;
        doctor = DRBENSON;

        // Create an Appointment object
        appointment = new Appointment(patient.getId(), doctor.getId(), "Monthly check-up");
    }

    @Test
    public void constructor_initializesCorrectly() {
        assertNotNull(appointment);
        assertEquals(doctor.getId(), appointment.getDoctorId());
        assertEquals(patient.getId(), appointment.getPatientId());
        assertEquals("Monthly check-up", appointment.getRemarks());
    }

    @Test
    public void constructor_nullDoctor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Appointment(patient.getId(), null, "Monthly check-up"));
    }

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Appointment(null, doctor.getId(), "Monthly check-up"));
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Appointment(doctor.getId(), patient.getId(), null));
    }

    @Test
    public void equals() {
        Patient patientCopy = PATIENTALICE;
        Doctor doctorCopy = DRBENSON;
        Appointment appointmentCopy = new Appointment(patientCopy.getId(), doctorCopy.getId(),
                "Monthly check-up");
        assertEquals(appointment, appointmentCopy);

        assertEquals(appointment.getDoctorId(), appointmentCopy.getDoctorId());
        assertEquals(appointment.getPatientId(), appointmentCopy.getPatientId());
        assertEquals(appointment.getRemarks(), appointmentCopy.getRemarks());

        Patient newPatient = PATIENTCARL;
        Doctor newDoctor = DRELLE;
        Appointment newAppointment = new Appointment(newPatient.getId(), newDoctor.getId(),
                "Monthly check-up");
        assertFalse(appointment.equals(newAppointment));
        assertFalse(newAppointment.equals(appointment));

        Appointment newAppointmentRemark = new Appointment(patient.getId(), doctor.getId(),
                "Monthly check-up ");
        assertFalse(appointment.equals(newAppointmentRemark));
        assertFalse(newAppointmentRemark.equals(appointment));

        Patient evilPatient = PATIENTBENSON;
        Doctor evilDoctor = DRALICE;
        Appointment newEvilAppointment = new Appointment(evilPatient.getId(), evilDoctor.getId(),
                "Monthly check-up");
        assertFalse(appointment.equals(newEvilAppointment));
        assertFalse(newEvilAppointment.equals(appointment));

        Appointment newEvilAppointment1 = new Appointment(evilDoctor.getId(), evilPatient.getId(),
                "Monthly check-up");
        assertFalse(appointment.equals(newEvilAppointment1));
        assertFalse(newEvilAppointment1.equals(appointment));
    }

    @Test
    public void toAString() {
        assertEquals(appointment.toString(), "Appointment: " + appointment.getPatientId()
                + " (patient id) with " + appointment.getDoctorId() + " (doctor id). "
                + "Remarks: " + appointment.getRemarks());
    }

    @Test
    public void hashCode_hashesCorrectly() {
        Patient patientCopy = PATIENTALICE;
        Doctor doctorCopy = DRBENSON;
        Appointment appointmentCopy = new Appointment(patientCopy.getId(), doctorCopy.getId(),
                "Monthly check-up");

        assertEquals(appointment.hashCode(), appointmentCopy.hashCode());
    }
}
