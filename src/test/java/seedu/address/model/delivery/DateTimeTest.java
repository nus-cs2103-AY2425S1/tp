package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "01-01-2234 00:70";
        assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidTime));
    }

    @Test
    public void isEarlierThan_laterDeliveryTimes() {
        DateTime datetime = new DateTime("18-06-2023 17:00");
        assertTrue(datetime.isEarlierThan(new DateTime("18-06-2023 17:01"))); //later time by minute
        assertTrue(datetime.isEarlierThan(new DateTime("18-06-2024 17:00"))); //later by year
        assertTrue(datetime.isEarlierThan(new DateTime("18-07-2023 17:00"))); //later by month
        assertTrue(datetime.isEarlierThan(new DateTime("19-06-2023 17:00"))); //later by day
    }

    @Test
    public void isEarlierThan_earlierDeliveryTimes() {
        DateTime datetime = new DateTime("18-06-2023 08:00");
        assertFalse(datetime.isEarlierThan(new DateTime("18-06-2023 07:59"))); //earlier time by minute
        assertFalse(datetime.isEarlierThan(new DateTime("18-06-2022 08:00"))); //earlier by year
        assertFalse(datetime.isEarlierThan(new DateTime("18-05-2023 08:00"))); //earlier by month
        assertFalse(datetime.isEarlierThan(new DateTime("17-06-2023 08:00"))); //earlier by day
        assertFalse(datetime.isEarlierThan(new DateTime("18-06-2023 08:00"))); //same date and time
    }


    @Test
    public void isValidDateTime() {
        // null time
        assertThrows(NullPointerException.class, () -> DateTime.isValidTime(null));

        // invalid time formats
        assertFalse(DateTime.isValidTime("")); // empty string
        assertFalse(DateTime.isValidTime(" ")); // spaces only
        assertFalse(DateTime.isValidTime("10-10-2024 50:00")); // invalid date and time
        assertFalse(DateTime.isValidTime("2024-10-10 15:30")); // wrong format (yyyy-MM-dd instead of dd-MM-yyyy)
        assertFalse(DateTime.isValidTime("10-10-2024")); // missing time portion

        // valid time formats
        assertTrue(DateTime.isValidTime("10-10-2023 15:30")); // valid date and time
    }

    @Test
    public void equals() {
        DateTime dateTime = new DateTime("12-12-2023 15:30");

        // same values -> returns true
        assertTrue(dateTime.equals(new DateTime("12-12-2023 15:30")));

        // same object -> returns true
        assertTrue(dateTime.equals(dateTime));

        // null -> returns false
        assertFalse(dateTime.equals(null));

        // different types -> returns false
        assertFalse(dateTime.equals(5));

        // different values -> returns false
        assertFalse(dateTime.equals(new DateTime("12-12-2023 16:30")));
    }
}
