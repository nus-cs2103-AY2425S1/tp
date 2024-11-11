package seedu.sellsavvy.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_ITEM_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_QUANTITY_ATLAS;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalOrders.ATLAS;
import static seedu.sellsavvy.testutil.TypicalOrders.BOTTLE;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.model.order.exceptions.OrderNotFoundException;
import seedu.sellsavvy.testutil.OrderBuilder;

public class OrderListTest {
    private final OrderList orderList = new OrderList();

    @Test
    public void contains_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.contains(null));
    }

    @Test
    public void contains_orderNotInList_returnsFalse() {
        assertFalse(orderList.contains(ATLAS));
    }

    @Test
    public void contains_orderInList_returnsTrue() {
        orderList.add(ATLAS);
        assertTrue(orderList.contains(ATLAS));
    }

    @Test
    public void contains_orderWithSameFieldsInList_returnsTrue() {
        orderList.add(ATLAS);
        Order atlasCopy = new OrderBuilder(ATLAS).build();
        assertTrue(orderList.contains(atlasCopy));
    }

    @Test
    public void containsDuplicateOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.containsDuplicateOrder(null));
    }

    @Test
    public void containsDuplicateOrder_orderNotInList_returnsFalse() {
        assertFalse(orderList.containsDuplicateOrder(ATLAS));
    }

    @Test
    public void containsDuplicateOrder_onlySameOrderInList_returnsFalse() {
        orderList.add(ATLAS);
        assertFalse(orderList.containsDuplicateOrder(ATLAS));
    }

    @Test
    public void containsDuplicateOrder_differentOrderWithSameFieldsInList_returnsTrue() {
        orderList.add(ATLAS);
        Order atlasCopy = new OrderBuilder(ATLAS).build();
        assertTrue(orderList.containsDuplicateOrder(atlasCopy));
    }

    @Test
    public void containsSimilarOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.containsSimilarOrder(null));
    }

    @Test
    public void containsSimilarOrder_orderNotInList_returnsFalse() {
        assertFalse(orderList.containsSimilarOrder(ATLAS));
    }

    @Test
    public void containsSimilarOrder_onlySameOrderInList_returnsFalse() {
        orderList.add(ATLAS);
        assertFalse(orderList.containsSimilarOrder(ATLAS));
    }

    @Test
    public void containsSimilarOrder_differentOrderWithSameFieldsInList_returnsTrue() {
        orderList.add(ATLAS);
        Order atlasCopy = new OrderBuilder(ATLAS).build();
        assertTrue(orderList.containsSimilarOrder(atlasCopy));
    }

    @Test
    public void containsSimilarOrder_differentOrderWithSimilarFieldsInList_returnsTrue() {
        orderList.add(ATLAS);
        Order atlasFailedCopy = new OrderBuilder(ATLAS).withItem(ATLAS.getItem().fullDescription.toLowerCase()).build();
        assertTrue(orderList.containsSimilarOrder(atlasFailedCopy));
    }

    @Test
    public void add_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.add(null));
    }

    @Test
    public void setOrder_nullTargetOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.setOrder(null, ATLAS));
    }

    @Test
    public void setOrder_nullEditedOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.setOrder(ATLAS, null));
    }

    @Test
    public void setOrder_targetOrderNotInList_throwsOrderNotFoundException() {
        assertThrows(OrderNotFoundException.class, () -> orderList.setOrder(ATLAS, ATLAS));
    }

    @Test
    public void setOrder_validEditedOrder_success() {
        orderList.add(ATLAS);
        orderList.setOrder(ATLAS, ATLAS);
        OrderList expectedOrderList = new OrderList();
        expectedOrderList.add(ATLAS);
        assertEquals(expectedOrderList, orderList);
    }

    @Test
    public void setOrder_duplicateOrderInList_throwsOrderNotFoundException() {
        orderList.add(ATLAS);
        Order orderCopy = new OrderBuilder().withItem(VALID_ITEM_ATLAS)
                .withQuantity(VALID_QUANTITY_ATLAS).withDate(VALID_DATE_ATLAS).build();
        assertThrows(OrderNotFoundException.class, () -> orderList.setOrder(orderCopy, orderCopy));
    }

    @Test
    public void remove_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.remove(null));
    }

    @Test
    public void remove_orderDoesNotExist_throwsOrderNotFoundException() {
        assertThrows(OrderNotFoundException.class, () -> orderList.remove(ATLAS));
    }

    @Test
    public void remove_existingOrder_removesOrder() {
        orderList.add(ATLAS);
        orderList.remove(ATLAS);
        OrderList expectedOrderList = new OrderList();
        assertEquals(expectedOrderList, orderList);
    }

    @Test
    public void setOrders_nullOrderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.setOrders((OrderList) null));
    }

    @Test
    public void setOrders_validOrderList_success() {
        orderList.add(ATLAS);
        OrderList expectedOrderList = new OrderList();
        expectedOrderList.add(BOTTLE);
        orderList.setOrders(expectedOrderList);
        assertEquals(expectedOrderList, orderList);
    }

    @Test
    public void setOrders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.setOrders((List<Order>) null));
    }

    @Test
    public void setOrders_validList_success() {
        orderList.add(ATLAS);
        List<Order> orders = Collections.singletonList(BOTTLE);
        orderList.setOrders(orders);
        OrderList expectedOrderList = new OrderList();
        expectedOrderList.add(BOTTLE);
        assertEquals(expectedOrderList, orderList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> orderList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(orderList.asUnmodifiableObservableList().toString(), orderList.toString());
    }

    @Test
    public void createCopy() {
        orderList.add(ATLAS);
        OrderList orderListCopy = orderList.createCopy();
        assertEquals(orderList, orderListCopy);
        assertNotSame(orderList, orderListCopy);
    }
}
