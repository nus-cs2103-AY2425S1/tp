package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void equals() {
        Date validDate = new Date(DATETIME_VALID);

        // same values -> return true
        assertTrue(validDate.equals(new Date(DATETIME_VALID)));

        // same object -> return true
        assertTrue(validDate.equals(validDate));

        // null -> return false
        assertFalse(validDate.equals(null));

        // different value -> return false
        assertFalse(validDate.equals(new Date("2024-12-12 13:00")));
    }
}
