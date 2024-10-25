package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ddd.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        final String invalidDate1 = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate1));

        final String invalidDate2 = "some random string";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate2));

        final String invalidDate3 = "2024-24-12";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate3));

        final String invalidDate4 = "24/12/2024";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate4));

        final String invalidDate5 = "2024-Dec-24";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate5));

        final String invalidDate6 = "2024-December-24";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate6));

        final String invalidDate7 = "24-12-24";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate7));

        final String invalidDate8 = "24 December 2024";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate8));
    }

    @Test
    public void parseDateTest() {
        final String validDateString1 = "2024-12-24";
        final String validDateString2 = "12/24/2024";
        final String validDateString3 = "24 Dec 2024";
        
        final LocalDate actualDate = LocalDate.parse(validDateString1);
        assertEquals(actualDate, Date.parseDate(validDateString1));
        assertEquals(actualDate, Date.parseDate(validDateString2));
        assertEquals(actualDate, Date.parseDate(validDateString3));
    }
}
