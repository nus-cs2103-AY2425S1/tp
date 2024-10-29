package seedu.address.model.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FromTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new From(null));
    }

    @Test
    public void constructor_invalidFrom_throwsIllegalArgumentException() {
        String invalidFrom = "2024-13-01 20:00"; // Invalid Month
        assertThrows(IllegalArgumentException.class, () -> new From(invalidFrom));
    }

    @Test
    public void isValidFrom() {
        // null from
        assertThrows(NullPointerException.class, () -> From.isValidFrom(null));

        // invalid from
        assertFalse(From.isValidFrom("")); // empty string
        assertFalse(From.isValidFrom(" ")); // spaces only
        assertFalse(From.isValidFrom("123456Hi")); // invalid format
        assertFalse(From.isValidFrom("2024-01-01 23:59 hello")); // invalid format
        assertFalse(From.isValidFrom("12 Jan 2024")); // invalid format
        assertFalse(From.isValidFrom("2024 01 01 23:59")); // invalid format
        assertFalse(From.isValidFrom("2024/01/01 23:59")); // invalid format
        assertFalse(From.isValidFrom("01-01-2024 23:59")); // invalid format
        assertFalse(From.isValidFrom("23:59 2024-01-01")); // invalid format
        assertFalse(From.isValidFrom("23:59")); // missing date
        assertFalse(From.isValidFrom("2024-01-01")); // missing time
        assertFalse(From.isValidFrom("2024-15-01 23:59")); // invalid month
        assertFalse(From.isValidFrom("2024-01-32 23:59")); // invalid day
        assertFalse(From.isValidFrom("2024-01-01 24:59")); // invalid time

        // valid from
        assertTrue(From.isValidFrom("2024-12-31 23:59")); // last day of the year
        assertTrue(From.isValidFrom("2024-01-01 00:00")); // first day of the year
        assertTrue(From.isValidFrom("2022-06-25 14:30")); // past date
        assertTrue(From.isValidFrom("2050-10-01 10:28")); // future date
    }

    @Test
    public void equals() {
        From from = new From("2024-06-25 14:30");

        // same values -> returns true
        assertTrue(from.equals(new From("2024-06-25 14:30")));

        // same object -> returns true
        assertTrue(from.equals(from));

        // null -> returns false
        assertFalse(from.equals(null));

        // different types -> returns false
        assertFalse(from.equals(5.0f));

        // different values -> returns false
        assertFalse(from.equals(new From("2024-10-15 13:30")));
    }

    @Test
    public void toPrettyString() {
        From from = new From("2024-06-25 14:30");
        String expectedString = "25 Jun 2024 (2:30pm)";
        assertEquals(from.toPrettyString(), expectedString);
    }

    @Test
    public void toStringMethod() {
        From from = new From("2024-06-25 14:30");
        String expectedString = "2024-06-25 14:30";
        assertEquals(from.toString(), expectedString);
    }
}
