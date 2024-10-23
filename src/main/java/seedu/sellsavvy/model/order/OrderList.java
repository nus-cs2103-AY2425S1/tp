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
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameOrder);
    }

    /**
     * Replaces the first instance of an equivalent order {@code target} in the list with {@code editedOrder}.
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
     * Removes the first instance of an equivalent order from the list.
     * An equivalent order must exist in the list.
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
}
