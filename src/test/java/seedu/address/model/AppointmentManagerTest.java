package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

public class AppointmentManagerTest {

    private AppointmentManager appointmentManager;
    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
        appointmentManager = modelManager.getAppointmentManager(); // Get singleton instance
        appointmentManager.reset(); // Reset appointments to ensure clean state for each test
    }

    @Test
    public void addAppointment_noConflict_success() {
        Person testPerson = new Person(ALICE);
        modelManager.addPerson(testPerson);

        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = startTime.plusHours(1);
        appointmentManager.reset();
        Appointment appointment = new Appointment("Appointment 1", testPerson.getNric(), startTime, endTime);

        boolean result = appointmentManager.addAppointment(appointment, testPerson);
        assertTrue(result);
    }

    @Test
    public void addAppointment_conflictingAppointment_throwsIllegalArgumentException() {
        Person testPerson = new Person(BENSON);
        modelManager.addPerson(testPerson);

        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = startTime.plusHours(1);
        Appointment appointment1 = new Appointment("Appointment 1", testPerson.getNric(), startTime, endTime, false);
        appointmentManager.addAppointment(appointment1, testPerson);
        // Conflicting appointment with overlapping time
        LocalDateTime startTime2 = startTime.plusMinutes(30);
        Appointment appointment2 = new Appointment("Appointment 2", testPerson.getNric(),
            startTime2, endTime.plusMinutes(30), false);

        boolean result = appointmentManager.addAppointment(appointment2, testPerson);
        assertFalse(result);
    }

    @Test
    public void removeAppointment_existingAppointment_success() {
        Person testPerson = new Person(BENSON);
        modelManager.addPerson(testPerson); // Add person to ModelManager

        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = startTime.plusHours(1);
        Appointment appointment = new Appointment("Appointment 1", testPerson.getNric(), startTime, endTime, false);

        appointmentManager.addAppointment(appointment, testPerson); // Add appointment
        assertEquals(1, modelManager.getAppointmentsForPerson(testPerson).size()); // Confirm appointment added

        appointmentManager.removeAppointment(appointment, testPerson); // Remove appointment
        assertEquals(0, modelManager.getAppointmentsForPerson(testPerson).size()); // Confirm appointment removed
    }

    @Test
    public void getAppointments_noAppointments_returnsEmptyList() {
        Person testPerson = new Person(ALICE);
        modelManager.addPerson(testPerson); // Add person to ModelManager

        List<Appointment> appointments = appointmentManager.getAppointments();
        assertTrue(appointments.isEmpty());
    }

    @Test
    public void getAppointments_appointmentsPresent_returnsSortedAppointments() {
        appointmentManager.reset();
        Person testPerson = new Person(ALICE);
        modelManager.addPerson(testPerson);

        LocalDateTime now = LocalDateTime.of(2024, 10, 22, 12, 0);
        Appointment appointment1 = new Appointment("Appointment 1", testPerson.getNric(),
            now, now.plusHours(1), false);
        Appointment appointment2 = new Appointment("Appointment 2", testPerson.getNric(),
            now.plusHours(2), now.plusHours(3), false);

        appointmentManager.addAppointment(appointment1, testPerson); // Add appointments
        appointmentManager.addAppointment(appointment2, testPerson);

        List<Appointment> appointmentsList2 = appointmentManager.getAppointments();
        assertEquals(2, appointmentsList2.size());
        assertEquals(appointment1, appointmentsList2.get(0));
        assertEquals(appointment2, appointmentsList2.get(1));
    }

    @Test
    public void reset_resetsAppointmentList() {
        System.out.println("previous: " + appointmentManager.getAppointments());
        Person testPerson = new Person(ALICE);
        modelManager.addPerson(testPerson); // Add person to ModelManager

        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = startTime.plusHours(1);
        Appointment appointment = new Appointment("Appointment 1", testPerson.getNric(), startTime, endTime, false);

        appointmentManager.addAppointment(appointment, testPerson); // Add appointment
        System.out.println("current: " + appointmentManager.getAppointments());
        assertEquals(1, appointmentManager.getAppointments().size());

        appointmentManager.reset();

        System.out.println("after reset: " + appointmentManager.getAppointments());
        assertEquals(0, appointmentManager.getAppointments().size()); // List should be empty after reset
    }

    @Test
    public void checkForConflicts_appointmentsWithConflict_returnsFalse() {
        appointmentManager.reset();
        Person testPerson = new Person(ALICE);
        modelManager.addPerson(testPerson); // Add person to ModelManager

        LocalDateTime startTime1 = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime1 = startTime1.plusHours(1);
        Appointment appointment1 = new Appointment("Appointment 1", testPerson.getNric(), startTime1, endTime1, false);

        LocalDateTime startTime2 = startTime1.plusMinutes(3); // Overlapping with appointment 1
        LocalDateTime endTime2 = startTime2.plusHours(1);
        Appointment appointment2 = new Appointment("Appointment 2", testPerson.getNric(), startTime2, endTime2, false);


        appointmentManager.addAppointment(appointment1, testPerson);


        boolean added = appointmentManager.addAppointment(appointment2, testPerson);

        assertFalse(added);

    }
}
