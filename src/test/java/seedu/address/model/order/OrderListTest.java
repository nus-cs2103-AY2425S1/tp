package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;
import seedu.address.testutil.PersonBuilder;

public class OrderListTest {
    private static class TestOrderList extends OrderList<Order> {
        // Concrete implementation of abstract class for testing
    }

    private TestOrderList orderList;
    private Order order1;
    private Order order2;
    private Order order3;

    @BeforeEach
    public void setUp() {
        orderList = new TestOrderList();
        Person person = new PersonBuilder().build();
        List<Product> items = List.of(new Ingredient(1, "Flour", 10.00));

        // Create orders with different statuses to test sorting
        order1 = new CustomerOrder(person, items, OrderStatus.PENDING, new Remark("Test 1"));
        order2 = new CustomerOrder(person, items, OrderStatus.COMPLETED, new Remark("Test 2"));
        order3 = new CustomerOrder(person, items, OrderStatus.CANCELLED, new Remark("Test 3"));
    }

    @Test
    public void addOrder_nullOrder_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> orderList.addOrder(null));
    }

    @Test
    public void addOrder_validOrder_addsOrderAndSorts() {
        orderList.addOrder(order2); // COMPLETED
        orderList.addOrder(order1); // PENDING
        orderList.addOrder(order3); // CANCELLED

        ObservableList<Order> orders = orderList.getOrders();
        assertEquals(3, orders.size());

        // Verify orders are sorted by status (PENDING, COMPLETED, CANCELLED)
        assertEquals(OrderStatus.PENDING, orders.get(0).getStatus());
        assertEquals(OrderStatus.COMPLETED, orders.get(1).getStatus());
        assertEquals(OrderStatus.CANCELLED, orders.get(2).getStatus());
    }

    @Test
    public void getOrders_modifyReturnedList_throwsUnsupportedOperationException() {
        orderList.addOrder(order1);
        ObservableList<Order> orders = orderList.getOrders();

        assertThrows(UnsupportedOperationException.class, () -> orders.add(order2));
        assertThrows(UnsupportedOperationException.class, () -> orders.remove(0));
    }

    @Test
    public void removeOrder_validIndex_removesOrder() {
        orderList.addOrder(order1);
        orderList.addOrder(order2);

        orderList.removeOrder(0);

        assertEquals(1, orderList.getOrders().size());
        assertEquals(order2, orderList.getOrders().get(0));
    }

    @Test
    public void removeOrder_invalidIndex_throwsIndexOutOfBoundsException() {
        orderList.addOrder(order1);

        // Test negative index
        assertThrows(IndexOutOfBoundsException.class, () -> orderList.removeOrder(-1));

        // Test index equal to size
        assertThrows(IndexOutOfBoundsException.class, () -> orderList.removeOrder(1));

        // Test index greater than size
        assertThrows(IndexOutOfBoundsException.class, () -> orderList.removeOrder(2));
    }

    @Test
    public void getOrder_validIndex_returnsOrder() {
        orderList.addOrder(order1);
        orderList.addOrder(order2);

        assertEquals(order1, orderList.getOrder(0));
        assertEquals(order2, orderList.getOrder(1));
    }

    @Test
    public void getOrder_invalidIndex_throwsIndexOutOfBoundsException() {
        orderList.addOrder(order1);

        // Test negative index
        assertThrows(IndexOutOfBoundsException.class, () -> orderList.getOrder(-1));

        // Test index equal to size
        assertThrows(IndexOutOfBoundsException.class, () -> orderList.getOrder(1));

        // Test index greater than size
        assertThrows(IndexOutOfBoundsException.class, () -> orderList.getOrder(2));
    }

    @Test
    public void viewOrders_emptyList_returnsEmptyString() {
        assertEquals("", orderList.viewOrders());
    }

    @Test
    public void viewOrders_nonEmptyList_returnsFormattedString() {
        orderList.addOrder(order1);
        orderList.addOrder(order2);

        String expectedOutput = String.format("Order 1\n%s\nOrder 2\n%s\n",
                order1.viewOrder(),
                order2.viewOrder());

        assertEquals(expectedOutput, orderList.viewOrders());
    }

    @Test
    public void toString_callsViewOrders() {
        orderList.addOrder(order1);
        orderList.addOrder(order2);

        assertEquals(orderList.viewOrders(), orderList.toString());
    }
}