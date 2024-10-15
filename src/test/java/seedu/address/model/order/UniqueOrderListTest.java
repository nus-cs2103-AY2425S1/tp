package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;

class UniqueOrderListTest {

    @Test
    void contains_addOrder_success() {
        UniqueOrderList list = new UniqueOrderList();
        list.add(new Order("cake"));
        assertTrue(list.contains(new Order("cake")));
        assertFalse(list.contains(new Order("pizza")));
    }

    @Test
    void add_duplicateOrder_throwsDuplicateOrderException() {
        UniqueOrderList list = new UniqueOrderList();
        list.add(new Order("cake"));
        assertThrows(DuplicateOrderException.class, () -> list.add(new Order("cake")));
    }

    @Test
    void remove_orderDoesNotExist_throwsOrderNotFoundException() {
        UniqueOrderList list = new UniqueOrderList();
        assertThrows(OrderNotFoundException.class, () -> list.remove(new Order("cake")));
    }
}
