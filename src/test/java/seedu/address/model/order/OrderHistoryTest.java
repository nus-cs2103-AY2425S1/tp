package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


class OrderHistoryTest {

    @Test
    void toStringMethodTest() {
        LocalDateTime time = LocalDateTime.of(2023, 12, 12, 1, 54);
        OrderHistory history = new OrderHistory(new Order("cake"), time);
        assertEquals(history.toString(), "Tuesday, December 12, 2023 at 01:54: Ordered cake");
    }

    @Test
    void equalsMethodTest() {
        LocalDateTime time = LocalDateTime.of(2023, 12, 12, 1, 54);
        LocalDateTime time2 = LocalDateTime.of(2223, 12, 12, 1, 54);
        OrderHistory orderHistory = new OrderHistory(new Order("cake"), time);
        OrderHistory orderHistory2 = new OrderHistory(new Order("pizza"), time);
        OrderHistory orderHistory3 = new OrderHistory(new Order("pizza"), time2);

        assertEquals(orderHistory, orderHistory);
        assertNotEquals(orderHistory, null);
        assertNotEquals(orderHistory, orderHistory2);
        assertNotEquals(orderHistory2, orderHistory3);

    }

}
