package hallpointer.address.model.session;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SessionDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SessionDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "invalid-date";
        assertThrows(IllegalArgumentException.class, () -> new SessionDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> SessionDate.isValidDate(null));

        // invalid dates
        assertFalse(SessionDate.isValidDate("")); // empty string
        assertFalse(SessionDate.isValidDate(" ")); // spaces only
        assertFalse(SessionDate.isValidDate("32 Dec 2024")); // invalid day
        assertFalse(SessionDate.isValidDate("31 Feb 2024")); // invalid month-day combination
        assertFalse(SessionDate.isValidDate("24-09-2024")); // wrong format
        assertFalse(SessionDate.isValidDate("24 September 2024")); // wrong format
        assertFalse(SessionDate.isValidDate("Oct 24 2024")); // wrong format
        assertFalse(SessionDate.isValidDate("2024 24 Sep")); // wrong format
        assertFalse(SessionDate.isValidDate("333 Mar 2024")); // invalid day
        assertFalse(SessionDate.isValidDate("30 m 2024")); // invalid month
        assertFalse(SessionDate.isValidDate("30 Mar 024")); // invalid year
        assertFalse(SessionDate.isValidDate(" Mar 2024")); // missing day
        assertFalse(SessionDate.isValidDate("29  2024")); // missing month
        assertFalse(SessionDate.isValidDate("30 Mar ")); // missing year

        // valid dates
        assertTrue(SessionDate.isValidDate("24 Sep 2024"));
        assertTrue(SessionDate.isValidDate("01 Jan 2000"));
        assertTrue(SessionDate.isValidDate("15 Aug 2023"));
        assertTrue(SessionDate.isValidDate("1 Jan 2000")); // no zero-padding
        assertTrue(SessionDate.isValidDate("15 aUg 2023")); // wrong case
        assertTrue(SessionDate.isValidDate("4 fEB 1989")); //both
    }

    @Test
    public void equals() {
        SessionDate sessionDate = new SessionDate("4 Sep 2024");

        // same values -> returns true
        assertTrue(sessionDate.equals(new SessionDate("4 Sep 2024")));

        // same after formatting -> returns true
        assertTrue(sessionDate.equals(new SessionDate("04 Sep 2024")));
        assertTrue(sessionDate.equals(new SessionDate("4 sep 2024")));
        assertTrue(sessionDate.equals(new SessionDate("4 SeP 2024")));

        // same object -> returns true
        assertTrue(sessionDate.equals(sessionDate));

        // null -> returns false
        assertFalse(sessionDate.equals(null));

        // different types -> returns false
        assertFalse(sessionDate.equals(5.0f));

        // different values -> returns false
        assertFalse(sessionDate.equals(new SessionDate("25 Sep 2024")));
    }
}
