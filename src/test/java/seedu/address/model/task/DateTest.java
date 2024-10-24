package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DateTest {

    private static final String VALID_DATE = "2023-10-20";
    private static final String INVALID_DATE = "2023-15-45";
    private static final String VALID_DATE_FORMATTED = "Oct 20 2023";
    private static final String DIFFERENT_DATE = "2023-11-20";
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    @Test
    public void constructor_validDate_success() {
        Date date = new Date(VALID_DATE);
        assertEquals(VALID_DATE, date.toString());
    }

    @Test
    public void constructor_invalidDate_throwsDateTimeParseException() {
        // Test if the constructor throws an exception when an invalid date is passed
        try {
            new Date(INVALID_DATE);
        } catch (Exception e) {
            assertTrue(e instanceof java.time.format.DateTimeParseException);
        }
    }

    @Test
    public void isValidDate_validDate_returnsTrue() {
        assertTrue(Date.isValidDate(VALID_DATE));
    }

    @Test
    public void isValidDate_invalidDate_returnsFalse() {
        assertFalse(Date.isValidDate(INVALID_DATE));
    }

    @Test
    public void format_customFormatter_returnsFormattedDate() {
        Date date = new Date(VALID_DATE);
        String formattedDate = date.format(CUSTOM_FORMATTER);
        assertEquals(VALID_DATE_FORMATTED, formattedDate);
    }

    @Test
    public void getDate_validDate_returnsLocalDate() {
        Date date = new Date(VALID_DATE);
        LocalDate expectedLocalDate = LocalDate.parse(VALID_DATE);
        assertEquals(expectedLocalDate, date.getDate());
    }

    @Test
    public void equals_nonDateObject_returnsFalse() {
        Date date = new Date(VALID_DATE);
        assertFalse(date.equals("Some String"));
    }

    @Test
    public void equals_sameDate_returnsTrue() {
        Date date1 = new Date(VALID_DATE);
        Date date2 = new Date(VALID_DATE);
        assertTrue(date1.equals(date2));
    }

    @Test
    public void equals_self_returnsTrue() {
        Date date1 = new Date(VALID_DATE);
        assertTrue(date1.equals(date1));
    }

    @Test
    public void equals_differentDate_returnsFalse() {
        Date date1 = new Date(VALID_DATE);
        Date date2 = new Date("2023-11-20"); // Different date
        assertFalse(date1.equals(date2));
    }

    @Test
    public void hashCode_sameDate_returnsSameHashCode() {
        Date date1 = new Date(VALID_DATE);
        Date date2 = new Date(VALID_DATE);
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void hashCode_differentDate_returnsDifferentHashCode() {
        Date date1 = new Date(VALID_DATE);
        Date date2 = new Date("2023-11-20");
        assertFalse(date1.hashCode() == date2.hashCode());
    }
}
