package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void testEquals() {
        Order.removeAllOrder();
        Order order = Order.of("Cake");
        assertEquals(order, Order.of("Cake"));
        assertEquals(Order.of("Coffee"), Order.of("Coffee"));

        assertNotEquals(order, new Object());

        assertNotEquals(Order.of("Cake"), Order.of("Pizza"));
        assertNotEquals("Cake", Order.of("Cake"));
    }

    @Test
    void removeOrderTest() {
        Order.removeAllOrder();
        Order cake = Order.of("Cake");
        Order pizza = Order.of("Pizza");
        assertEquals(2, Order.getTotalOrderCount());
        Order.removeOrder("Cake");
        assertEquals(1, Order.getTotalOrderCount());
        assertFalse(Order.removeOrder("test"));
    }

    @Test
    void hashCode_order_orderCanBeHashed() {
        Order.removeAllOrder();
        HashMap<Order, Integer> m = new HashMap<>();

        m.put(Order.of("Cake"), 2);
        assertEquals(m.get(Order.of("Cake")), 2);

        m.put(Order.of("Cake"), 10);
        assertEquals(m.get(Order.of("Cake")), 10);

    }

    @Test
    void testToString() {
        assertEquals(Order.of("Cake").toString(), "Cake");
    }
}
