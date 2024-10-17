package seedu.address.model.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class LogTest {
    private final LocalDate testDate = LocalDate.of(2024, 10, 17);
    private final String testEntry = "This is a test log entry for the appointment.";
    @Test
    public void constructor_nullEntry_throwsNullPointerException() {
        AppointmentDate validDate = new AppointmentDate("31 Dec 2024");
        assertThrows(NullPointerException.class, () -> new Log(validDate, null));
    }

    @Test
    public void constructor_nullAppointmentDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Log(null, "Valid entry"));
    }

    @Test
    public void constructor_invalidEntry_throwsIllegalArgumentException() {
        AppointmentDate validDate = new AppointmentDate("31 Dec 2024");
        String invalidEntry = ""; // Invalid log entry
        assertThrows(IllegalArgumentException.class, () -> new Log(validDate, invalidEntry));
    }

    @Test
    public void constructor_validEntry_createsLog() {
        AppointmentDate validDate = new AppointmentDate("31 Dec 2024");
        String validEntry = "This is a valid log entry.";
        Log log = new Log(validDate, validEntry);

        assert log.getEntry().equals(validEntry);
        assert log.getAppointmentDate().equals(validDate);
    }

    @Test
    public void fromStorageString_invalidFormat_throwsIllegalArgumentException() {
        String invalidFormat = "InvalidFormatString";
        assertThrows(IllegalArgumentException.class, () -> Log.fromStorageString(invalidFormat));
    }

    @Test
    public void fromStorageString_validFormat_createsLog() {
        String validStorageString = "31 Dec 2024|This is a valid log entry.";
        Log log = Log.fromStorageString(validStorageString);

        assert log.getEntry().equals("This is a valid log entry.");
        assert log.getAppointmentDate().getDate().equals(LocalDate.of(2024, 12, 31));
    }

    @Test
    public void equals_differentLog_returnsFalse() {
        AppointmentDate date1 = new AppointmentDate("31 Dec 2024");
        AppointmentDate date2 = new AppointmentDate("01 Jan 2025");
        Log log1 = new Log(date1, "Entry 1");
        Log log2 = new Log(date2, "Entry 1");

        assert !log1.equals(log2);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        Log log = new Log(new AppointmentDate(testDate), testEntry);
        assertTrue(log.equals(log));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        Log log = new Log(new AppointmentDate(testDate), testEntry);
        String notALog = "Not a Log instance";
        assertFalse(log.equals(notALog));
    }

    @Test
    public void equals_sameLog_returnsTrue() {
        Log log1 = new Log(new AppointmentDate(testDate), testEntry);
        Log log2 = new Log(new AppointmentDate(testDate), testEntry);
        assertTrue(log1.equals(log2));
    }

    @Test
    public void equals_differentEntry_returnsFalse() {
        Log log1 = new Log(new AppointmentDate(testDate), testEntry);
        Log log2 = new Log(new AppointmentDate(testDate), "Different entry");
        assertFalse(log1.equals(log2));
    }

    @Test
    public void equals_differentDate_returnsFalse() {
        Log log1 = new Log(new AppointmentDate(testDate), testEntry);
        Log log2 = new Log(new AppointmentDate(LocalDate.of(2024, 10, 18)), testEntry);
        assertFalse(log1.equals(log2));
    }

    @Test
    public void toString_shortEntry_returnsCorrectString() {
        String shortEntry = "Short entry.";
        Log log = new Log(new AppointmentDate(testDate), shortEntry);
        String expectedString = "Log{Appointment Date=17 Oct 2024, Entry=Short entry.}";
        assertEquals(expectedString, log.toString());
    }

    @Test
    public void toString_longEntry_returnsTruncatedString() {
        String longEntry = "This entry is longer than 100 characters. "
                + "It should be truncated when the toString method is called, "
                + "ensuring only the first 100 characters are shown.";
        Log log = new Log(new AppointmentDate(testDate), longEntry);
        String expectedString = "Log{Appointment Date=17 Oct 2024, Entry=This entry is longer than 100 characters. "
                + "It should be truncated when the toString method is called,...}";
        assertEquals(expectedString, log.toString());
    }

    @Test
    public void toDetailedString_returnsCorrectDetailedString() {
        Log log = new Log(new AppointmentDate(testDate), testEntry);
        String expectedDetailedString = "Appointment Date: 17 Oct 2024\n"
                + "Entry: This is a test log entry for the appointment.";
        assertEquals(expectedDetailedString, log.toDetailedString());
    }
}
