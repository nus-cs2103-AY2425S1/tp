package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class ScheduleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Schedule(null));
    }

    @Test
    public void constructor_invalidSchedule_throwsIllegalArgumentException() {
        String invalidSchedule = "";
        assertThrows(IllegalArgumentException.class, () -> new Schedule(invalidSchedule));
    }

    @Test
    public void isValidSchedule_null_throwsNullPointerException() {
        // null schedule
        assertThrows(NullPointerException.class, () -> Schedule.isValidSchedule(null));
    }

    @Test
    public void isValidSchedule_invalidFormat_returnsFalse() {
        // invalid schedules
        assertFalse(Schedule.isValidSchedule("")); // empty string
        assertFalse(Schedule.isValidSchedule(" ")); // spaces only
        assertFalse(Schedule.isValidSchedule("monday")); // single day
        assertFalse(Schedule.isValidSchedule("monday-1200-1300-1400")); // extra time
    }

    @Test
    public void isValidSchedule_invalidDay_returnsFalse() {
        // invalid day
        assertFalse(Schedule.isValidSchedule("mon-1200-1300"));
        assertFalse(Schedule.isValidSchedule("monday1-1200-1300"));

        // extra space
        assertFalse(Schedule.isValidSchedule("monday -1200-1300"));
        assertFalse(Schedule.isValidSchedule(" monday-1200-1300"));
        assertFalse(Schedule.isValidSchedule(" monday -1200-1300"));
    }
    @Test
    public void isValidSchedule_invalidTime_returnsFalse() {
        // invalid time range
        assertFalse(Schedule.isValidSchedule("monday-1300-1300"));
        assertFalse(Schedule.isValidSchedule("monday-1300-1200"));

        // invalid time
        assertFalse(Schedule.isValidSchedule("monday-1200-130"));
        assertFalse(Schedule.isValidSchedule("monday-1300-1360"));
        assertFalse(Schedule.isValidSchedule("monday-1300-2400"));

        // extra space
        assertFalse(Schedule.isValidSchedule("monday-1300- 1400"));
        assertFalse(Schedule.isValidSchedule("monday-1300 -1400"));
        assertFalse(Schedule.isValidSchedule("monday- 1300 - 1400"));
    }

    @Test
    public void isValidSchedule_validSchedule_returnsTrue() {
        assertTrue(Schedule.isValidSchedule("monday-1200-1300")); // single day and time
        assertTrue(Schedule.isValidSchedule("FRIDAY-2350-2359")); // day in uppercase
        assertTrue(Schedule.isValidSchedule("satUrDay-0000-2359")); // day in mixed case

    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Schedule.isValidTime(null));

        // invalid time
        assertFalse(Schedule.isValidTime("")); // empty string
        assertFalse(Schedule.isValidTime(" ")); // spaces only
        assertFalse(Schedule.isValidTime("123")); // wrong length
        assertFalse(Schedule.isValidTime("12345")); // wrong length
        assertFalse(Schedule.isValidTime("12:34")); // wrong format
        assertFalse(Schedule.isValidTime("2400")); // invalid time
        assertFalse(Schedule.isValidTime("2360")); // invalid time

        // valid time
        assertTrue(Schedule.isValidTime("0000")); // minimum time
        assertTrue(Schedule.isValidTime("2359")); // maximum time
    }

    @Test
    public void isValidTimeRange() {
        // null time
        assertThrows(NullPointerException.class, () -> Schedule.isValidTimeRange(null, null));

        // invalid time range
        assertFalse(Schedule.isValidTimeRange("1200", "1200")); // same time
        assertFalse(Schedule.isValidTimeRange("1300", "1200")); // start time after end time

        // valid time range
        assertTrue(Schedule.isValidTimeRange("1200", "1300")); // start time before end time
    }

    @Test
    public void equals() {
        Schedule schedule = new Schedule("monday-1200-1300");
        Schedule sameSchedule = new Schedule("monDay-1200-1300");
        Schedule differentSchedule = new Schedule("monday-1300-1400");
        // same values -> returns true
        assertTrue(schedule.equals(sameSchedule));

        // same object -> returns true
        assertTrue(schedule.equals(schedule));

        // null -> returns false
        assertFalse(schedule.equals(null));

        // different types -> returns false
        assertFalse(schedule.equals(5.0f));

        // different values -> returns false
        assertFalse(schedule.equals(differentSchedule));
    }

    @Test
    public void toStringTest() {
        Schedule schedule = new Schedule("monday-1200-1300");
        assertEquals("MONDAY 12:00 - 13:00", schedule.toString());

        schedule = new Schedule("FRIDaY-0000-2359");
        assertEquals("FRIDAY 00:00 - 23:59", schedule.toString());
    }

    @Test
    public void hashCodeTest() {
        Schedule schedule = new Schedule("monday-1200-1300");
        Schedule sameSchedule = new Schedule("monDay-1200-1300");
        Schedule differentSchedule = new Schedule("monday-1300-1400");

        assertEquals(schedule.hashCode(), sameSchedule.hashCode());
        assertNotEquals(schedule.hashCode(), differentSchedule.hashCode());
    }
}
