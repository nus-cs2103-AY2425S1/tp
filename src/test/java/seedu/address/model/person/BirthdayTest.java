package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BirthdayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "32-13-2020"; // Invalid date
        assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday() {
        // null birthday
        assertFalse(Birthday.isValidBirthday(null));

        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("32-12-2020")); // invalid day

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("01-01-2000"));
        assertTrue(Birthday.isValidBirthday("29-02-2000")); // leap year
        assertTrue(Birthday.isValidBirthday("15-08-1995"));
        assertTrue(Birthday.isValidBirthday("5-7-2010")); // single-digit day and month
        assertTrue(Birthday.isValidBirthday("01/01/2000")); // alternative format
    }

    @Test
    public void equals() {
        Birthday birthday = new Birthday("01-01-2000");

        // same values -> returns true
        assertTrue(birthday.equals(new Birthday("01-01-2000")));

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different types -> returns false
        assertFalse(birthday.equals(5.0f));

        // different values -> returns false
        assertFalse(birthday.equals(new Birthday("02-01-2000")));
    }
}
