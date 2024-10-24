package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class OrderTrackerTest {

    private final LocalDateTime time = LocalDateTime.of(2023, 12, 12, 1, 54);
    private final Order order = new Order("cake");
    private final OrderHistory history = new OrderHistory(order, time);

    @Test
    void clear_nonEmptyList_success() {
        OrderTracker tracker = new OrderTracker();
        tracker.add(new Order("cake"));
        tracker.add(new Order("pizza"));
        tracker.clear();
        assertEquals(tracker.toString(), "");
    }

    @Test
    void toStringMethodTest() {
        OrderTracker tracker = new OrderTracker();
        tracker.add(history);
        assertEquals("Tuesday, December 12, 2023 at 01:54: Ordered cake\n", tracker.toString());
    }

    @Test
    void equalsMethodTest() throws InterruptedException {
        OrderTracker tracker1 = new OrderTracker();
        OrderTracker tracker2 = new OrderTracker();
        tracker1.add(history);
        // Sleep for 1 millisecond to be safe
        Thread.sleep(1);
        // The time for the order should be different
        tracker2.add(order);

        assertEquals(tracker1, tracker1);
        assertNotEquals(tracker1, tracker2);
    }

}
