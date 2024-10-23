package seedu.address.model.delivery;

import java.util.function.Predicate;

/**
 * Tests if a {@code Delivery}'s status matches the given status.
 */
public class DeliveryStatusPredicate implements Predicate<Delivery> {
    private final Status targetStatus;

    /**
     * Creates a DeliveryStatusPredicate instance based on the given Status.
     * @param targetStatus Status as specified by user used to filter deliveries.
     */
    public DeliveryStatusPredicate(Status targetStatus) {
        this.targetStatus = targetStatus;
    }

    @Override
    public boolean test(Delivery delivery) {
        Status deliveryStatus = delivery.getDeliveryStatus();
        return deliveryStatus.equals(targetStatus);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeliveryStatusPredicate
                && targetStatus.equals(((DeliveryStatusPredicate) other).targetStatus));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{targetStatus=" + targetStatus.toString() + "}";
    }
}
