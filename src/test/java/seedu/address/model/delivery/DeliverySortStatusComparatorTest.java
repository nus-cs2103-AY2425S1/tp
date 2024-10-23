package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.SortOrder;
import seedu.address.testutil.DeliveryBuilder;

public class DeliverySortStatusComparatorTest {
    @Test
    public void compare_ascending_returnsNegativeInt() {
        DeliverySortStatusComparator comparator = new DeliverySortStatusComparator(new SortOrder("a"));
        Delivery delivery1 = new DeliveryBuilder().withStatus(Status.PENDING).build();
        Delivery delivery2 = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result < 0);
    }

    @Test
    public void compare_descending_returnsPositiveInt() {
        DeliverySortStatusComparator comparator = new DeliverySortStatusComparator(new SortOrder("d"));
        Delivery delivery1 = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        Delivery delivery2 = new DeliveryBuilder().withStatus(Status.CANCELLED).build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result > 0);
    }

    @Test
    public void compare_ascendingEqualInputs_returnsZero() {
        DeliverySortStatusComparator comparator = new DeliverySortStatusComparator(new SortOrder("d"));
        Delivery delivery1 = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        Delivery delivery2 = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        int result = comparator.compare(delivery1, delivery2);
        assertTrue(result == 0);
    }

    @Test
    public void equals() {
        DeliverySortStatusComparator comparator = new DeliverySortStatusComparator(new SortOrder("a"));

        // same values -> returns true
        assertTrue(comparator.equals(new DeliverySortStatusComparator(new SortOrder("a"))));

        // same object -> returns true
        assertTrue(comparator.equals(comparator));

        // null -> returns false
        assertFalse(comparator.equals(null));

        // different types -> returns false
        assertFalse(comparator.equals(5));

        // different values -> returns false
        assertFalse(comparator.equals(new DeliverySortStatusComparator(new SortOrder("d"))));
    }
}
