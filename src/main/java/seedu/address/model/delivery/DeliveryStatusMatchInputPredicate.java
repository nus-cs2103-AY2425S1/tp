package seedu.address.model.delivery;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Delivery}'s {@code Status} value is same as provided Status.
 */
public class DeliveryStatusMatchInputPredicate implements Predicate<Delivery> {
    private final Status deliveryStatus;

    /**
     * Creates a Predicate that checks if Delivery has a specified status.
     *
     * @param deliveryStatus Specified status.
     */
    public DeliveryStatusMatchInputPredicate(Status deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    /**
     * Determines if predicate is true for a given input delivery by comparing the Status values.
     *
     * @param delivery Delivery object to test.
     * @return True if Delivery input has Status that is same as input status.
     */
    @Override
    public boolean test(Delivery delivery) {
        return delivery.hasSameStatus(deliveryStatus);
    }
    /**
     * Returns true if Status object of both objects are same.
     *
     * @param other Object to be compared with.
     * @return True if object is an instance of DeliveryStatusMatchInputPredicate and both
     *         Status objects are equal.
     */
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
    /**
     * Represents the String value of predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("deliveryStatus", deliveryStatus).toString();
    }
}
