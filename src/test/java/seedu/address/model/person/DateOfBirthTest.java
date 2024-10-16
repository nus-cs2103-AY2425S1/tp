package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateOfBirthTest {

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "99 Jal 0002";
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDate));
    }

    @Test
    public void constructor_dateWithMonthFullySpelled_throwsIllegalArgumentException() {
        String invalidDate = "9 January 2000";
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDate));
    }

    @Test
    public void constructor_futureDate_throwsIllegalArgumentException() {
        String futureDate = "9 Dec 9999";
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(futureDate));
    }

    @Test
    public void constructor_validDate_shouldSucceed() {
        assertDoesNotThrow(() -> new DateOfBirth("18 Feb 1978"));
    }

    @Test
    public void isValidDate_null_throwsNullPointerException() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDate(nullString));
    }

    @Test
    public void isValidDate_validDate_returnsTrue() {
        String validDate1 = "1 Jan 2000";
        String validDate2 = "31 Dec 1998";

        assertTrue(DateOfBirth.isValidDate(validDate1));
        assertTrue(DateOfBirth.isValidDate(validDate2));
    }

    @Test
    public void isValidDate_invalidDate_returnsFalse() {
        String invalidDate = "srjhehr";
        assertFalse(DateOfBirth.isValidDate(invalidDate));
    }

    @Test
    public void isValidDate_futureDate_returnsFalse() {
        String futureDate = "9 Jan 9999";
        assertFalse(DateOfBirth.isValidDate(futureDate));
    }
}
