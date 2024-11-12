package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FromTest {

    private static final String INVALID_FORMAT = "9am";
    private static final String INVALID_FROM = "25:00";
    private static final String VALID_FROM = "0800";
    private static final String OTHER_FROM = "0900";

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
        From from = new From(VALID_FROM);
        // null value -> returns false
        assertFalse(from.equals(null));

        // different type -> returns false
        assertFalse(from.equals(5));

        // same object -> returns true
        assertTrue(from.equals(from));

        // same value -> returns true
        From otherDate = new From(VALID_FROM);
        assertTrue(from.equals(otherDate));

        // different value -> returns false
        otherDate = new From(OTHER_FROM);
        assertFalse(from.equals(otherDate));
    }
    @Test
    public void parseTime_invalidFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new From(INVALID_FROM));
    }
}
