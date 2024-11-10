package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void constructor_invalidDateFormats_throwsDateTimeParseException() {
        String dateTime = "2024-12-12";
        assertThrows(DateTimeParseException.class, () -> new Date(dateTime));

        String dateTimeIncorrectFormat = "2024-12-12 11, 00";
        assertThrows(DateTimeParseException.class, () -> new Date(dateTimeIncorrectFormat));

        String dateTimeImpossibleDate = "2024-02-31 11:00";
        assertThrows(DateTimeParseException.class, () -> new Date(dateTimeImpossibleDate));
    }

    @Test
    public void constructor_validDateFormats_success() {
        assertDoesNotThrow(() -> new Date(DATETIME_VALID));
    }

    @Test
    public void isBefore_success() {
        Date firstDate = new Date(DATETIME_VALID);
        Date secondDate = new Date("2024-12-13 12:00");

        assertTrue(firstDate.isBefore(secondDate));
    }

    @Test
    public void isBefore_sameDate_success() {
        Date firstDate = new Date(DATETIME_VALID);
        Date secondDate = new Date(DATETIME_VALID);

        // same -> not strictly before
        assertTrue(firstDate.isBefore(secondDate));
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
