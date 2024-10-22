package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.delivery.exceptions.DeliveryNotFoundException;

/**
 * A list of deliveries that does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 */
public class DeliveryList {
    private final ObservableList<Delivery> internalList = FXCollections.observableArrayList();
    private final ObservableList<Delivery> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent delivery as the given argument.
     */
    public boolean contains(Delivery toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDelivery);
    }

    /**
     * Returns the number of delivery in the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Adds a delivery to the list.
     */
    public void add(Delivery toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Adds a delivery to the index of the list.
     */
    public void add(Index targetIndex, Delivery toAdd) {
        requireNonNull(toAdd);
        requireNonNull(targetIndex);
        int index = targetIndex.getZeroBased();
        internalList.add(index, toAdd);
    }

    /**
     * Get the index of the first archived delivery in the list.
     */
    public Index getFirstArchivedIndex() {
        for (int i = 0; i < size(); i++) {
            Delivery delivery = internalList.get(i);
            if (delivery.isArchived()) {
                Index firstArchivedIndex = Index.fromZeroBased(i);
                return firstArchivedIndex;
            }
        }
        return Index.fromZeroBased(size());
    }


    /**
     * Replaces the delivery {@code target} in the list with {@code editedDelivery}.
     * {@code target} must exist in the list.
     */
    public void setDelivery(Delivery target, Delivery editedDelivery) throws DeliveryNotFoundException {
        requireAllNonNull(target, editedDelivery);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DeliveryNotFoundException();
        }

        internalList.set(index, editedDelivery);
    }

    /**
     * Removes the equivalent delivery from the list.
     * The delivery must exist in the list.
     */
    public void remove(Index deliveryIndex) throws DeliveryNotFoundException {
        requireNonNull(deliveryIndex);
        if (deliveryIndex.getZeroBased() >= internalList.size()) {
            throw new DeliveryNotFoundException();
        }
        internalList.remove(deliveryIndex.getZeroBased());
    }

    public void setDeliveries(DeliveryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code deliveries}.
     */
    public void setDeliveries(List<Delivery> deliveries) {
        requireAllNonNull(deliveries);
        internalList.setAll(deliveries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Delivery> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryList)) {
            return false;
        }

        DeliveryList otherDeliveryList = (DeliveryList) other;
        return internalList.equals(otherDeliveryList.internalList);
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
