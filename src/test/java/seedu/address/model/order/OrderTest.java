package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    public void constructor_invalidOrderName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Order(" cake"));
        assertThrows(IllegalArgumentException.class, () -> new Order("c.ke"));
        assertThrows(IllegalArgumentException.class, () -> new Order("ca/ke"));
        assertThrows(IllegalArgumentException.class, () -> new Order(""));
    }

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
