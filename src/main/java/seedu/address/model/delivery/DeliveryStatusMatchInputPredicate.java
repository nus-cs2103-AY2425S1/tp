package seedu.address.model.delivery;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Delivery}'s {@code Status} value is {@code PENDING}.
 */
public class DeliveryStatusMatchInputPredicate implements Predicate<Delivery> {
    private final Status deliveryStatus;

    public DeliveryStatusMatchInputPredicate(Status deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public boolean test(Delivery delivery) {
        return delivery.hasSameStatus(deliveryStatus);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryStatusMatchInputPredicate)) {
            return false;
        }

        DeliveryStatusMatchInputPredicate otherDeliveryStatusMatchInputPredicate =
                (DeliveryStatusMatchInputPredicate) other;
        return deliveryStatus.equals(otherDeliveryStatusMatchInputPredicate.deliveryStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("deliveryStatus", deliveryStatus).toString();
    }
}
