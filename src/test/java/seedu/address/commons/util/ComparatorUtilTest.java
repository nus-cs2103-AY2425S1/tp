package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.DaysAttended;

public class ComparatorUtilTest {

    @Test
    public void compareClassStrings_sameNumberDifferentSection_correctComparison() {
        assertTrue(ComparatorUtil.compareClassStrings("2A", "2B") < 0);
        assertTrue(ComparatorUtil.compareClassStrings("2C", "2A") > 0);
        assertEquals(0, ComparatorUtil.compareClassStrings("3D", "3D"));
    }

    @Test
    public void compareClassStrings_differentNumber_correctComparison() {
        assertTrue(ComparatorUtil.compareClassStrings("1A", "2A") < 0);
        assertTrue(ComparatorUtil.compareClassStrings("4B", "3B") > 0);
        assertTrue(ComparatorUtil.compareClassStrings("10A", "2A") > 0);
    }

    @Test
    public void compareClassStrings_invalidNumericFormat_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> ComparatorUtil.compareClassStrings("1A", "B"));
    }

    @Test
    public void getPrimaryClassForSorting_nonEmptyList_correctPrimaryClass() {
        List<String> classList = Arrays.asList("4B", "2A", "3C", "1D");
        String primaryClass = ComparatorUtil.getPrimaryClassForSorting(classList);
        assertEquals("1D", primaryClass);
    }

    @Test
    public void getPrimaryClassForSorting_emptyList_returnsEmptyString() {
        List<String> classList = Arrays.asList();
        String primaryClass = ComparatorUtil.getPrimaryClassForSorting(classList);
        assertEquals("", primaryClass);
    }

    @Test
    public void getPrimaryClassForSorting_singleElement_returnsSameElement() {
        List<String> classList = Arrays.asList("2A");
        String primaryClass = ComparatorUtil.getPrimaryClassForSorting(classList);
        assertEquals("2A", primaryClass);
    }

    @Test
    public void getPrimaryClassForSorting_nullList_throwsException() {
        assertThrows(NullPointerException.class, () -> ComparatorUtil.getPrimaryClassForSorting(null));
    }

    @Test
    public void compareDaysAttended_bothNonNull_correctComparisonDescending() {
        DaysAttended days1 = new DaysAttended(3);
        DaysAttended days2 = new DaysAttended(5);
        DaysAttended days3 = new DaysAttended(3);

        Comparator<DaysAttended> comparator = ComparatorUtil.getDaysAttendedComparator();

        // Since we want to sort in descending order, 5 should come before 3
        assertTrue(comparator.compare(days2, days1) < 0); // 5 > 3
        assertTrue(comparator.compare(days1, days2) > 0); // 3 < 5
        assertEquals(0, comparator.compare(days1, days3)); // 3 == 3
    }

    @Test
    public void compareDaysAttended_oneNull_correctComparison() {
        DaysAttended daysNonNull = new DaysAttended(3);
        DaysAttended daysNull = null;

        Comparator<DaysAttended> comparator = ComparatorUtil.getDaysAttendedComparator();

        // Non-null should come before null
        assertTrue(comparator.compare(daysNonNull, daysNull) < 0);
        assertTrue(comparator.compare(daysNull, daysNonNull) > 0);
    }

    @Test
    public void compareDaysAttended_bothNull_returnsZero() {
        DaysAttended daysNull1 = null;
        DaysAttended daysNull2 = null;

        Comparator<DaysAttended> comparator = ComparatorUtil.getDaysAttendedComparator();

        assertEquals(0, comparator.compare(daysNull1, daysNull2));
    }

    @Test
    public void compareDaysAttended_sameValue_correctComparison() {
        DaysAttended days1 = new DaysAttended(5);
        DaysAttended days2 = new DaysAttended(5);

        Comparator<DaysAttended> comparator = ComparatorUtil.getDaysAttendedComparator();

        assertEquals(0, comparator.compare(days1, days2));
    }
}
