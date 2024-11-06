package seedu.academyassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class YearGroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new YearGroup(null));
    }

    @Test
    public void constructor_invalidYearGroup_throwsIllegalArgumentException() {
        String invalidYearGroup = "";
        assertThrows(IllegalArgumentException.class, () -> new YearGroup(invalidYearGroup));
    }

    @Test
    public void isValidYearGroup() {
        // null year group
        assertThrows(NullPointerException.class, () -> YearGroup.isValidYearGroup(null));

        // invalid year groups
        assertFalse(YearGroup.isValidYearGroup("")); // empty string
        assertFalse(YearGroup.isValidYearGroup(" ")); // spaces only
        assertFalse(YearGroup.isValidYearGroup("0")); // less than 1
        assertFalse(YearGroup.isValidYearGroup("-4")); // negative number
        assertFalse(YearGroup.isValidYearGroup("14")); // more than 13
        assertFalse(YearGroup.isValidYearGroup("p")); // not a number
        assertFalse(YearGroup.isValidYearGroup("#")); // not a number

        // valid year groups
        assertTrue(YearGroup.isValidYearGroup("1")); // edge case
        assertTrue(YearGroup.isValidYearGroup("13")); // edge case
        assertTrue(YearGroup.isValidYearGroup("5"));
    }

    @Test
    public void equals() {
        YearGroup yearGroup = new YearGroup("3");

        // same values -> returns true
        assertTrue(yearGroup.equals(new YearGroup("3")));

        // same object -> returns true
        assertTrue(yearGroup.equals(yearGroup));

        // null -> returns false
        assertFalse(yearGroup.equals(null));

        // different types -> returns false
        assertFalse(yearGroup.equals(5.0f));

        // different values -> returns false
        assertFalse(yearGroup.equals(new YearGroup("2")));
    }
}
