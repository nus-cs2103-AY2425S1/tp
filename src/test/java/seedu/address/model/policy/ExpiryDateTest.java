package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpiryDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpiryDate((String) null));
        assertThrows(NullPointerException.class, () -> new ExpiryDate((LocalDate) null));
    }

    @Test
    public void constructor_invalidExpiryDate_throwsIllegalArgumentException() {
        // invalid month
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("9/12/2024"));
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("13/12/2024"));
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("-1/12/2024"));

        // invalid day
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("12/00/2024"));
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("12/9/2024"));
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("12/32/2024"));

        // invalid year
        assertThrows(IllegalArgumentException.class, () -> new CoverageAmount("12/12/202"));
    }

    @Test
    public void isValidExpiryDate() {
        // invalid expiry date
        assertFalse(ExpiryDate.isValidExpiryDate("9/12/2024"));
        assertFalse(ExpiryDate.isValidExpiryDate("13/12/2024"));
        assertFalse(ExpiryDate.isValidExpiryDate("-1/12/2024"));
        assertFalse(ExpiryDate.isValidExpiryDate("12/00/2024"));
        assertFalse(ExpiryDate.isValidExpiryDate("12/9/2024"));
        assertFalse(ExpiryDate.isValidExpiryDate("12/32/2024"));
        assertFalse(ExpiryDate.isValidExpiryDate("12/12/202"));
        assertFalse(ExpiryDate.isValidExpiryDate(""));

        // valid expiry date
        assertTrue(ExpiryDate.isValidExpiryDate("12/12/2024"));
        assertTrue(ExpiryDate.isValidExpiryDate("09/02/2024"));
    }

    @Test
    public void isBefore() {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);
        LocalDate yesterday = now.minusDays(1);
        ExpiryDate expiryDate = new ExpiryDate(now);
        assertTrue(expiryDate.isBefore(tomorrow));
        assertFalse(expiryDate.isBefore(yesterday));
    }

    @Test
    public void isAfter() {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);
        LocalDate yesterday = now.minusDays(1);
        ExpiryDate expiryDate = new ExpiryDate(now);
        assertTrue(expiryDate.isAfter(yesterday));
        assertFalse(expiryDate.isAfter(tomorrow));
    }

    @Test
    public void isEqual() {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);
        LocalDate yesterday = now.minusDays(1);
        ExpiryDate expiryDate = new ExpiryDate(now);
        assertTrue(expiryDate.isEqual(now));
        assertFalse(expiryDate.isEqual(tomorrow));
        assertFalse(expiryDate.isEqual(yesterday));
    }

    @Test
    public void toStringMethod() {
        String expected = "12/12/2024";
        assertEquals(expected, new ExpiryDate(expected).toString());
    }

    @Test
    public void equalsMethod() {
        ExpiryDate expiryDate = new ExpiryDate("12/12/2024");

        // same values -> returns true
        assertTrue(expiryDate.equals(new ExpiryDate("12/12/2024")));

        // same object -> returns true
        assertTrue(expiryDate.equals(expiryDate));

        // null -> returns false
        assertFalse(expiryDate.equals(null));

        // different types -> return false
        assertFalse(expiryDate.equals("12/12/2024"));

        // different values -> return false
        assertFalse(expiryDate.equals(new ExpiryDate("12/13/2024")));
    }
}
