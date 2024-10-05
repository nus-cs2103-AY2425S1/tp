package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Count(null));
    }

    @Test
    public void constructor_invalidCount_throwsIllegalArgumentException() {
        String invalidCount = "";
        assertThrows(IllegalArgumentException.class, () -> new Count(invalidCount));
    }

    @Test
    public void isValidCount() {
        // null count value
        assertThrows(NullPointerException.class, () -> Count.isValidCount(null));

        // invalid count values
        assertFalse(Count.isValidCount("")); // empty string
        assertFalse(Count.isValidCount(" ")); // spaces only
        assertFalse(Count.isValidCount("01")); // contains leading zeros
        assertFalse(Count.isValidCount("0")); // zero
        assertFalse(Count.isValidCount("-1")); // negative number
        assertFalse(Count.isValidCount("0.1")); // non-integer
        assertFalse(Count.isValidCount("count")); // non-numeric
        assertFalse(Count.isValidCount("1z1")); // contains non-numeric characters
        assertFalse(Count.isValidCount("9312 1534")); // spaces within digits

        // valid count values
        assertTrue(Count.isValidCount("1"));
        assertTrue(Count.isValidCount("124293842033123")); // long numbers
    }

    @Test
    public void equals() {
        Count count = new Count("999");

        // same values -> returns true
        assertTrue(count.equals(new Count("999")));

        // same object -> returns true
        assertTrue(count.equals(count));

        // null -> returns false
        assertFalse(count.equals(null));

        // different types -> returns false
        assertFalse(count.equals(5.0f));

        // different values -> returns false
        assertFalse(count.equals(new Count("995")));
    }
}
