package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    public static final String INVALID_FORMAT = "24/12/2024";

    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidFormat_throwsNullPointerException() {
        assertThrows(IllegalArgumentException.class, () -> new Date(INVALID_FORMAT));
    }

    @Test
    public void equals() {
        Date date = new Date("121224");
        // null value -> returns false
        assertFalse(date.equals(null));

        // different type -> returns false
        assertFalse(date.equals(5));

        // same object -> returns true
        assertTrue(date.equals(date));

        // same value -> returns true
        Date otherDate = new Date("121224");
        assertTrue(date.equals(otherDate));

        // different value -> returns false
        otherDate = new Date("131224");
        assertFalse(date.equals(otherDate));
    }

}
