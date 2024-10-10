package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void testEquals() {
        Order order = Order.of("Cake");
        assertEquals(order, Order.of("Cake"));
        assertEquals(Order.of("Coffee"), Order.of("Coffee"));

        assertNotEquals(order, new Object());

        assertNotEquals(Order.of("Cake"), Order.of("Pizza"));
        assertNotEquals("Cake", Order.of("Cake"));
    }

    @Test
    void hashCode_order_orderCanBeHashed() {
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
