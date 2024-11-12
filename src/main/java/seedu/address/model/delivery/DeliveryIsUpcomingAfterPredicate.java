package seedu.address.model.delivery;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Delivery} is upcoming after a specified date.
 */
public class DeliveryIsUpcomingAfterPredicate implements Predicate<Delivery> {
    private final DateTime completionDateTime;
    private final Status deliveryStatus;

    /**
     * Creates a Predicate that checks if Delivery has a specified status and occurs
     * after a specified date.
     *
     * @param completionDateTime Specified delivery date to check.
     * @param deliveryStatus Specified status.
     */
    public DeliveryIsUpcomingAfterPredicate(DateTime completionDateTime, Status deliveryStatus) {
        this.completionDateTime = completionDateTime;
        this.deliveryStatus = deliveryStatus;
    }
    /**
     * Determines if predicate is true for a given input delivery by comparing the DateTime values
     * and delivery status.
     *
     * @param delivery Delivery object to test.
     * @return True if input has a DateTime value that is later than completionDateTime
     *         and PENDING status.
     */
    @Override
    public boolean test(Delivery delivery) {
        DateTimeAfterInputDatePredicate deliveryIsLaterPredicate =
                new DateTimeAfterInputDatePredicate(completionDateTime);
        DeliveryStatusMatchInputPredicate deliveryStatusIsPendingPredicate =
                new DeliveryStatusMatchInputPredicate(deliveryStatus);
        boolean hasPendingStatus = deliveryStatusIsPendingPredicate.test(delivery);
        boolean hasValidDeliveryDate = deliveryIsLaterPredicate.test(delivery);
        boolean isUpcoming = hasPendingStatus && hasValidDeliveryDate;
        return isUpcoming;
    }
    /**
     * Returns true if DateTime object and Status of both objects are same.
     *
     * @param other Object to be compared with.
     * @return True if object is an instance of DeliveryIsUpcomingAfterPredicate and both
     *         DateTime objects and Status are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryIsUpcomingAfterPredicate)) {
            return false;
        }

        DeliveryIsUpcomingAfterPredicate otherDeliveryIsUpcomingAfterPredicate =
                (DeliveryIsUpcomingAfterPredicate) other;
        return completionDateTime.equals(otherDeliveryIsUpcomingAfterPredicate.completionDateTime)
                && deliveryStatus.equals(otherDeliveryIsUpcomingAfterPredicate.deliveryStatus);
    }
    /**
     * Represents the String value of predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("completionDateTime", completionDateTime)
                .add("deliveryStatus", deliveryStatus).toString();
    }
}
