package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.SortOrder;
import seedu.address.testutil.DeliveryBuilder;

public class DeliverySortCostComparatorTest {
    @Test
    public void compare_ascending_returnsNegativeInt() {
        DeliverySortCostComparator comparator = new DeliverySortCostComparator(new SortOrder("a"));
        Delivery delivery1 = new DeliveryBuilder().withCost("10.50").build();
        Delivery delivery2 = new DeliveryBuilder().withCost("23").build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result < 0);
    }

    @Test
    public void compare_descending_returnsPositiveInt() {
        DeliverySortCostComparator comparator = new DeliverySortCostComparator(new SortOrder("d"));
        Delivery delivery1 = new DeliveryBuilder().withCost("13.60").build();
        Delivery delivery2 = new DeliveryBuilder().withCost("67.20").build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result > 0);
    }

    @Test
    public void compare_ascendingEqualInputs_returnsZero() {
        DeliverySortCostComparator comparator = new DeliverySortCostComparator(new SortOrder("d"));
        Delivery delivery1 = new DeliveryBuilder().withCost("13.60").build();
        Delivery delivery2 = new DeliveryBuilder().withCost("13.60").build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result == 0);
    }

    @Test
    public void equals() {
        DeliverySortCostComparator comparator = new DeliverySortCostComparator(new SortOrder("a"));

        // same values -> returns true
        assertTrue(comparator.equals(new DeliverySortCostComparator(new SortOrder("a"))));

        // same object -> returns true
        assertTrue(comparator.equals(comparator));

        // null -> returns false
        assertFalse(comparator.equals(null));

        // different types -> returns false
        assertFalse(comparator.equals(5));

        // different values -> returns false
        assertFalse(comparator.equals(new DeliverySortCostComparator(new SortOrder("d"))));
    }
}
