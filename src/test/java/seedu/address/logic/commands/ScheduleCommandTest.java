package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

public class ScheduleCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Index index = Index.fromZeroBased(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
        String location = "A Valid Location";

        assertThrows(NullPointerException.class, () -> new ScheduleCommand(null, startTime, endTime, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, null, endTime, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, startTime, null, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, startTime, endTime, null));
    }
}