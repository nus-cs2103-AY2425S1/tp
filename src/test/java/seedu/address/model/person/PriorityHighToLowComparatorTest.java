package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PriorityHighToLowComparatorTest {

    @Test
    public void equals() {
        PriorityHighToLowComparator highToLowComparator = new PriorityHighToLowComparator();
        PriorityLowToHighComparator lowToHighComparator = new PriorityLowToHighComparator();

        assertTrue(highToLowComparator.equals(highToLowComparator));
        assertTrue(highToLowComparator.equals(new PriorityHighToLowComparator()));
        assertFalse(highToLowComparator.equals(lowToHighComparator));
        assertFalse(highToLowComparator.equals(null));
    }
}
