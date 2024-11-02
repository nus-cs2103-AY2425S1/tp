package seedu.edulog.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edulog.model.calendar.Day;

public class DayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_emptyDay_throwsIllegalArgumentException() {
        String invalidDayEmpty = "";
        assertThrows(IllegalArgumentException.class, () -> new Day(invalidDayEmpty));

    }

    @Test
    public void checkValidDayOfWeek() {
        // Days of the week, spelt in full
        for (String day: Day.DAYS_OF_THE_WEEK) {
            assertTrue(Day.checkValidDayOfWeek(day));
        }

        // Days of the week, in shorthand
        for (String day: Day.DAYS_OF_THE_WEEK_SHORTHAND) {
            assertTrue(Day.checkValidDayOfWeek(day));
        }

        // mixed-case representation
        assertTrue(Day.checkValidDayOfWeek("mon"));
        assertTrue(Day.checkValidDayOfWeek("TUE"));
        assertTrue(Day.checkValidDayOfWeek("wednesday"));
        assertTrue(Day.checkValidDayOfWeek("Thursday"));
        assertTrue(Day.checkValidDayOfWeek("FrIdAy"));

        // typos
        assertFalse(Day.checkValidDayOfWeek("Mnoday"));
        assertFalse(Day.checkValidDayOfWeek("Thr"));

        // numbers
        assertFalse(Day.checkValidDayOfWeek("M1y"));
        assertFalse(Day.checkValidDayOfWeek("4"));
    }
    @Test
    public void equals() {
        Day day = new Day("Monday");

        // same values
        assertEquals(day, new Day("Monday"));

        // different case
        assertEquals(day, new Day("MoNdAy"));

        // shorthand
        assertEquals(day, new Day("mon"));

        // null
        assertNotEquals(null, day);

        // different types
        assertNotEquals(day, 5.0f);

        // different days
        assertNotEquals(day, new Day("Tuesday"));
    }

    // Test dependency: proper functioning of equals()
    @Test
    public void plus() {
        Day day = new Day("Monday");
        Day nextDay = new Day("Tuesday");

        // add 0
        assertEquals(day, day.plus(0));

        // addition does not affect original day
        Day testDay = new Day("Monday");
        testDay.plus(3);
        assertEquals(day, testDay);

        // add 1 -> generates the next day
        assertEquals(day.plus(1), nextDay);

        // add 1 on a Sunday -> generates Monday
        assertEquals(new Day("Sunday").plus(1), new Day("Monday"));

        // add 3 is equivalent to 3 add 1
        assertEquals(day.plus(3), day.plus(1).plus(1).plus(1));
    }

}
