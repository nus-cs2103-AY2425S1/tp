package seedu.address.model.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ToTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new To(null));
    }

    @Test
    public void constructor_invalidto_throwsIllegalArgumentException() {
        String invalidto = "2024-13-01 20:00"; // Invalid Month
        assertThrows(IllegalArgumentException.class, () -> new To(invalidto));
    }

    @Test
    public void isValidTo() {
        // null to
        assertThrows(NullPointerException.class, () -> To.isValidTo(null));

        // invalid to
        assertFalse(To.isValidTo("")); // empty string
        assertFalse(To.isValidTo(" ")); // spaces only
        assertFalse(To.isValidTo("123456Hi")); // invalid format
        assertFalse(To.isValidTo("2024-01-01 23:59 hello")); // invalid format
        assertFalse(To.isValidTo("12 Jan 2024")); // invalid format
        assertFalse(To.isValidTo("2024 01 01 23:59")); // invalid format
        assertFalse(To.isValidTo("2024/01/01 23:59")); // invalid format
        assertFalse(To.isValidTo("01-01-2024 23:59")); // invalid format
        assertFalse(To.isValidTo("23:59 2024-01-01")); // invalid format
        assertFalse(To.isValidTo("23:59")); // missing date
        assertFalse(To.isValidTo("2024-01-01")); // missing time
        assertFalse(To.isValidTo("2024-15-01 23:59")); // invalid month
        assertFalse(To.isValidTo("2024-01-32 23:59")); // invalid day
        assertFalse(To.isValidTo("2024-01-01 24:59")); // invalid time

        // valid to
        assertTrue(To.isValidTo("2024-12-31 23:59")); // last day of the year
        assertTrue(To.isValidTo("2024-01-01 00:00")); // first day of the year
        assertTrue(To.isValidTo("2022-06-25 14:30")); // past date
        assertTrue(To.isValidTo("2050-10-01 10:28")); // future date
    }

    @Test
    public void equals() {
        To to = new To("2024-06-25 14:30");

        // same values -> returns true
        assertTrue(to.equals(new To("2024-06-25 14:30")));

        // same object -> returns true
        assertTrue(to.equals(to));

        // null -> returns false
        assertFalse(to.equals(null));

        // different types -> returns false
        assertFalse(to.equals(5.0f));

        // different values -> returns false
        assertFalse(to.equals(new To("2024-10-15 13:30")));
    }

    @Test
    public void toPrettyString() {
        To to = new To("2024-06-25 14:30");
        String expectedString = "25 Jun 2024 (2:30pm)";
        assertEquals(to.toPrettyString(), expectedString);
    }

    @Test
    public void toStringMethod() {
        To to = new To("2024-06-25 14:30");
        String expectedString = "2024-06-25 14:30";
        assertEquals(to.toString(), expectedString);
    }
}
