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
     * AFTER a specified date.
     *
     * @param completionDateTime Specified delivery date to check.
     * @param deliveryStatus Specified status.
     */
    public DeliveryIsUpcomingAfterPredicate(DateTime completionDateTime, Status deliveryStatus) {
        this.completionDateTime = completionDateTime;
        this.deliveryStatus = deliveryStatus;
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("completionDateTime", completionDateTime)
                .add("deliveryStatus", deliveryStatus).toString();
    }
}
