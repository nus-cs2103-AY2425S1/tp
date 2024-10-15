package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

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
    void setOrders_newUniqueOrderList_success() {
        UniqueOrderList list1 = new UniqueOrderList();
        UniqueOrderList list2 = new UniqueOrderList();
        list1.add(new Order("cake"));
        list2.add(new Order("pizza"));
        list1.setOrders(list2);
        assertEquals(list1, list2);
    }

    @Test
    void setOrders_nonUniqueOrderList_throwsDuplicateOrderException() {
        UniqueOrderList list = new UniqueOrderList();
        ArrayList<Order> nonUniqueList = new ArrayList<>();
        list.add(new Order("cake"));
        nonUniqueList.add(new Order("pizza"));
        nonUniqueList.add(new Order("pizza"));
        assertThrows(DuplicateOrderException.class, () -> list.setOrders(nonUniqueList));
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

    @Test
    void equalsMethod() {
        UniqueOrderList list = new UniqueOrderList();
        UniqueOrderList list2 = new UniqueOrderList();
        list.add(new Order("cake"));
        list.add(new Order("pizza"));
        list2.setOrders(list);

        assertEquals(list, list);
        assertEquals(list, list2);
        assertNotEquals(list, null);
        assertNotEquals(list, 1);

    }

    @Test
    void toStringMethod() {
        UniqueOrderList list = new UniqueOrderList();
        list.add(new Order("cake"));
        list.add(new Order("pizza"));
        assertEquals(list.asUnmodifiableObservableList().toString(), list.toString());
    }
}
