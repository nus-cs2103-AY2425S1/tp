package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ToTest {
    private static final String INVALID_FORMAT = "9am";
    private static final String INVALID_TO = "10:64";
    private static final String VALID_TO = "0800";
    private static final String OTHER_TO = "0900";

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
        To to = new To(VALID_TO);
        // null value -> returns false
        assertFalse(to.equals(null));

        // different type -> returns false
        assertFalse(to.equals(5));

        // same object -> returns true
        assertTrue(to.equals(to));

        // same value -> returns true
        To otherDate = new To(VALID_TO);
        assertTrue(to.equals(otherDate));

        // different value -> returns false
        otherDate = new To(OTHER_TO);
        assertFalse(to.equals(otherDate));
    }
    @Test
    public void parseTime_invalidFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new To(INVALID_TO));
    }
}
