package seedu.sellsavvy.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sellsavvy.model.order.exceptions.OrderNotFoundException;

/**
 * A list of orders made by a specific Person that does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class OrderList implements Iterable<Order> {

    private final ObservableList<Order> internalList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent order as the given argument.
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains an equivalent but different order as the given argument.
     */
    public boolean containsDuplicateOrder(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(order -> toCheck.equals(order) && toCheck != order);
    }

    /**
     * Returns if the order list contains an order with identical details and similar item.
     */
    public boolean containsSimilarOrder(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(order -> toCheck.isSimilarTo(order) && toCheck != order);
    }

    /**
     * Replaces the {@code target} order in the list with {@code editedOrder}.
     * {@code target} must exist in the list.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        int index = indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        internalList.set(index, editedOrder);
    }

    /**
     * Replaces the contents of this list with {@code orders}.
     */
    public void setOrders(List<Order> orders) {
        requireAllNonNull(orders);
        internalList.setAll(orders);
    }

    public void setOrders(OrderList replacement) {
        requireAllNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Adds an order to the list.
     */
    public void add(Order toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Removes the given order from the list.
     * The same order must exist in the list.
     */
    public void remove(Order toRemove) {
        requireNonNull(toRemove);

        int index = indexOf(toRemove);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        internalList.remove(index);
    }

    /**
     * Retrieves an order from the list at the specified index.
     * The index must be less than the list size.
     */
    public Order get(int index) {
        return internalList.get(index);
        // This will automatically throw IndexOutOfBoundsException if the index is invalid.
    }

    /**
     * Returns the number of orders in the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Order> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderList)) {
            return false;
        }

        OrderList otherUniqueOrderList = (OrderList) other;
        return internalList.equals(otherUniqueOrderList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Gets the index of the target {@code Order} using "==".
     */
    private int indexOf(Order target) {
        for (int i = 0; i < size(); i++) {
            if (target == internalList.get(i)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Creates a copy of this {@code OrderList} with new Orders.
     */
    public OrderList createCopy() {
        OrderList orderListCopy = new OrderList();
        for (Order order : internalList) {
            orderListCopy.add(order.createCopy());
        }
        return orderListCopy;
    }
}
