package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
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
     * Adds a delivery to the list.
     */
    public void add(Delivery toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
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

    /**
     * Reverses the backing list.
     * <p>
     * Used when sorting the list by a specified attribute, in descending order.
     */
    public void reverseDeliveryList() {
        Collections.reverse(internalList);
    }

    /**
     * Sorts the backing list using the {@code Address} attribute of each delivery, in ascending order.
     */
    public void sortByAddress() {
        internalList.sort(Comparator.comparing(d -> d.getAddress().value));
    }

    /**
     * Sorts the backing list using the {@code Cost} attribute of each delivery, in ascending order.
     */
    public void sortByCost() {
        internalList.sort((d1, d2) -> (int) (d1.getCost().asFloat() - d2.getCost().asFloat()));
    }

    /**
     * Sorts the backing list using the {@code Date} attribute of each delivery, in ascending order.
     * <p>
     * If the dates of the compared deliveries are the same, {@code Time} attribute of each delivery is
     * used for tie-breaking.
     */
    public void sortByDate() {
        internalList.sort((d1, d2) -> {
            int compareValue = d1.getDate().value.compareTo(d2.getDate().value);
            if (compareValue == 0) {
                return d1.getTime().value.compareTo(d2.getTime().value);
            } else {
                return compareValue;
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Eta} attribute of each delivery, in ascending order.
     */
    public void sortByEta() {
        internalList.sort(Comparator.comparing(d -> d.getEta().value));
    }

    /**
     * Sorts the backing list using the {@code Id} attribute of each delivery, in ascending order.
     */
    public void sortById() {
        internalList.sort(Comparator.comparing(d -> d.getDeliveryId().value));
    }

    /**
     * Sorts the backing list using the {@code Status} attribute of each delivery, in ascending order.
     */
    public void sortByStatus() {
        internalList.sort((d1, d2) -> {
            int compareValue = d1.getStatus().getValue().compareTo(d2.getStatus().getValue());
            return -compareValue; // Negation will keep delivered items below.
        });
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
