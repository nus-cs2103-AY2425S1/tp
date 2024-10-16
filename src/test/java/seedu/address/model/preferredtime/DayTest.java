package seedu.address.model.preferredtime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalid_empty = "";
        assertThrows(IllegalArgumentException.class, () -> new Day(invalid_empty));

        String invalid_out_of_range = "Wednes";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalid_out_of_range));
    }

    @Test
    public void isValidDay() {
        // null day
        assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // invalid day
        assertFalse(Day.isValidDay("")); // empty String
        assertFalse(Day.isValidDay(" ")); // spaces only
        assertFalse(Day.isValidDay("WEdnes")); // wrong abbreviation

        // valid day
        assertTrue(Day.isValidDay("Wednesday")); // normal input
        assertTrue(Day.isValidDay("wednesday")); // all lower case
        assertTrue(Day.isValidDay("WEDNESDAY")); // all upper case
        assertTrue(Day.isValidDay("mon")); // lower case abbreviation
    }
}
