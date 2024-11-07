package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OrderStatusTest {
    @Test
    void toString_returnsCorrectDescription() {
        assertEquals("Pending", OrderStatus.PENDING.toString());
        assertEquals("Completed", OrderStatus.COMPLETED.toString());
        assertEquals("Cancelled", OrderStatus.CANCELLED.toString());
    }
}