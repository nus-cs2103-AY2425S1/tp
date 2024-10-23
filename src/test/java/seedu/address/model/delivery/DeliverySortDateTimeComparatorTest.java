package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.SortOrder;
import seedu.address.testutil.DeliveryBuilder;

public class DeliverySortDateTimeComparatorTest {
    @Test
    public void compare_ascending_returnsNegativeInt() {
        DeliverySortDateTimeComparator comparator = new DeliverySortDateTimeComparator(new SortOrder("a"));
        Delivery delivery1 = new DeliveryBuilder().withDeliveryTime("20-01-2024 16:25").build();
        Delivery delivery2 = new DeliveryBuilder().withDeliveryTime("04-07-2024 12:40").build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result < 0);
    }

    @Test
    public void compare_descending_returnsPositiveInt() {
        DeliverySortDateTimeComparator comparator = new DeliverySortDateTimeComparator(new SortOrder("d"));
        Delivery delivery1 = new DeliveryBuilder().withDeliveryTime("14-02-2024 16:25").build();
        Delivery delivery2 = new DeliveryBuilder().withDeliveryTime("09-08-2024 12:40").build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result > 0);
    }

    @Test
    public void compare_ascendingEqualInputs_returnsZero() {
        DeliverySortDateTimeComparator comparator = new DeliverySortDateTimeComparator(new SortOrder("d"));
        Delivery delivery1 = new DeliveryBuilder().withDeliveryTime("09-08-2024 12:40").build();
        Delivery delivery2 = new DeliveryBuilder().withDeliveryTime("09-08-2024 12:40").build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result == 0);
    }

    @Test
    public void equals() {
        DeliverySortDateTimeComparator comparator = new DeliverySortDateTimeComparator(new SortOrder("a"));

        // same values -> returns true
        assertTrue(comparator.equals(new DeliverySortDateTimeComparator(new SortOrder("a"))));

        // same object -> returns true
        assertTrue(comparator.equals(comparator));

        // null -> returns false
        assertFalse(comparator.equals(null));

        // different types -> returns false
        assertFalse(comparator.equals(5));

        // different values -> returns false
        assertFalse(comparator.equals(new DeliverySortDateTimeComparator(new SortOrder("d"))));
    }
}
