package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.delivery.exceptions.DeliveryNotFoundException;
import seedu.address.model.delivery.exceptions.DuplicateDeliveryException;

/**
 * A list of deliveries that enforces uniqueness between its elements and does not allow nulls.
 * A delivery is considered unique by comparing using {@code Delivery#isSameDelivery(Delivery)}. As such,
 * adding and updating of deliveries uses Delivery#isSameDelivery(Delivery) for equality so as to ensure that the
 * delivery being added or updated is unique in terms of identity in the UniqueDeliveryList. However, the removal
 * of a supplier uses Supplier#equals(Object) so as to ensure that the supplier
 * with exactly the same fields will be removed.
 */

public class UniqueDeliveryList implements Iterable<Delivery> {
    private final ObservableList<Delivery> internalList = FXCollections.observableArrayList();

    private final ObservableList<Delivery> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Returns true if the list contains a delivery that is equivalent to the specified delivery.
     *
     * @param toCheck the delivery to check for its existence in the current list
     * @return true if an equivalent delivery already exists in the list, false otherwise
     */
    public boolean contains(Delivery toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDelivery);
    }

    /**
     * Adds a delivery to the list.
     * The delivery must not already exist in the list.
     *
     * @param toAdd the delivery to add to the list
     * @throws DuplicateDeliveryException if the delivery already exists in the list
     */

    public void addDelivery(Delivery toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDeliveryException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the specified delivery from the list.
     * The delivery must exist in the list.
     *
     * @param toRemove the delivery to remove from the list
     * @throws DeliveryNotFoundException if the delivery does not exist in the list
     */
    public void removeDelivery(Delivery toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DeliveryNotFoundException();
        }
    }

    /**
     * Replaces the specified target delivery in the list with an edited delivery.
     * The target delivery must exist in the list, and the identity of the edited delivery
     * must not be the same as an existing delivery in the list.
     *
     * @param target the delivery to be replaced
     * @param editedDelivery the new delivery to replace the target with
     * @throws DeliveryNotFoundException if the target delivery does not exist in the list
     * @throws DuplicateDeliveryException if the editedDelivery already exists in the list
     */
    public void setDelivery(Delivery target, Delivery editedDelivery) {
        requireAllNonNull(target, editedDelivery);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DeliveryNotFoundException();
        }

        if (!target.isSameDelivery(editedDelivery) && contains(editedDelivery)) {
            throw new DuplicateDeliveryException();
        }

        internalList.set(index, editedDelivery);
    }

    public void setDeliveries(UniqueDeliveryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code deliveries}.
     * {@code deliveries} must not contain duplicate deliveries.
     *
     * @param deliveries the list of deliveries to set as the new contents of this list
     * @throws DuplicateDeliveryException if the given list contains any duplicate deliveries
     */

    public void setDeliveries(List<Delivery> deliveries) {
        requireAllNonNull(deliveries);
        if (!deliveriesAreUnique(deliveries)) {
            throw new DuplicateDeliveryException();
        }

        internalList.setAll(deliveries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Delivery> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Delivery> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueDeliveryList)) {
            return false;
        }

        UniqueDeliveryList otherUniqueDeliveryList = (UniqueDeliveryList) other;
        return internalList.equals(otherUniqueDeliveryList.internalList);
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
     * Returns true if {@code deliveries} contains only unique deliveries.
     *
     * @param deliveries the list of deliveries to check for uniqueness
     * @return true if the list contains only unique deliveries
     */
    private boolean deliveriesAreUnique(List<Delivery> deliveries) {
        for (int i = 0; i < deliveries.size() - 1; i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                if (deliveries.get(i).isSameDelivery(deliveries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
