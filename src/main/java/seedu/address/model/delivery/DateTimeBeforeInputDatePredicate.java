package seedu.address.model.delivery;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Tests that a {@code Delivery}'s {@code DateTime} value is earlier than input completion date.
 */
public class DateTimeBeforeInputDatePredicate implements Predicate<Delivery> {
    private final DateTime completionDateTime;

    public DateTimeBeforeInputDatePredicate(DateTime completionDateTime) {
        this.completionDateTime = completionDateTime;
    }

    @Override
    public boolean test(Delivery delivery) {
        return delivery.hasEarlierDateThan(completionDateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateTimeBeforeInputDatePredicate)) {
            return false;
        }

        DateTimeBeforeInputDatePredicate otherDateTimeBeforeInputDatePredicate =
                (DateTimeBeforeInputDatePredicate) other;
        return completionDateTime.equals(otherDateTimeBeforeInputDatePredicate.completionDateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("completionDateTime", completionDateTime).toString();
    }
}
