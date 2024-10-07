package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class TimeRangeTest {

    @Test
    public void testTimeRangeWithEndTime() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 9, 0);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 17, 0);
        TimeRange timeRange = new TimeRange(start, Optional.of(end));

        assertEquals(start, timeRange.getStartDateTime());
        assertEquals(Optional.of(end), timeRange.getEndDateTime());
    }

    @Test
    public void testTimeRangeWithoutEndTime() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 9, 0);
        TimeRange timeRange = new TimeRange(start);

        assertEquals(start, timeRange.getStartDateTime());
        assertTrue(timeRange.getEndDateTime().isEmpty());
    }

    @Test
    public void testInvalidTimeRangeThrowsException() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 9, 0);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 8, 0); // Invalid: end is before start

        assertThrows(IllegalArgumentException.class, () -> {
            new TimeRange(start, Optional.of(end));
        });
    }
}

