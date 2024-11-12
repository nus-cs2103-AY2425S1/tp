package seedu.edulog.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edulog.model.calendar.LessonTime;

public class LessonTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonTime(null));
    }

    @Test
    public void constructor_emptyTime_throwsIllegalArgumentException() {
        String invalidTimeEmpty = "";
        assertThrows(IllegalArgumentException.class, () -> new LessonTime(invalidTimeEmpty));

    }

    @Test
    public void checkValidLessonTime() {
        // Values in the morning
        assertTrue(LessonTime.checkValidLessonTime("1000"));
        assertTrue(LessonTime.checkValidLessonTime("0511"));
        assertTrue(LessonTime.checkValidLessonTime("0159"));

        // Values in the afternoon and evening
        assertTrue(LessonTime.checkValidLessonTime("1230"));
        assertTrue(LessonTime.checkValidLessonTime("2200"));
        assertTrue(LessonTime.checkValidLessonTime("2310"));

        // Boundary values
        assertTrue(LessonTime.checkValidLessonTime("0000"));
        assertTrue(LessonTime.checkValidLessonTime("2359"));

        // OOB hour and/or minute
        assertFalse(LessonTime.checkValidLessonTime("2400"));
        assertFalse(LessonTime.checkValidLessonTime("9930"));
        assertFalse(LessonTime.checkValidLessonTime("0260"));
        assertFalse(LessonTime.checkValidLessonTime("2199"));
        assertFalse(LessonTime.checkValidLessonTime("2563"));

        // Common alternative time formats
        assertFalse(LessonTime.checkValidLessonTime("21:00"));
        assertFalse(LessonTime.checkValidLessonTime("11.00"));
        assertFalse(LessonTime.checkValidLessonTime("11-00"));
        assertFalse(LessonTime.checkValidLessonTime("12PM"));
        assertFalse(LessonTime.checkValidLessonTime("930"));

        // Letters
        assertFalse(LessonTime.checkValidLessonTime("dwodqkoq"));

        // null
        assertThrows(NullPointerException.class, () -> LessonTime.checkValidLessonTime(null));
    }
    @Test
    public void checkValidLessonTimes() {
        // start time before end time
        assertTrue(LessonTime.checkValidLessonTimes("1230", "1400"));
        assertTrue(LessonTime.checkValidLessonTimes("2358", "2359"));

        // start time after end time
        assertTrue(LessonTime.checkValidLessonTimes("2200", "0000"));

        // start time same as end time
        assertFalse(LessonTime.checkValidLessonTimes("2100", "2100"));

        // one of lesson times are invalid
        assertThrows(IllegalArgumentException.class, () -> LessonTime.checkValidLessonTimes("0260", "0430"));
        assertThrows(IllegalArgumentException.class, () -> LessonTime.checkValidLessonTimes("0250", "430"));

        // both lesson times are invalid
        assertThrows(IllegalArgumentException.class, () -> LessonTime.checkValidLessonTimes("0260", "430"));

        // null
        assertThrows(NullPointerException.class, () -> LessonTime.checkValidLessonTimes("0100", null));
        assertThrows(NullPointerException.class, () -> LessonTime.checkValidLessonTimes(null, "1430"));
        assertThrows(NullPointerException.class, () -> LessonTime.checkValidLessonTimes(null, null));
    }

    @Test
    public void spansTwoDays() {
        // start time before end time
        assertFalse(LessonTime.spansTwoDays(
            new LessonTime("1230"),
            new LessonTime("1400")));

        // start time after end time
        assertTrue(LessonTime.spansTwoDays(
            new LessonTime("2230"),
            new LessonTime("0000")));

        // null
        assertThrows(NullPointerException.class, () -> LessonTime.spansTwoDays(new LessonTime("1111"), null));
        assertThrows(NullPointerException.class, () -> LessonTime.spansTwoDays(null, new LessonTime("1111")));
        assertThrows(NullPointerException.class, () -> LessonTime.spansTwoDays(null, null));
    }

    @Test
    public void equals() {
        LessonTime time = new LessonTime("1200");

        // same value
        assertTrue(time.equals(new LessonTime("1200")));

        // same object
        assertTrue(time.equals(time));

        // different value
        assertFalse(time.equals(new LessonTime("1201")));

        // different types
        assertFalse(time.equals(5.0f));

        // null
        assertFalse(time.equals(null));
    }

}
