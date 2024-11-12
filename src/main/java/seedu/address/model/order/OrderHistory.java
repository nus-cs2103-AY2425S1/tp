package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        requireNonNull(order);
        this.order = order;
        this.time = LocalDateTime.now();
    }

    /**
     * Create a new OrderHistory with the specified order at current time
     * @param order to be placed
     * @param time of the order placed
     */
    public OrderHistory(Order order, LocalDateTime time) {
        requireNonNull(order);
        requireNonNull(time);
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

    @Override
    public int hashCode() {
        return Objects.hash(this.order, this.time);
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }

        if (!(rhs instanceof OrderHistory)) {
            return false;
        }

        OrderHistory otherOrderHistory = (OrderHistory) rhs;
        return this.time.equals(otherOrderHistory.time) && this.order.equals(otherOrderHistory.order);
    }
}
