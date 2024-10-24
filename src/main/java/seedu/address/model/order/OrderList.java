package seedu.address.model.order;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to manage a list of orders, including both supply and customer orders.
 */
public abstract class OrderList<T extends Order> {
    private ObservableList<T> orders = FXCollections.observableArrayList();;
    private ObservableList<T> internalUnmodifiableOrders =
            FXCollections.unmodifiableObservableList(orders);


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
     * Finds an order by the phone number.
     *
     * @param phoneNumber The phone number associated with the order.
     * @return The order associated with the given phone number, or null if not found.
     */

    /**
     * Retrieves all customer orders.
     *
     * @return A list of all customer orders.
     */
    public ObservableList<T> getOrders() {
        return internalUnmodifiableOrders;  // Return a copy of the list to avoid modification
    }


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
            sb.append(String.format("Order %d", i + 1));
            sb.append("\n");
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
