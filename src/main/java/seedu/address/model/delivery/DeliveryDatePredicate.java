package seedu.address.model.delivery;

import java.util.function.Predicate;

/**
 * Tests if a {@code Delivery}'s date matches the given date.
 */

public class DeliveryDatePredicate implements Predicate<Delivery> {
    private final DateTime targetDate;

    /**
     * Creates a DeliveryDatePredicate instance based on the given targetDateTime
     * @param targetDateTime DateTime as specified by user used to filter deliveries
     */
    public DeliveryDatePredicate(DateTime targetDateTime) {
        this.targetDate = targetDateTime;
    }

    @Override
    public boolean test(Delivery delivery) {
        return delivery.hasSameDate(targetDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeliveryDatePredicate
                && targetDate.equals(((DeliveryDatePredicate) other).targetDate));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{targetDate=" + targetDate.toString() + "}";
    }
}


