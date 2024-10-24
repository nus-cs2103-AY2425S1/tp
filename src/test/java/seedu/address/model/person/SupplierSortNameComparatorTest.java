package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.SortOrder;
import seedu.address.testutil.PersonBuilder;


public class SupplierSortNameComparatorTest {
    @Test
    public void compare_ascending_returnsNegativeInt() {
        SupplierSortNameComparator comparator = new SupplierSortNameComparator(new SortOrder("a"));
        Person supplier1 = new PersonBuilder().withName("Alice").build();
        Person supplier2 = new PersonBuilder().withName("Bob").build();
        int result = comparator.compare(supplier1, supplier2);
        assertTrue(result < 0);
    }
    @Test
    public void compare_descending_returnsPositiveInt() {
        SupplierSortNameComparator comparator = new SupplierSortNameComparator(new SortOrder("d"));
        Person supplier1 = new PersonBuilder().withName("Alice").build();
        Person supplier2 = new PersonBuilder().withName("Bob").build();
        int result = comparator.compare(supplier1, supplier2);
        assertTrue(result > 0);
    }
    @Test
    public void compare_ascendingEqualInputs_returnsZero() {
        SupplierSortNameComparator comparator = new SupplierSortNameComparator(new SortOrder("d"));
        Person supplier1 = new PersonBuilder().withName("Alice").build();
        Person supplier2 = new PersonBuilder().withName("Alice").build();
        int result = comparator.compare(supplier1, supplier2);
        assertTrue(result == 0);
    }
    @Test
    public void equals() {
        SupplierSortNameComparator comparator = new SupplierSortNameComparator(new SortOrder("a"));
        SupplierSortNameComparator sameComparator = new SupplierSortNameComparator(new SortOrder("a"));
        Object otherObject = new Object();

        // same values -> returns true
        assertTrue(comparator.equals(new SupplierSortNameComparator(new SortOrder("a"))));

        // same object -> returns true
        assertTrue(comparator.equals(sameComparator));
        //different object -> returns false
        assertFalse(comparator.equals(otherObject));
    }
}
