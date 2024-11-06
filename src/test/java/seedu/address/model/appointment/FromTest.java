package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FromTest {

    public static final String INVALID_FORMAT = "9am";
    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new From(null));
    }

    @Test
    public void constructor_invalidFormat_throwsNullPointerException() {
        assertThrows(IllegalArgumentException.class, () -> new From(INVALID_FORMAT));
    }

    @Test
    public void equals() {
        From from = new From("0800");
        // null value -> returns false
        assertFalse(from.equals(null));

        // different type -> returns false
        assertFalse(from.equals(5));

        // same object -> returns true
        assertTrue(from.equals(from));

        // same value -> returns true
        From otherDate = new From("0800");
        assertTrue(from.equals(otherDate));

        // different value -> returns false
        otherDate = new From("0900");
        assertFalse(from.equals(otherDate));
    }
}
