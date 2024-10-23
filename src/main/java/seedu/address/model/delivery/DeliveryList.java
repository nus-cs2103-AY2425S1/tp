package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
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
        requireAllNonNull(toAdd, targetIndex);
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

    /**
     * Reverses the backing list.
     * <p>
     * Used when sorting the list by a specified attribute, in descending order.
     */
    public void reverseDeliveryList() {
        int index = getFirstArchivedIndex().getZeroBased();
        Collections.reverse(internalList.subList(0, index));
        Collections.reverse(internalList.subList(index, internalList.size()));
    }

    /**
     * Checks if the archive status of two deliveries are the same.
     */
    public boolean isSameArchiveStatus(Delivery d1, Delivery d2) {
        return d1.isArchived() == d2.isArchived();
    }

    /**
     * Sorts the backing list using the {@code Address} attribute of each delivery, in ascending order.
     */
    public void sortByAddress() {
        internalList.sort((d1, d2) -> {
            if (isSameArchiveStatus(d1, d2)) {
                // If both archived or both not archived, compare address as normal.
                return d1.getAddress().value.compareTo(d2.getAddress().value);
            } else {
                //  Not archived (i.e. false) will always come first.
                return d1.getArchive().value.compareTo(d2.getArchive().value);
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Cost} attribute of each delivery, in ascending order.
     */
    public void sortByCost() {
        internalList.sort((d1, d2) -> {
            if (isSameArchiveStatus(d1, d2)) {
                return Float.compare(d1.getCost().asFloat(), d2.getCost().asFloat());
            } else {
                return d1.getArchive().value.compareTo(d2.getArchive().value);
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Date} attribute of each delivery, in ascending order.
     * <p>
     * If the dates of the compared deliveries are the same, {@code Time} attribute of each delivery is
     * used for tie-breaking.
     */
    public void sortByDate() {
        internalList.sort((d1, d2) -> {
            if (isSameArchiveStatus(d1, d2)) {
                int dateCompare = d1.getDate().value.compareTo(d2.getDate().value);
                if (dateCompare == 0) {
                    return d1.getTime().value.compareTo(d2.getTime().value);
                } else {
                    return dateCompare;
                }
            } else {
                return d1.getArchive().value.compareTo(d2.getArchive().value);
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Eta} attribute of each delivery, in ascending order.
     */
    public void sortByEta() {
        internalList.sort((d1, d2) -> {
            if (isSameArchiveStatus(d1, d2)) {
                return d1.getEta().value.compareTo(d2.getEta().value);
            } else {
                return d1.getArchive().value.compareTo(d2.getArchive().value);
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Id} attribute of each delivery, in ascending order.
     */
    public void sortById() {
        internalList.sort((d1, d2) -> {
            if (isSameArchiveStatus(d1, d2)) {
                return d1.getDeliveryId().value.compareTo(d2.getDeliveryId().value);
            } else {
                return d1.getArchive().value.compareTo(d2.getArchive().value);
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Status} attribute of each delivery, in ascending order.
     */
    public void sortByStatus() {
        internalList.sort((d1, d2) -> {
            if (isSameArchiveStatus(d1, d2)) {
                int statusCompare = d1.getStatus().getValue().compareTo(d2.getStatus().getValue());
                return -statusCompare; // Negation will keep delivered items below.
            } else {
                return d1.getArchive().value.compareTo(d2.getArchive().value);
            }
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
