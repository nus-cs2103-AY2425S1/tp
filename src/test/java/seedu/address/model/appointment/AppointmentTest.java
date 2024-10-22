package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.InvalidAppointmentException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AppointmentTest {

    @Test
    public void appointmentAddTest() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        Appointment testAppointment = new Appointment("test", testPerson.getNric(), startTime, endTime, false);
        testPerson.addAppointment(testAppointment);

        assertEquals(1, testPerson.getAppointments().size());
        assertTrue(testPerson.getAppointments().contains(testAppointment));
    }

    @Test
    public void appointmentRemoveTest() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        Appointment testAppointment = new Appointment("test", testPerson.getNric(), startTime, endTime, false);
        testPerson.addAppointment(testAppointment);
        testPerson.removeAppointment(testAppointment);

        assertEquals(0, testPerson.getAppointments().size());
        assertFalse(testPerson.getAppointments().contains(testAppointment));
    }

    @Test
    public void invalidAppointmentSameTime() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime fixedTime = LocalDateTime.of(2024, 10, 21, 12, 0);
        assertThrows(InvalidAppointmentException.class, () ->
            new Appointment("test", testPerson.getNric(), fixedTime, fixedTime, false)
        );
    }

    @Test
    public void invalidAppointmentEndTimeBeforeStartTime() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusHours(1);
        InvalidAppointmentException e = assertThrows(InvalidAppointmentException.class, () ->
            new Appointment("test", testPerson.getNric(), startTime, endTime, false)
        );
        assertEquals(Appointment.INVALID_APPOINTMENT_ERROR, e.getMessage());
    }

    @Test
    public void appointmentWithCompletionStatus() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        // Create an appointment with the completed flag set to true
        Appointment completedAppointment = new Appointment("Completed Appointment",
            testPerson.getNric(), startTime, endTime, true);

        assertTrue(completedAppointment.isCompleted());

        // Create an appointment with the completed flag set to false
        Appointment incompleteAppointment = new Appointment("Incomplete Appointment",
            testPerson.getNric(), startTime, endTime, false);

        assertFalse(incompleteAppointment.isCompleted());
    }

    @Test
    public void markAsCompletedTest() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        // Create an appointment with the completed flag set to false
        Appointment appointment = new Appointment("Incomplete Appointment", testPerson.getNric(),
            startTime, endTime, false);
        assertFalse(appointment.isCompleted());

        // Mark as completed and check again
        appointment.markAsCompleted();
        assertTrue(appointment.isCompleted());
    }

    @Test
    public void toStringTest() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        // Create an appointment
        Appointment appointment = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, false);

        // Expected string representation
        String expectedString = "Appointment: Test Appointment\n"
                                + "Nric: *****" + testPerson.getNric().toString().substring(5) + "\n"
                                + "Start Time: " + startTime + "\n"
                                + "End Time: " + endTime + "\n";

        assertEquals(expectedString, appointment.toString());
    }

    @Test
    public void equalsTest() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        // Create two appointments with the same attributes
        Appointment appointment1 = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, false);
        Appointment appointment2 = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, false);

        assertTrue(appointment1.equals(appointment2));

        // Create an appointment with different attributes
        Appointment differentAppointment = new Appointment("Different Appointment",
            testPerson.getNric(), startTime, endTime, false);

        assertFalse(appointment1.equals(differentAppointment));
    }
}
