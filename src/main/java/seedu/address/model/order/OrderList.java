package seedu.address.model.order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class OrderList<T extends Order> {

    protected List<T> orders;

    public OrderList() {
        this.orders = new ArrayList<>();
    }

    /**
     * Adds an order to the list.
     *
     * @param order The order to be added.
     */
    public void addOrder(T order) {
        assert order != null : "Order to add cannot be null";
        orders.add(order);
    }

    /**
     * Retrieves all orders.
     *
     * @return A list of all orders.
     */
    public List<T> getOrders() {
        return new ArrayList<>(orders);  // Return a copy of the list to avoid modification
    }

    /**
     * Finds an order by the phone number.
     *
     * @param phoneNumber The phone number associated with the order.
     * @return The order associated with the given phone number, or null if not found.
     */
    public T findOrderByPhoneNumber(String phoneNumber) {
        assert phoneNumber != null : "Phone number cannot be null";
        for (T order : orders) {
            if (order.getPhoneNumber().equals(phoneNumber)) {
                return order;
            }
        }
        return null;  // Return null if not found
    }

    /**
     * Removes an order by the phone number.
     *
     * @param phoneNumber The phone number associated with the order.
     * @return True if the order was successfully removed, false otherwise.
     */
    public boolean removeOrder(String phoneNumber) {
        assert phoneNumber != null : "Phone number cannot be null";
        return orders.removeIf(order -> order.getPhoneNumber().equals(phoneNumber));
    }

    /**
     * View all orders, with pending orders first, each order numbered, and status displayed.
     *
     * @return A string representing all the orders in the list.
     */
    public String viewOrders() {
        StringBuilder sb = new StringBuilder();

        // Sort orders: PENDING first, followed by others (e.g., COMPLETED, CANCELLED)
        orders.sort(Comparator.comparing(Order::getStatus));

        // Generate output
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            sb.append(String.format("Order %d: [Status: %s]\n", i + 1, order.getStatus()));
            sb.append(order.viewOrder());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "orders=" + orders +
                '}';
    }
}
