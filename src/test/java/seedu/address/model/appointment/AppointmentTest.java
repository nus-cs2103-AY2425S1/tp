package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.InvalidAppointmentException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AppointmentTest {

    @Test
    public void appointmentAddTest() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 12, 0).plusHours(1);
        Appointment testAppointment = new Appointment("test", testPerson.getNric(), startTime, endTime, false);
        testPerson.addAppointment(testAppointment);

        assertEquals(1, testPerson.getAppointments().size());
        assertTrue(testPerson.getAppointments().contains(testAppointment));
    }

    @Test
    public void appointmentRemoveTest() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 12, 0).plusHours(1);
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
        assertThrows(InvalidAppointmentException.class, () ->
            new Appointment("test", testPerson.getNric(), fixedTime, fixedTime)
        );

    }

    @Test
    public void invalidAppointmentEndTimeBeforeStartTime() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 21, 12, 0);
        LocalDateTime endTime = startTime.minusHours(1);
        InvalidAppointmentException e = assertThrows(InvalidAppointmentException.class, () ->
            new Appointment("test", testPerson.getNric(), startTime, endTime, false)
        );
        InvalidAppointmentException f = assertThrows(InvalidAppointmentException.class, () ->
            new Appointment("test", testPerson.getNric(), startTime, endTime)
        );
        assertEquals(Appointment.INVALID_APPOINTMENT_ERROR, e.getMessage());
        assertEquals(Appointment.INVALID_APPOINTMENT_ERROR, f.getMessage());
    }

    @Test
    public void appointmentWithCompletionStatus() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 12, 0).plusHours(1);

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
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 12, 0).plusHours(1);

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
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 12, 0).plusHours(1);

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
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 12, 0).plusHours(1);

        // Create two appointments with the same attributes
        Appointment appointment1 = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, false);
        Appointment appointment2 = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, false);

        assertTrue(appointment1.equals(appointment2));

        // Create an appointment with different attributes
        Appointment differentAppointment = new Appointment("Different Appointment",
            testPerson.getNric(), startTime, endTime, false);

        assertFalse(appointment1.equals(differentAppointment));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        // Create a test appointment
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 13, 0);
        Appointment appointment = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, false);

        // Test that an appointment equals itself
        assertTrue(appointment.equals(appointment));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        // Create a test appointment
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 13, 0);
        Appointment appointment = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, false);

        // Test that comparing an appointment to an unrelated object (like a String) returns false
        assertFalse(appointment.equals("not an appointment"));
    }
    @Test
    public void equals_differentNric_returnsFalse() {
        Person testPerson1 = new PersonBuilder().withNric(BENSON.getNric().toString()).build();
        Person testPerson2 = new PersonBuilder().withNric(ALICE.getNric().toString()).build();

        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 13, 0);

        Appointment appointment1 = new Appointment("Test Appointment", testPerson1.getNric(),
            startTime, endTime, false);
        Appointment appointment2 = new Appointment("Test Appointment", testPerson2.getNric(),
            startTime, endTime, false);

        // Test that appointments with different NRICs are not equal
        assertFalse(appointment1.equals(appointment2));
    }

    @Test
    public void equals_differentStartTime_returnsFalse() {
        Person testPerson = new PersonBuilder().build();

        LocalDateTime startTime1 = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime startTime2 = startTime1.plusHours(1); // Different start time
        LocalDateTime endTime = startTime1.plusHours(2);

        Appointment appointment1 = new Appointment("Test Appointment", testPerson.getNric(),
            startTime1, endTime, false);
        Appointment appointment2 = new Appointment("Test Appointment", testPerson.getNric(),
            startTime2, endTime, false);

        // Test that appointments with different start times are not equal
        assertFalse(appointment1.equals(appointment2));
    }

    @Test
    public void equals_differentEndTime_returnsFalse() {
        Person testPerson = new PersonBuilder().build();

        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime1 = startTime.plusHours(1);
        LocalDateTime endTime2 = startTime.plusHours(2);

        Appointment appointment1 = new Appointment("Test Appointment", testPerson.getNric(),
            startTime, endTime1, false);
        Appointment appointment2 = new Appointment("Test Appointment", testPerson.getNric(),
            startTime, endTime2, false);

        // Test that appointments with different end times are not equal
        assertFalse(appointment1.equals(appointment2));
    }
    @Test
    public void getAppointmentDetailsTest() {
        // Prepare the test data
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 13, 0);

        // Create an appointment with the completion status as false
        Appointment appointment = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, false);

        // Expected formatted details
        String expectedDetails = "From: 22 October 2024 12:00HRS To: 22 October 2024 13:00HRS Status: Not Completed";

        // Assert that the details are as expected
        assertEquals(expectedDetails, appointment.getAppointmentDetails());
    }

    @Test
    public void getAppointmentDetailsCompletedTest() {
        // Prepare the test data
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 22, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 22, 13, 0);

        // Create an appointment with the completion status as true
        Appointment appointment = new Appointment("Test Appointment", testPerson.getNric(), startTime, endTime, true);

        // Expected formatted details with completed status
        String expectedDetails = "From: 22 October 2024 12:00HRS To: 22 October 2024 13:00HRS Status: Completed";

        // Assert that the details are as expected
        assertEquals(expectedDetails, appointment.getAppointmentDetails());
    }






}
