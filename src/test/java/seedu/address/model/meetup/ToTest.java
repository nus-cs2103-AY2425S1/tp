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
    public void isValidToFrom() {
        To to = new To("2024-06-25 14:30");

        // null -> returns false
        assertFalse(to.isValidToFrom(null));

        // same values -> returns false
        From from1 = new From("2024-06-25 14:30");
        assertFalse(to.isValidToFrom(from1));

        // to before from -> returns false
        From from2 = new From("2024-06-25 15:30");
        assertFalse(to.isValidToFrom(from2));

        // to after from -> returns true
        From from3 = new From("2024-06-25 11:30");
        assertTrue(to.isValidToFrom(from3));
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
