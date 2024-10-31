package seedu.address.model.order;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to manage a list of orders, including both supply and customer orders.
 */
public abstract class OrderList<T extends Order> {

    private final ObservableList<T> orders = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableOrders =
            FXCollections.unmodifiableObservableList(orders);

    /**
     * Adds an order to the list.
     *
     * @param order The order to be added.
     */
    public void addOrder(T order) {
        assert order != null : "Order to add cannot be null";
        orders.add(order);
        orders.sort(Comparator.comparing(Order::getStatus));
    }

    /**
     * Retrieves all customer orders.
     *
     * @return An ObservableList of all customer orders.
     */
    public ObservableList<T> getOrders() {
        return internalUnmodifiableOrders;
    }

    /**
     * Remove an order of the specified index.
     *
     * @param index The index of the order to retrieve (0-based index).
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void removeOrder(int index) {
        if (index < 0 || index >= orders.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        orders.remove(index);
    }

    /**
     * View all orders, with pending orders first, each order numbered, and status displayed.
     *
     * @return A string representing all the orders in the list.
     */
    public String viewOrders() {
        StringBuilder sb = new StringBuilder();

        orders.sort(Comparator.comparing(Order::getStatus));

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
        return viewOrders();
    }
}
