package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

public class AppointmentTest {

    private final Name name = new Name("Alex Yeoh");
    private final LocalDate date = LocalDate.of(2024, 10, 30);

    private final Appointment appointment =
            new Appointment(name, date, LocalTime.of(10, 0), LocalTime.of(11, 0));
    private final Appointment conflictingAppointment =
            new Appointment(name, date, LocalTime.of(10, 30), LocalTime.of(12, 30));
    private final Appointment nonConflictingAppointment =
            new Appointment(name, date, LocalTime.of(13, 0), LocalTime.of(14, 0));

    @Test
    public void constructor_invalidTimeInterval_throwsIllegalArgumentException() {
        LocalTime startTime = LocalTime.of(16, 0);
        LocalTime endTime = LocalTime.of(14, 0);

        assertThrows(IllegalArgumentException.class, () -> new Appointment(name, date, startTime, endTime));
    }

    @Test
    public void isValidTimeInterval() {
        LocalTime starTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        assertTrue(Appointment.isValidTimeInterval(starTime, endTime));
        assertFalse(Appointment.isValidTimeInterval(starTime, starTime));
        assertFalse(Appointment.isValidTimeInterval(endTime, starTime));
    }

    @Test
    public void getFormattedDate() {
        assertEquals("Wednesday, October 30, 2024", appointment.getFormattedDate());
    }

    @Test
    public void getFormattedStartTime() {
        assertEquals("10:00 AM", appointment.getFormattedStartTime());
        assertEquals("10:30 AM", conflictingAppointment.getFormattedStartTime());
        assertEquals("1:00 PM", nonConflictingAppointment.getFormattedStartTime());
    }

    @Test
    public void getFormattedEndTime() {
        assertEquals("11:00 AM", appointment.getFormattedEndTime());
        assertEquals("12:30 PM", conflictingAppointment.getFormattedEndTime());
        assertEquals("2:00 PM", nonConflictingAppointment.getFormattedEndTime());
    }

    @Test
    public void hasConflictWith() {
        assertTrue(appointment.hasConflictWith(conflictingAppointment));
        assertFalse(appointment.hasConflictWith(nonConflictingAppointment));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different type -> returns false
        assertFalse(appointment.equals(5));

        // different appointment -> returns false
        assertFalse(appointment.equals(conflictingAppointment));
    }

    @Test
    public void toStringMethod() {
        String expected = Appointment.class.getCanonicalName() + "{name="
                + appointment.name() + ", date="
                + appointment.date() + ", startTime="
                + appointment.startTime() + ", endTime="
                + appointment.endTime() + "}";
        assertEquals(expected, appointment.toString());
    }
}
