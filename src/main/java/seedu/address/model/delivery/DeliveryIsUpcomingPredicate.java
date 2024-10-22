package seedu.address.model.delivery;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Delivery}'s is upcoming before a specified date.
 */
public class DeliveryIsUpcomingPredicate implements Predicate<Delivery> {
    private final DateTime completionDateTime;
    private final Status deliveryStatus;

    /**
     * Creates a Predicate that checks if Delivery has a specified status and occurs
     * before a specified date.
     *
     * @param completionDateTime Specified delivery date to check.
     * @param deliveryStatus Specified status.
     */
    public DeliveryIsUpcomingPredicate(DateTime completionDateTime, Status deliveryStatus) {
        this.completionDateTime = completionDateTime;
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public boolean test(Delivery delivery) {
        DateTimeBeforeInputDatePredicate deliveryIsEarlierPredicate =
                new DateTimeBeforeInputDatePredicate(completionDateTime);
        DeliveryStatusMatchInputPredicate deliveryStatusIsPendingPredicate =
                new DeliveryStatusMatchInputPredicate(deliveryStatus);
        boolean hasPendingStatus = deliveryStatusIsPendingPredicate.test(delivery);
        boolean hasValidDeliveryDate = deliveryIsEarlierPredicate.test(delivery);
        boolean isUpcoming = hasPendingStatus && hasValidDeliveryDate;
        return isUpcoming;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryIsUpcomingPredicate)) {
            return false;
        }

        DeliveryIsUpcomingPredicate otherDeliveryIsUpcomingPredicate = (DeliveryIsUpcomingPredicate) other;
        return completionDateTime.equals(otherDeliveryIsUpcomingPredicate.completionDateTime)
                && deliveryStatus.equals(otherDeliveryIsUpcomingPredicate.deliveryStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("completionDateTime", completionDateTime)
                .add("deliveryStatus", deliveryStatus).toString();
    }
}
