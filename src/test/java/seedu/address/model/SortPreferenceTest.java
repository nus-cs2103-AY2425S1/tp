package seedu.address.model;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.DateDistantToRecentComparator;
import seedu.address.model.person.DateRecentToDistantComparator;
import seedu.address.model.person.PriorityHighToLowComparator;
import seedu.address.model.person.PriorityLowToHighComparator;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

public class SortPreferenceTest {
    // Test for valid sort preference input
    @Test
    public void constructor_validSortPreference() {
        // Testing valid inputs
        SortPreference sp1 = new SortPreference("high");
        SortPreference sp2 = new SortPreference("low");
        SortPreference sp3 = new SortPreference("recent");
        SortPreference sp4 = new SortPreference("distant");
        SortPreference sp5 = new SortPreference("default");

        assertEquals("high", sp1.value);
        assertEquals("low", sp2.value);
        assertEquals("recent", sp3.value);
        assertEquals("distant", sp4.value);
        assertEquals("default", sp5.value);
    }

    // Test for invalid
    @Test
    public void constructor_invalidSortPreference() {
        // null -> throws null pointer exception
        assertThrows(NullPointerException.class, () -> new SortPreference(null));

        // valid but with space -> throws illegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new SortPreference(" high "));

        // valid but in capital -> throws illegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new SortPreference("HIGH"));

        // completely invalid -> throws illegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new SortPreference("completely invalid"));
    }
    // Test for isValidSortPreference method - valid inputs
    @Test
    public void isValidSortPreference_validInput_returnsTrue() {
        // Test all possible valid inputs
        assertTrue(SortPreference.isValidSortPreference("high"));
        assertTrue(SortPreference.isValidSortPreference("low"));
        assertTrue(SortPreference.isValidSortPreference("recent"));
        assertTrue(SortPreference.isValidSortPreference("distant"));
        assertTrue(SortPreference.isValidSortPreference("default"));
    }

    // Test for isValidSortPreference method - invalid inputs
    @Test
    public void isValidSortPreference_invalidInput_returnsFalse() {
        // input is null -> returns False
        assertFalse(SortPreference.isValidSortPreference(null));
        // input is valid but with space -> returns False
        assertFalse(SortPreference.isValidSortPreference(" high "));
        // input is valid but with caps -> returns False
        assertFalse(SortPreference.isValidSortPreference("HiGh"));
        // input is completely invalid -> returns False
        assertFalse(SortPreference.isValidSortPreference("medium"));
    }

    // Test for getComparator method - valid inputs
    @Test
    public void getComparator_validSortPreferences_returnsCorrectComparator() {
        // High to Low Priority comparator for "high"
        SortPreference high = new SortPreference("high");
        assertTrue(high.getComparator().equals(new PriorityHighToLowComparator()));

        // Low to High Priority comparator for "low"
        SortPreference low = new SortPreference("low");
        assertTrue(low.getComparator().equals(new PriorityLowToHighComparator()));

        // Distant to Recent Date comparator for "distant"
        SortPreference distant = new SortPreference("distant");
        assertTrue(distant.getComparator().equals(new DateDistantToRecentComparator()));

        // Recent to Distant Date comparator for "recent"
        SortPreference recent = new SortPreference("recent");
        assertTrue(recent.getComparator().equals(new DateRecentToDistantComparator()));

        // default SortPreference returns null
        SortPreference standard = new SortPreference("default");
        assertTrue(standard.getComparator() == null);
    }

    // Test for equals method
    @Test
    public void getComparator_invalidSortPreferences_returnsNull() {
        SortPreference high = new SortPreference("high");
        SortPreference standard = new SortPreference("default");
        // Same object should return true
        assertEquals(high, high);

        // Same sort preference should return true
        assertEquals(high, new SortPreference("high"));

        // Different sort preference should return false
        assertNotEquals(high, standard);

        // null returns false
        assertNotEquals(high, null);
    }
}
