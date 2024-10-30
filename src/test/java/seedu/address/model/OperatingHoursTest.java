package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    private Appointment lateAppointment = new Appointment("12 September 2024 22:30");
    private Appointment regularAppointment = new Appointment("13 October 2024 15:30");

    @Test
    public void constructor() {
        assertEquals(defaultOperatingHours, new OperatingHours(null, null));
        assertEquals(diffrerentOperatingHours, new OperatingHours(openingHour, closingHour));
        assertEquals(differentOpeningOperatingHours, new OperatingHours(openingHour, null));
        assertEquals(differentClosingOperatingHours, new OperatingHours(null, closingHour));
    }

    @Test
    public void isValid() {
        assertTrue(defaultOperatingHours.isWithinOperatingHours(earlyAppointment));
        assertTrue(differentClosingOperatingHours.isWithinOperatingHours(earlyAppointment));
        assertTrue(defaultOperatingHours.isWithinOperatingHours(lateAppointment));
        assertTrue(differentOpeningOperatingHours.isWithinOperatingHours(lateAppointment));

        assertFalse(differentOpeningOperatingHours.isWithinOperatingHours(earlyAppointment));
        assertFalse(differentClosingOperatingHours.isWithinOperatingHours(lateAppointment));
        assertFalse(diffrerentOperatingHours.isWithinOperatingHours(earlyAppointment));
        assertFalse(diffrerentOperatingHours.isWithinOperatingHours(lateAppointment));
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
    public void equals() {
        assertTrue(defaultOperatingHours.equals(defaultOperatingHours));
        assertFalse(defaultOperatingHours.equals(2));

    }

}
