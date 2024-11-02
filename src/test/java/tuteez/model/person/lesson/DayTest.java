package tuteez.model.person.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DayTest {

    @Test
    public void isValidDay_validInputs_returnsTrue() {
        assertTrue(Day.isValidDay("monday"));
        assertTrue(Day.isValidDay("tuesday"));
        assertTrue(Day.isValidDay("wednesday"));
        assertTrue(Day.isValidDay("thursday"));
        assertTrue(Day.isValidDay("friday"));
        assertTrue(Day.isValidDay("saturday"));
        assertTrue(Day.isValidDay("sunday"));

        assertTrue(Day.isValidDay("mon"));
        assertTrue(Day.isValidDay("tue"));
        assertTrue(Day.isValidDay("wed"));
        assertTrue(Day.isValidDay("thu"));
        assertTrue(Day.isValidDay("fri"));
        assertTrue(Day.isValidDay("sat"));
        assertTrue(Day.isValidDay("sun"));

        assertTrue(Day.isValidDay("Monday"));
        assertTrue(Day.isValidDay("TUESDAY"));
        assertTrue(Day.isValidDay("wEdNeSdAy"));
        assertTrue(Day.isValidDay("Thu"));
        assertTrue(Day.isValidDay("FRI"));
    }

    @Test
    public void isValidDay_invalidInputs_returnsFalse() {
        assertFalse(Day.isValidDay("not a day"));
        assertFalse(Day.isValidDay("mon day"));
        assertFalse(Day.isValidDay("mondays"));
        assertFalse(Day.isValidDay("tues"));
        assertFalse(Day.isValidDay("thur"));

        assertFalse(Day.isValidDay(""));
        assertFalse(Day.isValidDay(" "));
        assertFalse(Day.isValidDay("\t"));

        assertFalse(Day.isValidDay(null));
    }

    @Test
    public void convertDayToEnum_validInputs_returnsCorrectEnum() {
        assertEquals(Day.MONDAY, Day.convertDayToEnum("monday"));
        assertEquals(Day.TUESDAY, Day.convertDayToEnum("tuesday"));
        assertEquals(Day.WEDNESDAY, Day.convertDayToEnum("wednesday"));
        assertEquals(Day.THURSDAY, Day.convertDayToEnum("thursday"));
        assertEquals(Day.FRIDAY, Day.convertDayToEnum("friday"));
        assertEquals(Day.SATURDAY, Day.convertDayToEnum("saturday"));
        assertEquals(Day.SUNDAY, Day.convertDayToEnum("sunday"));

        assertEquals(Day.MONDAY, Day.convertDayToEnum("mon"));
        assertEquals(Day.TUESDAY, Day.convertDayToEnum("tue"));
        assertEquals(Day.WEDNESDAY, Day.convertDayToEnum("wed"));
        assertEquals(Day.THURSDAY, Day.convertDayToEnum("thu"));
        assertEquals(Day.FRIDAY, Day.convertDayToEnum("fri"));
        assertEquals(Day.SATURDAY, Day.convertDayToEnum("sat"));
        assertEquals(Day.SUNDAY, Day.convertDayToEnum("sun"));

        assertEquals(Day.MONDAY, Day.convertDayToEnum("Monday"));
        assertEquals(Day.TUESDAY, Day.convertDayToEnum("TUESDAY"));
        assertEquals(Day.WEDNESDAY, Day.convertDayToEnum("wEdNeSdAy"));
    }

    @Test
    public void toString_returnsUpperCase() {
        assertEquals("MONDAY", Day.MONDAY.toString());
        assertEquals("TUESDAY", Day.TUESDAY.toString());
        assertEquals("WEDNESDAY", Day.WEDNESDAY.toString());
        assertEquals("THURSDAY", Day.THURSDAY.toString());
        assertEquals("FRIDAY", Day.FRIDAY.toString());
        assertEquals("SATURDAY", Day.SATURDAY.toString());
        assertEquals("SUNDAY", Day.SUNDAY.toString());
    }
}
