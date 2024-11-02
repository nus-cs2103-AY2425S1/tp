package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Appointment;

public class OperatingHoursTest {
    private LocalTime openingHour = LocalTime.of(8, 30);
    private LocalTime closingHour = LocalTime.of(21, 30);
    private OperatingHours defaultOperatingHours = new OperatingHours(null, null);
    private OperatingHours differentOpeningOperatingHours = new OperatingHours(openingHour, null);
    private OperatingHours differentClosingOperatingHours = new OperatingHours(null, closingHour);
    private OperatingHours diffrerentOperatingHours = new OperatingHours(openingHour, closingHour);

    private Appointment earlyAppointment = new Appointment("11 August 2024 07:30");
    private Appointment lateAppointment = new Appointment("12 September 2024 21:16");
    private Appointment regularAppointment = new Appointment("13 October 2024 15:30");
    private Appointment firstAppointment = new Appointment("12 November 2024 08:30");
    private Appointment lastAppointment = new Appointment("12 November 2024 21:15");

    @Test
    public void constructor() {
        assertEquals(defaultOperatingHours, new OperatingHours(null, null));
        assertEquals(diffrerentOperatingHours, new OperatingHours(openingHour, closingHour));
        assertEquals(differentOpeningOperatingHours, new OperatingHours(openingHour, null));
        assertEquals(differentClosingOperatingHours, new OperatingHours(null, closingHour));

        assertEquals(defaultOperatingHours, new OperatingHours("00:00 to 23:59"));
        assertEquals(diffrerentOperatingHours, new OperatingHours("08:30 to 21:30"));
        assertThrows(IllegalArgumentException.class, () -> new OperatingHours("10:00"));
        assertThrows(IllegalArgumentException.class, () -> new OperatingHours("60:00 to 12:00"));
    }

    @Test
    public void isValid() {
        assertTrue(defaultOperatingHours.isWithinOperatingHours(earlyAppointment));
        assertTrue(differentClosingOperatingHours.isWithinOperatingHours(earlyAppointment));
        assertTrue(defaultOperatingHours.isWithinOperatingHours(lateAppointment));
        assertTrue(differentOpeningOperatingHours.isWithinOperatingHours(lateAppointment));
        assertTrue(diffrerentOperatingHours.isWithinOperatingHours(firstAppointment));

        assertFalse(differentOpeningOperatingHours.isWithinOperatingHours(earlyAppointment));
        assertFalse(differentClosingOperatingHours.isWithinOperatingHours(lateAppointment));
        assertFalse(diffrerentOperatingHours.isWithinOperatingHours(earlyAppointment));
        assertFalse(diffrerentOperatingHours.isWithinOperatingHours(lateAppointment));
        assertTrue(diffrerentOperatingHours.isWithinOperatingHours(lastAppointment));
    }

    @Test
    public void isCalenderValid() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(regularAppointment);
        appointments.add(earlyAppointment);
        appointments.add(lateAppointment);

        assertTrue(defaultOperatingHours.isCalenderValid(appointments));
        assertFalse(differentClosingOperatingHours.isCalenderValid(appointments));
        assertFalse(differentOpeningOperatingHours.isCalenderValid(appointments));
        assertFalse(diffrerentOperatingHours.isCalenderValid(appointments));
    }

    @Test
    public void isWithinOperatingHours() {
        OperatingHours badOperatingHours = new OperatingHours(LocalTime.of(0, 0),
                                                                LocalTime.of(0, 10));
        assertTrue(defaultOperatingHours.isWithinOperatingHours(regularAppointment));
        assertFalse(badOperatingHours.isWithinOperatingHours(new Appointment("10/10/2024 00:00")));
        assertTrue(diffrerentOperatingHours.isWithinOperatingHours(new Appointment("-")));
    }

    @Test
    public void equals() {
        assertTrue(defaultOperatingHours.equals(defaultOperatingHours));
        assertTrue(defaultOperatingHours.equals(new OperatingHours()));
        assertFalse(defaultOperatingHours.equals(2));

    }

}
