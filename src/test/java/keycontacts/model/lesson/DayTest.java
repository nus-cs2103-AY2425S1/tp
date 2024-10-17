package keycontacts.model.lesson;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class DayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "";
        assertThrows(IllegalArgumentException.class, () -> new Day(invalidDay));
    }

    @Test
    public void isValidDay() {
        // null day
        assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // invalid days
        assertFalse(Day.isValidDay("")); // empty string
        assertFalse(Day.isValidDay("first day")); // not a day of the week

        // valid days
        assertTrue(Day.isValidDay("Monday"));
        assertTrue(Day.isValidDay("Mon")); // abbreviation
        assertTrue(Day.isValidDay("TueSDaY")); // case-insensitive
        assertTrue(Day.isValidDay("tUE")); // case-insensitive abbreviation
    }

    @Test
    public void equals() {
        Day day = new Day("Monday");

        // same values -> returns true
        assertTrue(day.equals(new Day("Monday")));

        // same object -> returns true
        assertTrue(day.equals(day));

        // null -> returns false
        assertFalse(day.equals(null));

        // different types -> returns false
        assertFalse(day.equals(5.0f));

        // different values -> returns false
        assertFalse(day.equals(new Day("Tuesday")));
    }
}
