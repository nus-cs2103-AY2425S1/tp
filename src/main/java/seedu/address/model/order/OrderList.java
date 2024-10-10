package seedu.address.model.order;

import java.util.ArrayList;
import java.util.List;

public class OrderList<T extends Order> {
    private final List<T> orders;

    public OrderList() {
        this.orders = new ArrayList<>();
    }

    // Add an order
    public void addOrder(T order) {
        orders.add(order);
    }

    // Remove an order
    public boolean removeOrder(T order) {
        return orders.remove(order);
    }

    // Get all orders
    public List<T> getAllOrders() {
        return new ArrayList<>(orders);  // Return a copy to protect encapsulation
    }

    // Count total orders
    public int getOrderCount() {
        return orders.size();
    }

    // Find orders by status (e.g., "Pending", "Completed")
    public List<T> findOrdersByStatus(String status) {
        List<T> filteredOrders = new ArrayList<>();
        for (T order : orders) {
            if (order.getStatus().equalsIgnoreCase(status)) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "orders=" + orders +
                '}';
    }
}
