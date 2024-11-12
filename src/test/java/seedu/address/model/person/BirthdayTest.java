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
        String invalidBirthday = "";
        assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday() {
        // null birthday
        assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("91")); // not formatted
        assertFalse(Birthday.isValidBirthday("phone")); // non-numeric
        assertFalse(Birthday.isValidBirthday("40 10 2991")); // more than 31 days
        assertFalse(Birthday.isValidBirthday("9312 1534")); // not formatted
        assertFalse(Birthday.isValidBirthday("31 02 2002")); // february should have <= 28 days

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("01 01 2002")); // first of month
        assertTrue(Birthday.isValidBirthday("31 10 1908")); // last of month
        assertTrue(Birthday.isValidBirthday("28 02 2002")); // last of february
        assertTrue(Birthday.isValidBirthday("15 04 1987")); // random
    }

    @Test
    public void equals() {
        Birthday birthday = new Birthday("01 01 2001");

        // same values -> returns true
        assertTrue(birthday.equals(new Birthday("01 01 2001")));

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different types -> returns false
        assertFalse(birthday.equals(5.0f));

        // different values -> returns false
        assertFalse(birthday.equals(new Birthday("01 01 2002")));
    }
}
