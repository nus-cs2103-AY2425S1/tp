package seedu.address.model.order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.person.Person;

/**
 * OrderHistory consist of a past order and the time of order was placed, and the owner of the order.
 */
public class OrderHistory {
    private final Order order;
    private final LocalDateTime time;
    private final Person owner;
    static private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' HH:mm");

    /**
     * Create a new OrderHistory with the specified order at current time
     * @param order To be placed
     * @param owner of the order
     */
    public OrderHistory(Order order, Person owner) {
        this.order = order;
        this.time = LocalDateTime.now();
        this.owner = owner;
    }

    /**
     * Create a new OrderHistory with the specified order at current time
     * @param order to be placed
     * @param time of the order placed
     * @param owner of the order
     */
    public OrderHistory(Order order, LocalDateTime time, Person owner) {
        this.order = order;
        this.time = time;
        this.owner = owner;
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
