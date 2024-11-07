package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ToTest {
    public static final String INVALID_FORMAT = "9am";
    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new To(null));
    }

    @Test
    public void constructor_invalidFormat_throwsNullPointerException() {
        assertThrows(IllegalArgumentException.class, () -> new To(INVALID_FORMAT));
    }

    @Test
    public void equals() {
        To to = new To("0800");
        // null value -> returns false
        assertFalse(to.equals(null));

        // different type -> returns false
        assertFalse(to.equals(5));

        // same object -> returns true
        assertTrue(to.equals(to));

        // same value -> returns true
        To otherDate = new To("0800");
        assertTrue(to.equals(otherDate));

        // different value -> returns false
        otherDate = new To("0900");
        assertFalse(to.equals(otherDate));
    }
    @Test
    public void parseTime_invalidFormat_throwsIllegalArgumentException() {
        String invalidTime = "10:64";

        assertThrows(IllegalArgumentException.class, () -> new To(invalidTime));
    }
}
