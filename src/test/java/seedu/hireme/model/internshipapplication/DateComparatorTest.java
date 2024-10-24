package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.TypicalInternshipApplications.APPLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.BOFA;

import org.junit.jupiter.api.Test;

public class DateComparatorTest {

    @Test
    public void equals() {
        DateComparator firstComparator = new DateComparator(true);
        DateComparator secondComparator = new DateComparator(false);

        // same object -> returns true
        assertTrue(firstComparator.equals(firstComparator));

        // same values -> returns true
        DateComparator firstComparatorCopy = new DateComparator(true);
        assertTrue(firstComparator.equals(firstComparatorCopy));

        // different types -> returns false
        assertFalse(firstComparator.equals(1));

        // null -> returns false
        assertFalse(firstComparator.equals(null));

        // different comparator -> returns false
        assertFalse(firstComparator.equals(secondComparator));
    }

    @Test
    public void compare_internshipApplicationsOrder_returnsInteger() {
        // Sort by earliest
        DateComparator comparator1 = new DateComparator(true);
        assertTrue(comparator1.compare(APPLE, BOFA) < 0);
        assertTrue(comparator1.compare(BOFA, APPLE) > 0);

        // Same order
        assertEquals(0, comparator1.compare(APPLE, APPLE));
        assertEquals(0, comparator1.compare(BOFA, BOFA));

        // Sort by latest
        DateComparator comparator2 = new DateComparator(false);
        assertTrue(comparator2.compare(BOFA, APPLE) < 0);
        assertTrue(comparator2.compare(APPLE, BOFA) > 0);

        // Same order
        assertEquals(0, comparator2.compare(APPLE, APPLE));
        assertEquals(0, comparator2.compare(BOFA, BOFA));
    }

    @Test
    public void toStringMethod() {
        DateComparator comparator1 = new DateComparator(true);
        String expected1 = DateComparator.class.getCanonicalName() + "{isEarliestOrder=" + true + "}";
        assertEquals(expected1, comparator1.toString());

        DateComparator comparator2 = new DateComparator(false);
        String expected2 = DateComparator.class.getCanonicalName() + "{isEarliestOrder=" + false + "}";
        assertEquals(expected2, comparator2.toString());
    }
}
