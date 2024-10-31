package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.ENGLISH_FORMAT_WITH_TIME;
import static seedu.address.model.person.Appointment.TODAY;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointment = "21/10 11:20";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }

    @Test
    public void isValidAppointment() {
        // null appointment
        assertThrows(NullPointerException.class, () -> Appointment.isValidAppointment(null));

        // invalid appointment
        assertFalse(Appointment.isValidAppointment("")); // empty string
        assertFalse(Appointment.isValidAppointment(" ")); // spaces only

        // valid appointments
        assertTrue(Appointment.isValidAppointment("01/01/2024 08:30"));
        assertTrue(Appointment.isValidAppointment("12/12/2100 00:01"));
        assertTrue(Appointment.isValidAppointment("01/01/2024 23:59"));
        assertTrue(Appointment.isValidAppointment("01/01/2023 02:20"));
        assertTrue(Appointment.isValidAppointment("01/01/2024 21:30"));
        assertTrue(Appointment.isValidAppointment("01-01-2024 08:30"));
        assertTrue(Appointment.isValidAppointment("01 01 2024 21:30"));
        assertTrue(Appointment.isValidAppointment("01 January 2024 08:30"));
        assertTrue(Appointment.isValidAppointment("-"));
    }

    @Test
    public void getEndTime_sameAppointments_isEqual() {
        Appointment a = new Appointment("12/10/2023 10:30");
        Appointment b = new Appointment("12/10/2023 10:30");
        Appointment c = new Appointment(null);
        Appointment d = new Appointment("-");

        assertEquals(a.getEndTime(), b.getEndTime());
        assertEquals(c.getEndTime(), d.getEndTime());
    }

    @Test
    public void hasPassed() {
        assertTrue(new Appointment("01/01/2024 10:30").hasPassed());
        assertFalse(new Appointment("12/12/2100 23:59").hasPassed());
        assertFalse(new Appointment("-").hasPassed());
    }

    @Test
    public void hasNotPassed() {
        assertTrue(new Appointment("12/12/2100 23:50").hasNotPassed());
        assertFalse(new Appointment("01/01/2024 10:30").hasNotPassed());
        assertFalse(new Appointment("-").hasNotPassed());
    }

    @Test
    public void isOn() {
        Appointment validAppointment = new Appointment("12/12/2024 12:30");
        Appointment noAppointment = new Appointment("-");
        assertTrue(validAppointment.isOn(LocalDate.of(2024, 12, 12)));
        assertFalse(validAppointment.isOn(LocalDate.of(2024, 12, 11)));
        assertFalse(noAppointment.isOn(LocalDate.of(2024, 12, 11)));

    }

    @Test
    public void isToday() {
        Appointment validAppointment = new Appointment("12/12/2021 12:30");
        Appointment todayAppointment = new Appointment(TODAY.atTime(10, 30)
                                                            .format(ENGLISH_FORMAT_WITH_TIME));
        assertTrue(todayAppointment.isToday());
        assertFalse(new Appointment("-").isToday());
        assertFalse(validAppointment.isToday());
    }

    @Test
    public void equals() {
        Appointment validAppointment = new Appointment("12/12/2024 12:30");

        // Same values
        assertTrue(validAppointment.equals(new Appointment("12/12/2024 12:30")));

        // Same object
        assertTrue(validAppointment.equals(validAppointment));

        // Same null values
        assertTrue(new Appointment(null).equals(new Appointment(null)));

        assertTrue(new Appointment("-").equals(new Appointment("-")));

        assertTrue(new Appointment("-").equals(new Appointment(null)));

        // different types
        assertFalse(validAppointment.equals(new Appointment(null)));

        // different times same date
        assertFalse(validAppointment.equals(new Appointment("12/12/2024 16:30")));
    }

}
