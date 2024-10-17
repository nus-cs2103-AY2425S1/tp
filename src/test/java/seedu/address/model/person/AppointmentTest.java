package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalPersons.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.tag.Tag;

public class AppointmentTest {

    private Appointment appointment;
    private Doctor doctor;
    private Doctor doctor2;
    private Patient patient;
    private Patient patient2;
    private LocalDateTime appointmentDate;
    private History history;

    @BeforeEach
    public void setUp() {
        // Intialise static database
        history = new History();

        patient = PATIENTALICE;
        doctor = DRBENSON;

        // Initialize the appointment date/time
        appointmentDate = LocalDateTime.of(2024, 10, 18, 10, 30);

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
        assertThrows(NullPointerException.class,
                () -> new Appointment(patient.getId(),null,"Monthly check-up"));
    }

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new Appointment(null, doctor.getId(), "Monthly check-up"));
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new Appointment(doctor.getId(), patient.getId(), null));
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
        assertEquals(appointment.toString(),"Appointment: " + appointment.getPatientId()
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
