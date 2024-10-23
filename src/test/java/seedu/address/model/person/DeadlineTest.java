package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline = "";
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // invalid deadlines
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("31-02-2020")); // invalid date (February 31st doesn't exist)
        assertFalse(Deadline.isValidDeadline("2020-12-12")); // incorrect format (should be dd-MM-yyyy)
        assertFalse(Deadline.isValidDeadline("12/12/2020")); // slashes instead of dashes
        assertFalse(Deadline.isValidDeadline("32-01-2020")); // invalid day
        assertFalse(Deadline.isValidDeadline("15-13-2020")); // invalid month
        assertFalse(Deadline.isValidDeadline("15-12-20")); // invalid year (too short)
        assertFalse(Deadline.isValidDeadline("15- 12 -20")); // spaces in between
        assertFalse(Deadline.isValidDeadline("10 -12-20")); // spaces in between
        assertFalse(Deadline.isValidDeadline("10-12 -20")); // spaces in between

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("01-01-2020")); // valid format, start of the year
        assertTrue(Deadline.isValidDeadline("29-02-2020")); // valid leap year date
        assertTrue(Deadline.isValidDeadline("31-12-2020")); // valid date, end of the year
        assertTrue(Deadline.isValidDeadline("10-10-2022")); // arbitrary valid date
        assertTrue(Deadline.isValidDeadline("15-07-2021")); // another valid date
    }

    @Test
    public void equals() {
        Deadline deadline = new Deadline("01-01-2020");

        // same values -> returns true
        assertTrue(deadline.equals(new Deadline("01-01-2020")));

        // same object -> returns true
        assertTrue(deadline.equals(deadline));

        // null -> returns false
        assertFalse(deadline.equals(null));

        // different types -> returns false
        assertFalse(deadline.equals(5.0f));

        // different values -> returns false
        assertFalse(deadline.equals(new Deadline("02-01-2020")));
    }
}
