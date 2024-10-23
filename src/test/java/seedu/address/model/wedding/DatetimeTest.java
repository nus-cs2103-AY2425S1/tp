package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DatetimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Datetime(null));
    }

    @Test
    public void constructor_invalidDatetime_throwsIllegalArgumentException() {
        String invalidDatetime = "invalid date";
        assertThrows(IllegalArgumentException.class, () -> new Datetime(invalidDatetime));
    }

    @Test
    public void isValidDatetime() {
        // null datetime
        assertThrows(NullPointerException.class, () -> Datetime.isValidDatetime(null));

        // invalid datetime
        assertFalse(Datetime.isValidDatetime("")); // empty string
        assertFalse(Datetime.isValidDatetime(" ")); // spaces only
        assertFalse(Datetime.isValidDatetime("99/99/9999")); // invalid date format
        assertFalse(Datetime.isValidDatetime("12/31/2024")); // invalid date format (should be dd/mm/yyyy)

        // valid datetime
        assertTrue(Datetime.isValidDatetime("15/10/2024")); // valid date
        assertTrue(Datetime.isValidDatetime("01/01/2023")); // edge case
        assertTrue(Datetime.isValidDatetime("29/02/2024")); // edge date
    }

    @Test
    public void equals() {
        Datetime datetime = new Datetime("12/11/2023");

        // same values -> returns true
        assertTrue(datetime.equals(new Datetime("12/11/2023")));

        // same object -> returns true
        assertTrue(datetime.equals(datetime));

        // null -> returns false
        assertFalse(datetime.equals(null));

        // different types -> returns false
        assertFalse(datetime.equals(5.0f));

        // different values -> returns false
        assertFalse(datetime.equals(new Datetime("15/11/2023")));
    }
}

