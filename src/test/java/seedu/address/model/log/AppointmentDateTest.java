package seedu.address.model.log;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;


public class AppointmentDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentDate((LocalDate) null));
    }

    @Test
    public void constructor_invalidDateString_throwsIllegalArgumentException() {
        String invalidDateString = "31-02-2024"; // Invalid date format
        assertThrows(IllegalArgumentException.class, () -> new AppointmentDate(invalidDateString));
    }

    @Test
    public void constructor_validDateString_createsAppointmentDate() {
        String validDateString = "31 Dec 2024";
        AppointmentDate appointmentDate = new AppointmentDate(validDateString);
        assert appointmentDate.getDate().equals(LocalDate.of(2024, 12, 31));
    }

    @Test
    public void isValidDateString() {
        assertThrows(NullPointerException.class, () -> AppointmentDate.isValidDateString(null));
        assert AppointmentDate.isValidDateString("31 Dec 2024");
        assert !AppointmentDate.isValidDateString("31 2024");
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        AppointmentDate appointmentDate = new AppointmentDate(LocalDate.of(2024, 10, 17));
        assertTrue(appointmentDate.equals(appointmentDate));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        AppointmentDate appointmentDate = new AppointmentDate(LocalDate.of(2024, 10, 17));
        String notAnAppointmentDate = "Not an AppointmentDate";
        assertFalse(appointmentDate.equals(notAnAppointmentDate));
    }

    @Test
    public void equals_sameDate_returnsTrue() {
        AppointmentDate appointmentDate1 = new AppointmentDate(LocalDate.of(2024, 10, 17));
        AppointmentDate appointmentDate2 = new AppointmentDate(LocalDate.of(2024, 10, 17));
        assertTrue(appointmentDate1.equals(appointmentDate2));
    }

    @Test
    public void equals_differentDate_returnsFalse() {
        AppointmentDate appointmentDate1 = new AppointmentDate(LocalDate.of(2024, 10, 17));
        AppointmentDate appointmentDate2 = new AppointmentDate(LocalDate.of(2024, 10, 18));
        assertFalse(appointmentDate1.equals(appointmentDate2));
    }
}
