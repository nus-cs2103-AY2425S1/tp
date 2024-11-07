package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ClassComparatorUtilTest {

    @Test
    public void compareClassStrings_sameNumberDifferentSection_correctComparison() {
        assertTrue(ClassComparatorUtil.compareClassStrings("2A", "2B") < 0);
        assertTrue(ClassComparatorUtil.compareClassStrings("2C", "2A") > 0);
        assertEquals(0, ClassComparatorUtil.compareClassStrings("3D", "3D"));
    }

    @Test
    public void compareClassStrings_differentNumber_correctComparison() {
        assertTrue(ClassComparatorUtil.compareClassStrings("1A", "2A") < 0);
        assertTrue(ClassComparatorUtil.compareClassStrings("4B", "3B") > 0);
        assertTrue(ClassComparatorUtil.compareClassStrings("10A", "2A") > 0);
    }

    @Test
    public void getPrimaryClassForSorting_nonEmptyList_correctPrimaryClass() {
        List<String> classList = Arrays.asList("4B", "2A", "3C", "1D");
        String primaryClass = ClassComparatorUtil.getPrimaryClassForSorting(classList);
        assertEquals("1D", primaryClass);
    }

    @Test
    public void getPrimaryClassForSorting_emptyList_returnsEmptyString() {
        List<String> classList = Arrays.asList();
        String primaryClass = ClassComparatorUtil.getPrimaryClassForSorting(classList);
        assertEquals("", primaryClass);
    }

    @Test
    public void getPrimaryClassForSorting_singleElement_returnsSameElement() {
        List<String> classList = Arrays.asList("2A");
        String primaryClass = ClassComparatorUtil.getPrimaryClassForSorting(classList);
        assertEquals("2A", primaryClass);
    }
}
