package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class LogListTest {

    @Test
    public void addLog_validLog_addsLogSuccessfully() {
        LogList logList = new LogList();
        Log log = new Log("25-12-2024 14:30 Attended appointment");
        LogList updatedLogList = logList.addLog(log);
        assertTrue(updatedLogList.getLogs().contains(log));
    }

    @Test
    public void getLogs_emptyLogList_returnsEmptyList() {
        LogList logList = new LogList();
        assertTrue(logList.getLogs().isEmpty());
    }

    @Test
    public void getLogs_nonEmptyLogList_returnsUnmodifiableList() {
        Log log = new Log("25-12-2024 14:30 Attended appointment");
        LogList logList = new LogList(List.of("25-12-2024 14:30 Attended appointment"));
        List<Log> logs = logList.getLogs();
        assertThrows(UnsupportedOperationException.class, () -> logs.add(log));
    }

    @Test
    public void toString_nonEmptyLogList_returnsCorrectString() {
        LogList logList = new LogList(List.of("25-12-2024 14:30 Attended appointment"));
        assertEquals("25-12-2024 14:30 Attended appointment", logList.toString());
    }

    @Test
    public void equals_sameLogList_returnsTrue() {
        LogList logList1 = new LogList(List.of("25-12-2024 14:30 Attended appointment"));
        LogList logList2 = new LogList(List.of("25-12-2024 14:30 Attended appointment"));
        assertTrue(logList1.equals(logList2));
    }

    @Test
    public void equals_differentLogList_returnsFalse() {
        LogList logList1 = new LogList(List.of("25-12-2024 14:30 Attended appointment"));
        LogList logList2 = new LogList(List.of("26-12-2024 15:30 Attended appointment"));
        assertFalse(logList1.equals(logList2));
    }

    @Test
    public void hashCode_sameLogList_returnsSameHashCode() {
        LogList logList1 = new LogList(List.of("25-12-2024 14:30 Attended appointment"));
        LogList logList2 = new LogList(List.of("25-12-2024 14:30 Attended appointment"));
        assertEquals(logList1.hashCode(), logList2.hashCode());
    }

    @Test
    public void hashCode_differentLogList_returnsDifferentHashCode() {
        LogList logList1 = new LogList(List.of("25-12-2024 14:30 Attended appointment"));
        LogList logList2 = new LogList(List.of("26-12-2024 15:30 Attended appointment"));
        assertNotEquals(logList1.hashCode(), logList2.hashCode());
    }
}
