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
