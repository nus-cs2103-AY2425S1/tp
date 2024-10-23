package seedu.address.model.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Track all the order history of a customer
 */
public class OrderTracker {
    private ArrayList<OrderHistory> history;
    private HashMap<Order, Integer> frequency;

    /**
     * Init the OrderTracker
     */
    public OrderTracker() {
        this.history = new ArrayList<>();
        this.frequency = new HashMap<>();
    }

    /**
     * Add an order to the tracker
     * @param order to add
     */
    public void add(Order order) {
        this.history.add(new OrderHistory(order));
        this.frequency.merge(order, 1, Integer::sum);
    }

    /**
     * Add an order with specified time
     * @param order to add
     */
    public void add(OrderHistory order) {
        this.history.add(order);
        this.frequency.merge(order.getOrder(), 1, Integer::sum);
    }

    public int getTotalOrder() {
        int sum = 0;
        for (Map.Entry<Order, Integer> entry: this.frequency.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public ArrayList<OrderHistory> get() {
        return this.history;
    }

    /**
     * Clear all order tracked
     */
    public void clear() {
        this.history = new ArrayList<>();
        this.frequency = new HashMap<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(history, frequency);
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }

        if (!(rhs instanceof OrderTracker)) {
            return false;
        }

        OrderTracker otherTracker = (OrderTracker) rhs;
        return history.equals(otherTracker.history)
                && frequency.equals(otherTracker.frequency);
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        for (OrderHistory orderHistory : this.history) {
            msg.append(orderHistory.toString()).append('\n');
        }
        return msg.toString();
    }
}
