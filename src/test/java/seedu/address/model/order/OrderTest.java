package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    public void equalsTest() {
        Order order = new Order("Cake");
        Order order2 = new Order("Test");
        Order order3 = new Order("Cake");
        assertEquals(order, order3);
        assertNotEquals(order, order2);
        assertNotEquals(order, new Object());
    }

    @Test
    public void toStringTest() {
        Order order = new Order("Cake");
        assertEquals("cake", order.toString());
    }

}
