package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class LogTest {

    @Test
    public void isValidLog_validLogEntry_returnsTrue() {
        assertTrue(Log.isValidLog("25-12-2024 14:30 Attended appointment"));
    }

    @Test
    public void isValidLog_invalidLogEntry_returnsFalse() {
        assertFalse(Log.isValidLog("invalid log entry"));
    }

    @Test
    public void isValidLog_emptyLogMessage_returnsFalse() {
        assertFalse(Log.isValidLog("25-12-2024 14:30 "));
    }

    @Test
    public void isValidLog_invalidTimestamp_returnsFalse() {
        assertFalse(Log.isValidLog("25-12-2024 25:30 Attended appointment"));
    }

    @Test
    public void constructor_validLogEntry_createsLog() {
        Log log = new Log("25-12-2024 14:30 Attended appointment");
        assertEquals("Attended appointment", log.getLogString());
        assertEquals(LocalDateTime.of(2024, 12, 25, 14, 30), log.getTimestamp());
    }

    @Test
    public void constructor_invalidLogEntry_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, () -> new Log("invalid log entry"));
    }

    @Test
    public void equals_sameLog_returnsTrue() {
        Log log1 = new Log("25-12-2024 14:30 Attended appointment");
        Log log2 = new Log("25-12-2024 14:30 Attended appointment");
        assertTrue(log1.equals(log2));
    }

    @Test
    public void equals_differentLog_returnsFalse() {
        Log log1 = new Log("25-12-2024 14:30 Attended appointment");
        Log log2 = new Log("26-12-2024 15:30 Attended appointment");
        assertFalse(log1.equals(log2));
    }

    @Test
    public void hashCode_sameLog_returnsSameHashCode() {
        Log log1 = new Log("25-12-2024 14:30 Attended appointment");
        Log log2 = new Log("25-12-2024 14:30 Attended appointment");
        assertEquals(log1.hashCode(), log2.hashCode());
    }

    @Test
    public void hashCode_differentLog_returnsDifferentHashCode() {
        Log log1 = new Log("25-12-2024 14:30 Attended appointment");
        Log log2 = new Log("26-12-2024 15:30 Attended appointment");
        assertNotEquals(log1.hashCode(), log2.hashCode());
    }
}
