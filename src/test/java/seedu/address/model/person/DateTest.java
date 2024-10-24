package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class DateTest {
    @Test
    public void isValidDate_null_throwsNullPointerException() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> Date.isValidDate(nullString));
    }

    @Test
    public void isValidDate_validDate_returnsTrue() {
        String validDate1 = "1 Jan 2000";
        String validDate2 = "31 Dec 1998";

        assertTrue(Date.isValidDate(validDate1));
        assertTrue(Date.isValidDate(validDate2));
    }

    @Test
    public void isValidDate_dateWithLowercaseMonth_returnsTrue() {
        String date1 = "1 jan 2000";
        String date2 = "31 dec 1983";

        assertTrue(Date.isValidDate(date1));
        assertTrue(Date.isValidDate(date2));
    }
    @Test
    public void isValidDate_invalidDate_returnsFalse() {
        String invalidDate = "srjhehr";
        assertFalse(Date.isValidDate(invalidDate));
    }

    @Test
    public void isValidDate_invalidDay_returnsFalse() {
        String invalidDate = "32 Jan 2000";
        assertFalse(Date.isValidDate(invalidDate));
    }

    @Test
    public void isValidDate_invalidMonth_returnsFalse() {
        String invalidDate = "1 January 2000";
        assertFalse(Date.isValidDate(invalidDate));
    }

    @Test
    public void isValidDate_invalidYear_returnsFalse() {
        String invalidDate = "1 Jan 10000BC";
        assertFalse(Date.isValidDate(invalidDate));
    }

    @Test
    public void equals() {
        HashMap<Date, Date> equalDates = new HashMap<>();
        equalDates.put(new DateOfBirth("1 Jan 2000"), new DateOfBirth("01 Jan 2000"));
        equalDates.put(new DateOfBirth("18 Nov 1991"), new DateOfBirth("18 Nov 1991"));
        equalDates.put(new DateOfBirth("30 Dec 1889"), new DateOfBirth("30 Dec 1889"));

        HashMap<Date, Date> differentDates = new HashMap<>();
        differentDates.put(new DateOfBirth("1 Jan 2000"), new DateOfBirth("1 Feb 2000"));
        differentDates.put(new DateOfBirth("31 Dec 1999"), new DateOfBirth("1 Jan 2000"));
        differentDates.put(new DateOfBirth("2 Jan 1932"), new DateOfBirth("22 Jan 1932"));

        for (HashMap.Entry<Date, Date> entry: equalDates.entrySet()) {
            Date age1 = entry.getKey();
            Date age2 = entry.getValue();
            assertEquals(age1, age2);
        }

        for (HashMap.Entry<Date, Date> entry: differentDates.entrySet()) {
            Date age1 = entry.getKey();
            Date age2 = entry.getValue();
            assertNotEquals(age1, age2);
        }

        assertNotEquals(new DateOfBirth("29 Nov 2022"), "29 Nov 2022");
    }
}
