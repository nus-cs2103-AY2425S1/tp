package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.delivery.DeliveryId;

/**
 * Represents a delivery worker's details.
 * Contains list of DeliveryIds the worker is responsible for
 */
public class Worker {
    private final Set<DeliveryId> deliveryIds;
    /**
     * Every field must be present and not null.
     */
    public Worker(Set<DeliveryId> deliveryIds) {
        requireAllNonNull(deliveryIds);
        this.deliveryIds = deliveryIds;
    }

    /**
     * Return all delivery ids of the worker
     */
    public Set<DeliveryId> getDeliveryIds() {
        return deliveryIds;
    }

    /**
     * Add a deliveryId to the worker
     */
    public void addDelivery(DeliveryId deliveryId) {
        deliveryIds.add(deliveryId);
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
        return deliveryIds.equals(otherWorker.deliveryIds);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("deliveryIds", deliveryIds).toString();
    }
}
