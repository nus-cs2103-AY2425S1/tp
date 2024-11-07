package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryList;

/**
 * Represents a delivery worker's details.
 * Contains list of DeliveryIds the worker is responsible for
 */
public class Worker {
    private final DeliveryList assignedDeliveryList= new DeliveryList();

    /**
     * Sets the delivery list of this worker.
     * <p>
     * Mainly used when loading a person's information from storage.
     */
    public void setDeliveryList(List<Delivery> deliveryList) {
        this.assignedDeliveryList.setDeliveries(deliveryList);
    }

    /**
     * Add a deliveryId to the worker
     */
    public void addDelivery(Delivery delivery) {
        assignedDeliveryList.add(delivery);
    }

    /**
     * Returns the list of deliveries assigned to the worker
     */
    public DeliveryList getAssignedDeliveryList() {
        return assignedDeliveryList;
    }

    /**
     * Returns the  {@code DeliveryList} of a {@code Worker} as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Delivery> getUnmodifiableAssignedDeliveryList() {
        return assignedDeliveryList.asUnmodifiableObservableList();
    }

    public boolean hasDelivery(Delivery delivery) {
        System.out.println(assignedDeliveryList);
        System.out.println(delivery);
        return assignedDeliveryList.contains(delivery);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Worker)) {
            return false;
        }

        Worker otherWorker = (Worker) other;
        return assignedDeliveryList.equals(otherWorker.assignedDeliveryList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("deliveries", assignedDeliveryList).toString();
    }
}
