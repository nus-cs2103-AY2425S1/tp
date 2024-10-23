package seedu.address.model.order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * OrderHistory consist of a past order and the time of order was placed
 */
public class OrderHistory {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' HH:mm");

    private final Order order;
    private final LocalDateTime time;

    /**
     * Create a new OrderHistory with the specified order at current time
     * @param order To be placed
     */
    public OrderHistory(Order order) {
        this.order = order;
        this.time = LocalDateTime.now();
    }

    /**
     * Create a new OrderHistory with the specified order at current time
     * @param order to be placed
     * @param time of the order placed
     */
    public OrderHistory(Order order, LocalDateTime time) {
        this.order = order;
        this.time = time;
    }

    public Order getOrder() {
        return this.order;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return this.time.format(formatter) + ": Ordered " + getOrder();
    }
}
