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
        Appointment testAppointment = new Appointment("test", testPerson.getNric(), startTime, endTime);
        testPerson.addAppointment(testAppointment);

        assertEquals(1, testPerson.getAppointments().size());
        assertTrue(testPerson.getAppointments().contains(testAppointment));

    }

    @Test
    public void appointmentRemoveTest() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now();
        Appointment testAppointment = new Appointment("test", testPerson.getNric(), startTime, endTime);
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
            new Appointment("test", testPerson.getNric(), fixedTime, fixedTime)
        );
    }
    @Test
    public void invalidAppointmentEndTimeBeforeStartTime() {
        Person testPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusHours(1);
        InvalidAppointmentException e = assertThrows(InvalidAppointmentException.class, () ->
        new Appointment("test", testPerson.getNric(), startTime, endTime));
        assertEquals(Appointment.INVALID_APPOINTMENT_ERROR, e.getMessage());
    }
}
