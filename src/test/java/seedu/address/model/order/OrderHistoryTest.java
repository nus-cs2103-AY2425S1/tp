package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


class OrderHistoryTest {

    @Test
    void toStringMethodTest() {
        LocalDateTime time = LocalDateTime.of(2023, 12, 12, 1, 54);
        OrderHistory history = new OrderHistory(new Order("cake"), time);
        assertEquals(history.toString(), "Tuesday, December 12, 2023 at 01:54: Ordered cake");
    }

}
