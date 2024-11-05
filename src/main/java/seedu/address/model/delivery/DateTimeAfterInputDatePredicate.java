package seedu.address.model.delivery;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Delivery}'s {@code DateTime} value is later than input completion date.
 */
public class DateTimeAfterInputDatePredicate implements Predicate<Delivery> {
    private final DateTime completionDateTime;

    public DateTimeAfterInputDatePredicate(DateTime completionDateTime) {
        this.completionDateTime = completionDateTime;
    }

    @Override
    public boolean test(Delivery delivery) {
        return delivery.hasLaterDateThan(completionDateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateTimeAfterInputDatePredicate)) {
            return false;
        }

        DateTimeAfterInputDatePredicate otherDateTimeAfterInputDatePredicate =
                (DateTimeAfterInputDatePredicate) other;
        return completionDateTime.equals(otherDateTimeAfterInputDatePredicate.completionDateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("completionDateTime", completionDateTime).toString();
    }
}
