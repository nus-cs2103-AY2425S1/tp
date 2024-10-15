package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    public void toStringTest() {
        Order order = new Order("Cake");
        assertEquals("Cake", order.toString());
    }
}
