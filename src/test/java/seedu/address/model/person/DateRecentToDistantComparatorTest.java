package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateRecentToDistantComparatorTest {

    @Test
    public void equals() {
        DateRecentToDistantComparator recentToDistantComparator = new DateRecentToDistantComparator();
        DateDistantToRecentComparator distantToRecentComparator = new DateDistantToRecentComparator();

        assertTrue(recentToDistantComparator.equals(recentToDistantComparator));
        assertTrue(recentToDistantComparator.equals(new DateRecentToDistantComparator()));
        assertFalse(recentToDistantComparator.equals(distantToRecentComparator));
        assertFalse(recentToDistantComparator.equals(null));
    }
}
