package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class DateTest {
    private static final String DATETIME_VALID = "2024-12-12 12:00";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDateFormats_throwsIllegalArgumentException() {
        String dateTime = "5";
        assertThrows(IllegalArgumentException.class, () -> new Date(dateTime));

        String dateTimeIncorrectFormat = "2024-12-12 11, 00";
        assertThrows(DateTimeParseException.class, () -> new Date(dateTimeIncorrectFormat));
    }

    @Test
    public void constructor_validDateFormats_success() {
        assertDoesNotThrow(() -> new Date(DATETIME_VALID));
    }

    @Test
    public void equals() {
        Date validDate = new Date(DATETIME_VALID);

        // same values -> return true
        assertEquals(validDate, new Date(DATETIME_VALID));

        // same object -> return true
        assertEquals(validDate, validDate);

        // null -> return false
        assertNotEquals(null, validDate);

        // different value -> return false
        assertNotEquals(validDate, new Date("2024-12-12 13:00"));
    }
}
