package seedu.address.model.log;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LogEntryTest {
    private final String testEntry = "This is a test log entry for the appointment.";

    @Test
    public void constructor_invalidEntry_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new LogEntry("")); // Invalid log entry
    }

    @Test
    public void constructor_validEntry_createsLogEntry() {
        LogEntry validEntry = new LogEntry("This is a valid log entry.");

        assertTrue(validEntry.getEntry().equals("This is a valid log entry."));
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        LogEntry entry = new LogEntry("This is a valid log entry.");

        assertTrue(entry.equals(entry));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        LogEntry entry = new LogEntry("This is a valid log entry.");
        String notALogEntry = "Not a LogEntry";

        assertFalse(entry.equals(notALogEntry));
    }

    @Test
    public void equals_sameDate_returnsTrue() {
        LogEntry entry1 = new LogEntry("This is a valid log entry.");
        LogEntry entry2 = new LogEntry("This is a valid log entry.");

        assertTrue(entry1.equals(entry2));
    }

    @Test
    public void equals_differentLogEntry_returnsFalse() {
        LogEntry entry1 = new LogEntry("This is a valid log entry.");
        LogEntry entry2 = new LogEntry("This is a different log entry.");

        assertNotEquals(entry1, entry2);
    }
}
